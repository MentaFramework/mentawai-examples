<%@ page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="mtw" uri="http://www.mentaframework.org/tags-mtw/"%>

<mtw:useI18N prefix="login" />

<html>
	<head>
		<title>
			<mtw:i18n key="title" noPrefix="true" />
		</title>
	</head>
	
	<body>
        <p>
        <b><mtw:i18n key="login" /></b>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <a href="<mtw:urlWithLoc loc="en" />"><img src="<mtw:contextPath />/images/usa.gif" border="0" width="20" height="13" /></a>
        <a href="<mtw:urlWithLoc loc="pt" />"><img src="<mtw:contextPath />/images/brazil.gif" border="0" width="20" height="13" /></a>
        </p>
		<form action="<mtw:contextPath />/Login.mtw" method="post">
			<table>
				<tr>
					<td>
						<mtw:i18n key="username" />
					</td>
					<td>
						<mtw:input name="username" size="20" maxlength="20" />
						<mtw:outError field="username">
							<font color="red">
								<b><mtw:out /></b>
							</font>
						</mtw:outError>
					</td>
				</tr>
				<tr>
					<td>
						<mtw:i18n key="password" />
					</td>
					<td>
						<mtw:input name="password" type="password" size="20" maxlength="20" /> 
						<mtw:outError field="password">
							<font color="red">
								<b><mtw:out /></b>
							</font>
						</mtw:outError>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<input type="submit" value="<mtw:i18n key="enter" />" />
					</td>
				</tr>
	
			</table>
		</form>
		<p>
			<a href="<mtw:contextPath />/jsp/user/add.jsp">
				<mtw:i18n key="register" />
			</a>
		</p>
	</body>
</html>




