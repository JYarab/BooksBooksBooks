<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<!-- CSS only -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-F3w7mX95PdgyTmZZMECAngseQB83DfGTowi0iMjiWaeVhAn4FJkqJByhZMI3AhiU" crossorigin="anonymous">
<!-- local stylesheet -->
<link href="/css/style.css" type="text/css" rel="stylesheet">
<link rel="shortcut icon" href="https://img.icons8.com/external-itim2101-lineal-color-itim2101/64/000000/external-book-back-to-school-itim2101-lineal-color-itim2101.png" />

<script src="https://kit.fontawesome.com/665249abe1.js" crossorigin="anonymous"></script>
<script src="/js/scripts.js"></script>
<title>Login</title>
</head>
<body onload="get_quote_of_the_day()">
<div class="container">
	<div class="d-flex justify-content-center h-100">
		<div class="card">
			<div class="card-header">
				<h3>Sign In</h3>
				<div class="d-flex justify-content-center book_icon">
					<span><i class="fas fa-book-reader"></i></span>
				</div>
				<div id="quote" class="text-center"></div>
    			<div id="author" class="text-end"></div>
   				<div class="text-end">
	   				<span style="z-index:50;font-size:0.9em; font-weight: bold;">
	        			<img src="https://theysaidso.com/branding/theysaidso.png" height="20" width="20" alt="theysaidso.com"/>
	        			<a href="https://theysaidso.com" title="Powered by quotes from theysaidso.com" style="color: #ccc; margin-left: 4px; vertical-align: middle;">
	          			They Said So®
	        			</a>
	  				</span>
  				</div>
			</div>
			<div class="card-body">
				<p class="text-danger"><c:out value="${loginError}" /></p>
				<form action="/login" method="post" class="mt-3">
					<div class="input-group mb-3">						
						<span class="input-group-text input-icon justify-content-center"><i class="fas fa-envelope"></i></span>
						<input type="email" name="email" class="form-control" placeholder="Email...">						
					</div>
				
					<div class="input-group mb-3">
						<span class="input-group-text input-icon justify-content-center"><i class="fas fa-lock"></i></span>
            			<input type="password" class="form-control" id="password" name="password" placeholder="Password..."/>
        			</div>
					<div class="text-center">
			        	<button type="submit" class="btn btn-login mb-3">Login</button>
			        </div>
				</form>				
			</div>
			<div class="card-footer">
				<div class="d-flex justify-content-center">
					<span>Don't have an account? <a href="/">Sign Up</a></span>
				</div>
			</div>
		</div>
	</div>
</div>
</body>
</html>