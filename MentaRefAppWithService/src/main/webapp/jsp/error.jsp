<%@ page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="mtw" uri="http://www.mentaframework.org/tags-mtw/"%>

<html>
	<head>
		<title>Error</title>
	</head>
	
	<body>
	
		<h2>Error:</h2>
	
		<h3>
			<mtw:out value="message" />
		</h3>
	
		<h4>
			<mtw:out value="exception" />
		</h4>
	
		<h5>
			<mtw:out value="stackheader" />
		</h5>
	
		<mtw:out value="stacktrace" />
	
	</body>

</html>
