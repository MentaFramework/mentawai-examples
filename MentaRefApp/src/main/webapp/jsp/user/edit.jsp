<%@ page contentType="text/html; charset=UTF-8" %> 
<%@taglib prefix="mtw" uri="http://www.mentaframework.org/tags-mtw/" %>

<mtw:useI18N prefix="user.edit" />

<mtw:requiresAuthentication />

<html>
	<head>
		
		<title><mtw:i18n key="title" noPrefix="true" /></title>
		
		<script type="text/javascript" src="<mtw:contextPath />/jquery/jquery-1.4.4.min.js"></script>
		
		<script type="text/javascript"> 
		 
		$(document).ready(function() {
			
			$('#usernameLoading').hide();
		
			$('#username').blur(function() {
				  
				  $('#usernameMessage').hide();
		
				  if ($('#username').val().trim() != '') {
		
					  $('#usernameLoading').show();
			
					  if ($('#username').val() == '<mtw:out value="user.username" />') {
						    $('#usernameMessage').fadeOut();
				       	    setTimeout("usernameResult('same')", 400);
				       	    return false;		
					  }
					
				      $.post("<mtw:contextPath />/User.check.mtw", { username: $('#username').val() }, function(response) {
				        	$('#usernameMessage').fadeOut();
				        	setTimeout("usernameResult('" + escape(response) + "')", 400);
				      });
		
				  }
		
				  return false;
			});
		});
		
		function usernameResult(response) {
		
			$('#usernameLoading').hide();
			
			var res = unescape(response);
		
			if (res == "error") {
		
				$('#usernameMessage').html('<font color="red"><b><mtw:i18n key="username_error" /></b></font>');
				$('#usernameMessage').fadeIn();
		
			} else if (res == "same") {
		
				$('#usernameMessage').html('');
				$('#usernameMessage').fadeIn();
				
		
			} else if (res == "success") {
		
				$('#usernameMessage').html('<img src="<mtw:contextPath />/images/chk.gif" />');
				$('#usernameMessage').fadeIn();
		
			} else if (res == "already") {
				
				$('#usernameMessage').html('<font color="red"><b><mtw:i18n key="username_already" /></b></font>');
				$('#usernameMessage').fadeIn();
			}
		}
		
		</script>
		
	</head>
	
	<body>
		
		<h3><mtw:i18n key="account" /></h3>

		<form action="<mtw:contextPath />/User.edit.mtw" method="post">
			<mtw:bean value="user">
				<table>
	
					<mtw:input type="hidden" name="id" />
					<tr>
						<td><mtw:i18n key="username" /></td>
						<td>
							<mtw:input name="username" size="30" maxlength="30" id="username" /> 
							<span id="usernameLoading">
								<img src="<mtw:contextPath />/images/loading.gif" /> 
							</span> 
							<span id="usernameMessage">
								<mtw:outError field="username">
									<font color="red"><b><mtw:out /></b></font>
								</mtw:outError>
							</span>
						</td>
					</tr>
					<tr>
						<td><mtw:i18n key="email" /></td>
						<td><mtw:input name="email" size="30" maxlength="100" /> 
							<mtw:outError field="email">
								<font color="red"> <b><mtw:out /></b> </font>
							</mtw:outError>
						</td>
					</tr>
					<tr>
						<td><mtw:i18n key="group" /></td>
						<td>
							<mtw:select name="groupId" list="groups" />
							<mtw:outError field="groupId">
								<font color="red"> <b><mtw:out /></b> </font>
							</mtw:outError></td>
					</tr>
					<tr>
						<td><mtw:i18n key="language" /></td>
						<td><mtw:select name="languageId" list="languages" />
							<mtw:outError field="languageId">
								<font color="red"> <b><mtw:out /></b> </font>
							</mtw:outError>
						</td>
					</tr>
	
					<tr>
						<td colspan="2">
							<input type="submit" value="<mtw:i18n key="save" />" />
						</td>
					</tr>
				</table>
			</mtw:bean>
		</form>

		<p>
			<a href="<mtw:contextPath />/jsp/index.jsp"><mtw:i18n key="back" /></a>
		</p>
	
	</body>
</html>



