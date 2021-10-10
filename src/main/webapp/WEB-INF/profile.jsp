<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<!-- CSS only -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-F3w7mX95PdgyTmZZMECAngseQB83DfGTowi0iMjiWaeVhAn4FJkqJByhZMI3AhiU" crossorigin="anonymous">
<!-- local stylesheet -->
<link href="/css/style.css" type="text/css" rel="stylesheet">
<script src="https://kit.fontawesome.com/665249abe1.js" crossorigin="anonymous"></script>
<link rel="shortcut icon" href="https://img.icons8.com/external-itim2101-lineal-color-itim2101/64/000000/external-book-back-to-school-itim2101-lineal-color-itim2101.png" />
<meta charset="ISO-8859-1">
<title>${user.name}'s Profile </title>
</head>
<body>
	<div class="container">
		<div class="d-flex justify-content-center h-100">
			<div class="card">
				<div class="card-header">
					<h3>Profile</h3>
					<div class="d-flex justify-content-center book_icon">
						<span><i class="fas fa-book-reader"></i></span>
					</div>
				</div>
				
				<div class="card-body">
					<form:form method="POST" action="/profile/update" modelAttribute="user">
						<form:hidden path="id" value="${user.id}"/>
						<div class="input-errors">
							<form:errors class="text-danger" path="name"/>
						</div>
						<div class="input-group mb-3">						
							<span class="input-group-text input-icon justify-content-center"><i class="fas fa-address-card"></i></span>
							<form:input class="form-control" path="name" value="${user.name}"/>						
						</div>
						
						<div class="input-errors">
							<form:errors class="text-danger" path="email"/>
						</div>
						<div class="input-group mb-3">						
							<span class="input-group-text input-icon justify-content-center"><i class="fas fa-envelope"></i></span>
							<form:input class="form-control" type="email" path="email" value="${user.email}"/>						
						</div>
	
						<div class="text-center">
				        	<button type="submit" class="btn btn-login mb-3">Update</button>
				        </div>
					</form:form>				
				</div>
	
				<div class="card-footer">
					<div class="d-flex justify-content-center">
<%-- 						<form:form>
							<div class="input-errors">
								<form:errors class="text-danger" path="password"/>
							</div>
							<div class="input-group mb-3">						
								<span class="input-group-text input-icon justify-content-center"><i class="fas fa-lock"></i></span>
								<form:password class="form-control" path="password" placeholder="Password..."/>						
							</div>
							
							<div class="input-group mb-3">						
								<span class="input-group-text input-icon justify-content-center"><i class="fas fa-lock"></i></span>
								<form:password class="form-control" path="passwordConfirmation" placeholder="Confirm Password..."/>						
							</div>
						</form:form> --%>
					</div>
				</div>
			</div>	
		</div>	
	</div>
</body>
</html>