<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
 <head>
  <meta charset="utf-8">
  <meta name="_csrf" content="${_csrf.token}"/>
  <!-- default header name is X-CSRF-TOKEN -->
  <meta name="_csrf_header" content="${_csrf.headerName}"/>
 
  <title>LAVisGrafix</title>
 
	<script type="text/javascript" src="http://www.sonnenfeldt.de/cit/js/jquery.js"></script>
	<script type="text/javascript" src="http://www.sonnenfeldt.de/cit/js/thickbox.js"></script>  

	<link rel="stylesheet" href="http://www.sonnenfeldt.de/cit/css/thickbox.css" type="text/css" media="screen" />	
	<link rel="stylesheet" href="http://www.sonnenfeldt.de/cit/css/cit.css" type="text/css" media="screen" />	

   <script language="javascript" type="text/javascript">
    function scale_images() {
		var viewportwidth;
       	var viewportheight;

       	if (typeof window.innerWidth != 'undefined') {
       		viewportwidth = window.innerWidth,
       		viewportheight = window.innerHeight
       	}
        else if (typeof document.documentElement != 'undefined' && 
        		typeof document.documentElement.clientWidth != 'undefined' && document.documentElement.clientWidth != 0) {
        	       viewportwidth = document.documentElement.clientWidth,
        	       viewportheight = document.documentElement.clientHeight
        	 	} else {
        	       viewportwidth = document.getElementsByTagName('body')[0].clientWidth,
        	       viewportheight = document.getElementsByTagName('body')[0].clientHeight
        	 	}
        	
            var images = document.images;
            var scale = 0.7;
            for (var i = 0; i < images.length; i++) {
                var image = images[i];
                var heightRatio = image.naturalHeight/viewportheight;
                var widthRatio = image.naturalWidth/viewportwidth;
                if (heightRatio > widthRatio) {
                	if (image.naturalHeight > viewportheight * scale) {
                		image.height = viewportheight * scale;
                	} 
                } else {
                	if (image.naturalWidth > viewportwidth * scale) {
                		image.width = viewportheight * scale;
                	}
                }
            }
                 
        }
    </script>
	
 
 </head>
 <body background="http://www.sonnenfeldt.de/images/bg.jpg" bgcolor="#000000"
 link="#ffffff" vlink="#ffffff" alink="#ccccff" marginwidth="20"
 marginheight="20" topmargin="20" leftmargin="20" onload="scale_images();" onresize="scale_images();" onorientationchange="scale_images();">

<table width="100%"> 
<tr>
<td valign="top">
 <h1>LAVisGrafix!</h1>
</td>
<td align="right">
<table>
<tr><td colspan="2" id="TextSmallBold" >System Information</td></tr>
<tr><td id="TextSmall">+ User: </td><td id="TextSmall">${user.username}</td> </tr>
<tr><td id="TextSmall">+ Name: </td><td id="TextSmall">${user.firstName} ${user.familyName}</td></tr>
<tr><td id="TextSmall">+ Roles: </td><td id="TextSmall">
<c:forEach items="${user.authorities}" var="authority" varStatus="row">
${authority.role}
</c:forEach>
</td>
</tr>
<tr><td id="TextSmall">+ Host Address: </td><td id="TextSmall">
<script type="text/javascript">
    var ip = location.host;
    document.write(ip);
</script>
</td></tr>
</table>
</td>
</tr>

<tr>
<td> </td>
<td align="right"><a class="btn" href="<c:url value="/logout" />" > Logout</a></td>
</tr>
<tr>
<td> </td>
<td align="right"><br></td>
</tr>
</table> 
	<form method="POST" action="./update/${asset.uuid}">
	
	<table border="0" style="width: 100%;">
  	<tbody>
    <tr>
    </tr>
    <tr>
      <td><!-- body -->
      <table  border="0" style="width: 100%;">
        <tbody>
          <tr>
            <td>
            
	          	<table border="0" style="width: 100%;">  
		          	<tbody>
		          	<tr>
		            <td style="text-align: center;" id="TextNormal"><!-- empty  -->
		 				<img alt="${asset.description}" border="3" src="${asset.location}" width="i" height=> <br> <!-- image comment --> 
		 			</td>
		 			</tr>
		 			<tr><td></td><td><br></td></tr>
		 			<tr>                
					<td id="TextNormal" style="text-align: center;"><input type="text" name="description" value="${asset.description}" size="120" />
					</td>
					</tr> 
					</tbody>           
	             </table>
          	</td>          
            <td id="TextNormal">
	          	<table border="0" style="width: 100%;">  
		          	<tbody>
		 			<tr>                
						<td>Keywords:</td><td> <input id="keywords" name="keywords" type="text" value="${asset.keywordNamesAsCSV}" size="35" /></td>
					</tr> 		          	
		 			<tr>                
						<td>Category:</td><td> 
						<select id="select_categories" name="category">
							<option value=""></option>
							<c:forEach items="${categories}" var="category" varStatus="row">
							<option value="${category.name}">${category.name}</option>
							</c:forEach>
						</select>
						
						<c:forEach items="${asset.categories}" var="category" varStatus="row">
							<script>
							function setMatchingValues(id, val)
							{ 
								selectObj = document.getElementById(id);
								selectObj.value = val;
								/*
								for(var i = 0; i < selectObj.length; i++)
							   	{
									if(selectObj.options[i].value == val) {
								  		selectObj.options[i].selected = true;							      		
									} else {
							      		selectObj.options[i].selected = false;																
									}
										
							   } */
							}
							setMatchingValues('select_categories',"${category.name}")
							</script>
						</c:forEach>
						</td>
					</tr> 		          	
		 			<tr>                
						<td>User Rating:</td><td> 
							 <select  id="select_rating" name="rating">
							 	<option value=""></option>
								<option value="5">5</option>
								<option value="4">4</option>
								<option value="3">3</option>
								<option value="2">2</option>
  								<option value="1">1</option>								  
							</select> 
							<script>
							function setSelectValue (id, val) {
							    document.getElementById(id).value = val;
							}
							setSelectValue('select_rating',"${asset.rating}")
							</script>
						</td>
					</tr> 		          	
					<tr>
						<td>
						</td><td><input style="text-align: right;"type="submit" value="Update" /></td>
					</tr>							
							
					</tbody>           
	             </table>
            <br>

+ <a href="../">back to top</a><br>
            </td>

          </tr>
        </tbody>
      </table>
      </td>
    </tr>
  </tbody>
</table>
 <input type="hidden"
    name="${_csrf.parameterName}"
    value="${_csrf.token}"/>
</form>
</body>
</html>