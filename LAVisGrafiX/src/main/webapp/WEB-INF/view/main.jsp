<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<html>
 <head>
  <meta charset="utf-8">
  <meta name="_csrf" content="${_csrf.token}"/>
  <!-- default header name is X-CSRF-TOKEN -->
  <meta name="_csrf_header" content="${_csrf.headerName}"/>
  
  <title>LAVisGrafix</title>
 
	<link rel="stylesheet" href="http://www.sonnenfeldt.de/cit/css/thickbox.css" type="text/css" media="screen" />	
	<link rel="stylesheet" href="http://www.sonnenfeldt.de/cit/css/cit.css" type="text/css" media="screen" />	
	
 </head>
 <body background="http://www.sonnenfeldt.de/images/bg.jpg" bgcolor="#000000"
 link="#ffffff" vlink="#ffffff" alink="#ccccff" marginwidth="20"
 marginheight="20" topmargin="20" leftmargin="20">
 
 <h1>LAVisGrafix!</h1>
 <sec:authentication property="principal" var="user" />
${user.username } 
 <br>
 <button class="btn" id="upload">Upload ...</button>  <a class="btn" href="<c:url value="/logout" />" > Logout</a>
 <br>
 <br>
 <div align="center">  
  <table>
   <tr>	   
       <td id="Gallery">
	   <div align="center">

		<table id="GalleryTable" >
		<c:forEach items="${assets}" var="asset" varStatus="row">
		<tr>
			<a href="<c:url value="./assets/${asset.uuid}"/>" title="${asset.description}"><img class="image" src="<c:url value="${asset.thumbnailLocation}"/>" border=3 alt=""  height="200"></a>
		</tr>
		</c:forEach>
		<tr></tr>
		</table>
		
	   </div>
       </td>
  	   <td id="GalleryTableRefresh"> </td>

	</tr>
  </table>
  </div>
 </body>
</html>