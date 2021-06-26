<%@ page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="mtw" uri="http://www.mentaframework.org/tags-mtw/"%>

<mtw:useI18N prefix="index" />

<mtw:requiresAuthentication />

<html>
	<head>
		<title>
			<mtw:i18n key="title" noPrefix="true" />
		</title>
	</head>
	
	<body>
		<h3>
			<mtw:i18n key="welcome" dynValues="sessionUser.username" />
			(<a href="<mtw:contextPath />/Logout.mtw"><mtw:i18n key="logout" /></a>)
		</h3>
	
		<mtw:outMessage>
			<h5>
				<mtw:out />
			</h5>
		</mtw:outMessage>
	
		<mtw:hasAuthorization groups="admin, master">
			<p>
				<a href="<mtw:contextPath />/User.edit.mtw">
					<mtw:i18n key="alter_profile" />
				</a>
			</p>
		</mtw:hasAuthorization>
	
		<mtw:hasAuthorization groups="admin, master" negate="true">
			<p>
				<i>
					<mtw:i18n key="only_admin_master" />
				</i>
			</p>
		</mtw:hasAuthorization>
	
	</body>
</html>




