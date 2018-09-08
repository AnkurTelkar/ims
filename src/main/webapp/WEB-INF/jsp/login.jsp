<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<tiles:importAttribute name="stylesheets" />
<tiles:importAttribute name="javascripts" />
<html >
    <head>
        <meta charset="UTF-8">
        <title>Login</title>
  <c:forEach var="stylesheet" items="${stylesheets}">
   <link rel="stylesheet" href="<c:url value="${stylesheet}"/>">
  </c:forEach>
  
  <script type="text/javascript">
  
  function work(){
		 $("#welcoming").fadeOut(10000);
		 sleep(5000);
		 $("#checking").fadeIn(20000);
		 
		 sleep(5000);
		 $('form').fadeOut(500);
		 
		 return true;
  }
  
  </script>
  
  
  
    </head>
    <body>

  <div class="wrapper">
	<div class="container">
	
	<div id="welcoming">
		<h1>K.N Sales</h1>
	</div>
		<div id="checking" style="display: none;">
		<h1>Please wait...</h1>
		</div>
		<c:url var="postLogin" value="/postLogin.htm"/>
		<c:url var="postLogin" value="/postLogin.htm"/>
            <form:form method="post" name="loginForm" class="form" action="${postLogin}">
                <div class="alert alert-warning">${message}</div>
                <input class="input" id="username" name="username" type="text" placeholder="Login Id" required="required" value="${loginId}" >
                <input class="input" id="password" name="password" type="password" placeholder="Password" required="required" value="${password}" >
                <button type="submit" id="login-button" onclick="return work();">Login</button>  <!-- onclick="work()" -->
            </form:form>
		
	</div>
	
	<ul class="bg-bubbles">
		<li></li>
		<li></li>
		<li></li>
		<li></li>
		<li></li>
		<li></li>
		<li></li>
		<li></li>
		<li></li>
		<li></li>
	</ul>
</div>
 <!--  <script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script> -->

</body>
</html>

<c:forEach var="javscript" items="${javascripts}">
 <script src="<c:url value="${javscript}"/>"></script>
</c:forEach>