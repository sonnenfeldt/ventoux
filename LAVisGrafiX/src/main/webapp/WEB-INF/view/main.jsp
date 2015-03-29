<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>

	<head>
		<meta charset="utf-8">
		<title>Welcome to the LA Visualization Graphic Library!</title>
	</head> 
	
	<body>
		<h2>Welcome to the LA Visualization Graphic Library!</h2>
	
	<c:forEach items="${assets}" var="asset" varStatus="row">
		<a href="<c:url value="${assets.location}"/>">${assets.description}</a>
	</c:forEach>


	</body>

</html>