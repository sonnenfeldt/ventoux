<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
 <head>
  <meta charset="utf-8">

  <title>LAVisGrafix</title>
 
	<script type="text/javascript" src="http://www.sonnenfeldt.de/cit/js/jquery.js"></script>
	<script type="text/javascript" src="http://www.sonnenfeldt.de/cit/js/thickbox.js"></script>  

	<link rel="stylesheet" href="http://www.sonnenfeldt.de/cit/css/thickbox.css" type="text/css" media="screen" />	
	<link rel="stylesheet" href="http://www.sonnenfeldt.de/cit/css/cit.css" type="text/css" media="screen" />	
	
 </head>
 <body link="#ffffff" vlink="#ffffff" alink="#ccccff">
 <h1>Welcome to the LA Visualization Graphic Library!</h1>
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
			<a href="<c:url value="${asset.location}"/>" class="thickbox" title="${asset.description}"><img class="image" 
			src="<c:url value="${asset.thumbnailLocation}"/>" border=3 alt=""  height="200"></a>
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