<%@page pageEncoding="UTF-8" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="mtw" uri="http://www.mentaframework.org/tags-mtw/"%>
 
<html>
<body>
<mtw:form action="Hello.hi.mtw" method="post">
  Type something: <mtw:input type="text" name="msg" size="30" maxlength="30" />
  <input type="submit" value="Go!" />
</mtw:form>
</body>
</html>
