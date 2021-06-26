package org.menta;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.menta.dao.UserDAO;
import org.mentacontainer.Container;
import org.mentacontainer.Scope;
import org.mentawai.core.ApplicationManager.Environment;
import org.mentawai.core.BaseAction;
import org.mentawai.db.ConnectionHandler;
import org.mentawai.mail.Email;

public abstract class AbstractBaseTest {
	
	protected static Container container = null;
	
	static {
		
		AppManager appManager = new AppManager();

		appManager.setWebappPath("src/main/webapp");
		
		container = AppManager.getContainer();
		
		appManager.setEnvironment(Environment.TEST);
		
		appManager.init();
		
		ConnectionHandler connHandler = appManager.createConnectionHandler();
		appManager.setConnectionHandler(connHandler);
		
		appManager.setupDB();
		
		appManager.loadBeans();
		
		appManager.loadLocales();
		
		appManager.loadLists();
		
		appManager.setupIoC();
		
		// don't send email during tests...
		
		Email.setSendEmail(false);
		
		Runtime.getRuntime().addShutdownHook(new Thread() {
			
			@Override
			public void run() {
				deleteFile("MentaTest.h2.db");
				deleteFile("MentaTest.lock.db");
			}
		});
	}
	
	public static void deleteFile(String s) {
		try {
			Thread.sleep(1000);	// in MS Windows dont works without this =/
			
			File f = new File(s);
			if (f.exists()) f.delete();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	protected UserDAO userDAO = null;
	
	@Before
	public void before() throws Exception {
		
		userDAO = container.get("userDAO");
	}
	
	@After
	public void after() throws Exception {

		container.clear(Scope.THREAD);
	}
	
	protected <T extends BaseAction> T getAction(Class<T> action) throws Exception {
		
		T a = container.construct(action);
		
		a.init();
		
		return a;
	}
}