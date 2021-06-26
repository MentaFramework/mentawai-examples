package org.menta;

import java.io.IOException;
import java.sql.Connection;

import org.menta.action.LoginAction;
import org.menta.action.UserAction;
import org.menta.dao.UserDAO;
import org.menta.dao.jdbc.JdbcUserDAO;
import org.menta.model.Group;
import org.menta.model.Language;
import org.menta.model.User;
import org.menta.model.User.Sexo;
import org.menta.service.UserService;
import org.menta.service.impl.UserServiceImpl;
import org.mentabean.BeanSession;
import org.mentabean.DBTypes;
import org.mentabean.util.SQLUtils;
import org.mentawai.action.LogoutAction;
import org.mentawai.ajax.renderer.ResultRenderer;
import org.mentawai.core.ApplicationManager;
import org.mentawai.core.Context;
import org.mentawai.core.Props;
import org.mentawai.db.ConnectionHandler;
import org.mentawai.db.mysql.MySQLBoneCPConnectionHandler;
import org.mentawai.filter.AuthenticationFilter;
import org.mentawai.filter.ExceptionFilter;
import org.mentawai.filter.MentaContainerFilter;
import org.mentawai.filter.TransactionFilter;
import org.mentawai.filter.ValidationFilter;
import org.mentawai.list.BaseListData;
import org.mentawai.list.ListManager;
import org.mentawai.mail.Email;

public class AppManager extends ApplicationManager {

	private Props props;
	
	@Override
	public void init(Context application) {
		
		this.props = getProps();
		
		////////////////////////////////////////////
		// TURN ON/OFF DEBUG MODE
		////////////////////////////////////////////
		setDebugMode(props.getBoolean("debug_mode"));
		
		///////////////////////////////////////////////////
		// TURN ON/OFF APP MANAGER AUTO-REDEPLOY FEATURE
        // OBS: Requires http://www.javarebel.com to work
		///////////////////////////////////////////////////
		setReloadable(props.getBoolean("auto_reload"));
		
		//////////////////////////////////////////
		// FOR SENDING EMAIL
		//////////////////////////////////////////
		if (!props.getBoolean("email.send_email")) {
			
			Email.setSendEmail(false);
			
		} else {
		
			Email.setDefaultHostName(props.getString("email.host"));
			
			Email.setDefaultSslConnection( props.getBoolean("email.ssl") );
			
			Email.setDefaultPort( props.getInt("email.port") );

			if (props.getBoolean("email.use_authentication")) {
				
				Email.setDefaultAuthentication(props.getString("email.user"), props.getString("email.pass")); 
			}
			
			Email.setDefaultFrom(props.getString("email.from_email"), props.getString("email.from_name"));
		}
	}

	@Override
	public ConnectionHandler createConnectionHandler() {
		
		String driver = props.getString("jdbc.driver");
		String url = props.getString("jdbc.url");
		String user = props.getString("jdbc.user");
		String pass = props.getString("jdbc.pass");
		
		return new MySQLBoneCPConnectionHandler(driver, url, user, pass);
	}
	
	@Override
	public void setupDB() {
		initDatabaseIfNeeded();
	}
	
	@Override
	public void loadBeans() {
	
		bean(User.class, "Users")
			.pk("id", DBTypes.AUTOINCREMENT)
			.field("username", DBTypes.STRING)
			.field("password", DBTypes.STRING)
			.field("email", DBTypes.STRING)
			.field("languageId", "language_id", DBTypes.INTEGER)
			.field("groupId", "group_id", DBTypes.INTEGER)
			.field("sexo","sexo", DBTypes.ENUMID.from(Sexo.class));
		
	}
	
    @Override
    public void loadLists() {
		
    	try {
			ListManager.addList( new BaseListData("sexo", BaseListData.ORDER_BY_ID) );
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		addLocalizedLists("groups", "languages");
	}
	
	@Override
	public void loadLocales() {
		addLocale(Language.ENGLISH.getLocale());
		addLocale(Language.PORTUGUESE.getLocale());
	}
	
	@Override
	public void loadFilters() {
		
		filter(new ExceptionFilter());
		on(EXCEPTION, fwd("/jsp/error.jsp"));
		
		filter(new MentaContainerFilter());
		
		filter(new AuthenticationFilter());
		on(LOGIN, redir("/jsp/login.jsp"));
		
		filter(new ValidationFilter()); 
		
		filter(new TransactionFilter());
	}
	
	@Override
	public void setupIoC() {

		ioc(BeanSession.class, props.getClass("mentabean.dialect"));
		
		ioc(UserDAO.class, JdbcUserDAO.class);
		
		ioc(UserService.class, UserServiceImpl.class);
	}
	
	@Override
	public void loadActions() {
		
		action("/User", UserAction.class, "add")
			.bypassAuthentication()
			.on(ERROR, fwd("/jsp/user/add.jsp"))
			.on(CREATED, redir("/jsp/index.jsp"));
		
		action("/User", UserAction.class, "edit")
			.comeBackAfterLogin()
			.authorize(Group.ADMIN, Group.MASTER)
			.on(ERROR, fwd("/jsp/user/edit.jsp"))
			.on(SHOW, fwd("/jsp/user/edit.jsp"))
			.on(UPDATED, redir("/jsp/index.jsp"));
		
		action("/User", UserAction.class, "check")
			.bypassAuthentication()
			.all(ajax(new ResultRenderer())); // return text/plain with the result...
	
		action("/Login", LoginAction.class)
			.methodParams("username", "password")
			.on(ERROR, fwd("/jsp/login.jsp"))
			.on(SUCCESS, redir("/jsp/index.jsp"));
		
		action("/Logout", LogoutAction.class)
			.on(SUCCESS, redir("/jsp/login.jsp"));
		
	}
	
	////////////////////////////////////////////////////////////////////////////////
	// Database initialization so this app does not require any database setup
	//
	// NOTE: It is not necessary to do this if your database is already initialized
	////////////////////////////////////////////////////////////////////////////////
	
	private void initDatabaseIfNeeded() {
		
		final ConnectionHandler connHandler = getConnectionHandler();
		
		connHandler.exec(new ConnectionHandler.Exec() {
		
			@Override
			public void exec(Connection conn) {
				initDatabaseIfNeeded(conn);
			}
		});
	}
	
	protected void initDatabaseIfNeeded(Connection conn) {
		
		if (SQLUtils.checkIfTableExists(conn, "users")) return;
		
		try {
			String file = props.getAbsolutePath("db.script");
			SQLUtils.executeScript(conn, file, "UTF-8");
		} catch(Exception e) {
			e.printStackTrace();
		}

		if (!SQLUtils.checkIfTableExists(conn, "users")) throw new RuntimeException("Failed to initialize db!");
	}
}