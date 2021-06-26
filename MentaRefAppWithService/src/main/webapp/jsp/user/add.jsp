<%@ page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="mtw" uri="http://www.mentaframework.org/tags-mtw/"%>

<mtw:useI18N prefix="user.add" />

<html>
	<head>
		<title><mtw:i18n key="title" noPrefix="true" /></title>
		
		<script type="text/javascript" src="<mtw:contextPath />/jquery/jquery-1.4.4.min.js"></script>
		
		<script type="text/javascript">
			$(document).ready(function() {
		
				$('#usernameLoading').hide();
		
				$('#username').blur( function() {
		
					$('#usernameMessage').hide();
		
					if ($('#username').val().trim() != '') {
		
						$('#usernameLoading').show();
		
						$.post("<mtw:contextPath />/User.check.mtw", {
							username : $('#username').val()
						}, function(response) {
							$('#usernameMessage').fadeOut();
							setTimeout("usernameResult('"
									+ escape(response) + "')", 400);
						});
					}
		
					return false;
				});
				
			});
		
			function usernameResult(response) {
		
				$('#usernameLoading').hide();
		
				var res = unescape(response);
		
				if (res == "error") {
		
					$('#usernameMessage')
							.html(
									'<font color="red"><b><mtw:i18n key="username_error" /></b></font>');
					$('#usernameMessage').fadeIn();
		
				} else if (res == "success") {
		
					$('#usernameMessage').html(
							'<img src="<mtw:contextPath />/images/chk.gif" />');
					$('#usernameMessage').fadeIn();
		
				} else if (res == "already") {
		
					$('#usernameMessage')
							.html(
									'<font color="red"><b><mtw:i18n key="username_already" /></b></font>');
					$('#usernameMessage').fadeIn();
				}
			}
		</script>
	
	</head>
	<body>
		<p>
		<table width="100%">
		<tr>
			<td align="left">
				<b><mtw:i18n key="registration" /></b>
			</td>
			<td align="right">
				<a href="<mtw:urlWithLoc loc="en" />"><img src="<mtw:contextPath />/images/usa.gif" border="0" width="20" height="13" /></a>
				<a href="<mtw:urlWithLoc loc="pt" />"><img src="<mtw:contextPath />/images/brazil.gif" border="0" width="20" height="13" /></a>
			</td>
		<tr>
		</table>
		</p>
		<form action="<mtw:contextPath />/User.add.mtw" method="post">
			<table>
				<tr>
					<td>
						<mtw:i18n key="username" />
					</td>
					<td>
						<mtw:input name="username" id="username" size="30" maxlength="30" />
						<span id="usernameLoading">
							<img src="<mtw:contextPath />/images/loading.gif" />
						</span>
						<span id="usernameMessage">
							<mtw:outError field="username">
								<font color="red">
									<b><mtw:out /></b>
								</font>
							</mtw:outError>
						</span>
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
					<td>
						<mtw:i18n key="passconf" />
					</td>
					<td>
						<mtw:input name="passconf" type="password" size="20" maxlength="20" /> 
							<mtw:outError field="passconf">
								<font color="red">
								<b><mtw:out /></b>
							</font>
						</mtw:outError>
					</td>
				</tr>
				<tr>
					<td>
						<mtw:i18n key="email" />
					</td>
					<td>
						<mtw:input name="email" size="30" maxlength="100" /> 
							<mtw:outError field="email">
							<font color="red">
								<b><mtw:out /></b>
							</font>
						</mtw:outError>
					</td>
				</tr>
				<tr>
					<td><mtw:i18n key="sexo" /></td>
						<td><mtw:select name="sexo" list="sexo"/> 
							<mtw:outError field="sexo">
								<font color="red"> <b><mtw:out /></b> </font>
							</mtw:outError>
						</td>
					</tr>
				<tr>
					<td>
						<mtw:i18n key="group" />
					</td>
					<td>
						<mtw:select name="groupId" list="groups" emptyField="true" />
						<mtw:outError field="groupId">
							<font color="red">
								<b><mtw:out /></b>
							</font>
						</mtw:outError>
					</td>
				</tr>
				<tr>
					<td>
						<mtw:i18n key="language" />
					</td>
					<td>
						<mtw:select name="languageId" list="languages" emptyField="true" /> 
							<mtw:outError field="languageId">
								<font color="red">
								<b><mtw:out /></b>
							</font>
						</mtw:outError>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<input type="submit" value="<mtw:i18n key="go" />" />
					</td>
				</tr>
	
			</table>
	
			<p>
				<a href="<mtw:contextPath />/jsp/login.jsp">
					<mtw:i18n key="register" />
				</a>
			</p>
	
		</form>
	</body>
</html>




