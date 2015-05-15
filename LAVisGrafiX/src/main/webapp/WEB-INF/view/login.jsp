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
 <body background="http://www.sonnenfeldt.de/images/bg.jpg" bgcolor="#000000"
 link="#ffffff" vlink="#ffffff" alink="#ccccff" marginwidth="20"
 marginheight="20" topmargin="20" leftmargin="20">
 
<table width="100%"> 
<tr>
<td valign="top">
 <h1>LAVisGrafix!</h1>
</td>
<td align="right">
<table>
<tr><td colspan="2" id="TextSmallBold" >System Information</td></tr>
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
<td align="right"></td>
</tr>
</table> 

 <div align="center">  
  <table>
   <tr>	   
       <td id="Gallery">
	   <div align="center">
		
<form name='f' action='./login' method='POST'>
<table>
	<tr>
		<script>
			function checkForError() {
				var str = document.baseURI;
				if (str.contains("error")) {
					document.write("<td colspan=\"2\" id=\"TextError\"> Login failed. Please try again!<br></td>");
				} else
					{
					document.write("<td> <br> </td>");				
				}
			}
			checkForError();
		</script>		
	</tr>
	<tr><td></td><td><br></td></tr>
	<tr><td id="TextNormal">User:</td><td><input type='text' name='username' value=''></td></tr>
	<tr><td id="TextNormal">Password:</td><td><input type='password' name='password'/></td></tr>
	<tr><td></td><td><br></td></tr>
	<tr><td/><td align="right"><input class="btn" name="submit" type="submit" value="Login"/></td></tr>
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</table>
</form>		
	   </div>
       </td>
  	   <td id="GalleryTableRefresh"> </td>

	</tr>
  </table>
  </div>
 </body>
</html>