<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
<head>
<!-- CSS only -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-F3w7mX95PdgyTmZZMECAngseQB83DfGTowi0iMjiWaeVhAn4FJkqJByhZMI3AhiU" crossorigin="anonymous">
<script src="https://kit.fontawesome.com/665249abe1.js" crossorigin="anonymous"></script>
<link rel="shortcut icon" href="https://img.icons8.com/external-itim2101-lineal-color-itim2101/64/000000/external-book-back-to-school-itim2101-lineal-color-itim2101.png" />
<!-- local stylesheet -->
<link href="/css/book-style.css" type="text/css" rel="stylesheet">
<meta charset="ISO-8859-1">
<title>${book.title}</title>
</head>
<body>
	<div class="container">
		<div class="row text-center">
			<div class="col">
				<p><a href="#">Home</a> | <a href="/myBooks">My Books</a> | <a href="/profile">Profile</a> | <a href="/logout">Logout</a>
			</div>
		</div>
	
		<div class="row mt-3 justify-content-center">
				<div class="col-4 d-flex justify-content-end">
					<img src="${book.cover_md}" alt="" />
				</div>
				
				<div class="col-8">
					<h2 class="text-capitalize">${book.title}</h2>
					<p>Author(s):
						<c:forEach items="${book.authors}" var="author">
							<a href="${author.url}">${author.name}</a>, 
						</c:forEach>
					</p>
					<p>ISBN 10: 
						<c:forEach items="${book.isbn10}" var="isbn10">		
							${isbn10}, 
						</c:forEach>
					</p>
					<p>ISBN 13: 
						<c:forEach items="${book.isbn13}" var="isbn13">		
							${isbn13}, 
						</c:forEach>
					</p>
					<p>User Rating:
 						<c:choose>
 							<c:when test="${bookRating == 'NaN' || bookRating == null}">
 								None
 							</c:when>
 							<c:otherwise>
		 						<c:set var="rating" scope="session" value="${bookRating}"/>
								<%float rating = (float) session.getAttribute("rating");%>
		 						<%for ( int stars = 1; stars <= 5; stars++){ 
									if(rating >= 1){
										out.println("<i class='fas fa-star'></i>");
									} else if (rating >= 0.5){
										out.println("<i class='fas fa-star-half-alt'></i>");
									} else {
										out.println("<i class='far fa-star'></i>");
									}
		 							rating --;
		 						}%>
 								${bookRating}/5 (From ${numberOfRatings} Ratings)
 							</c:otherwise>
 						</c:choose>
      				</p>
      				<p> 
      					<c:choose>
      					<c:when test="${book.usersBooks.size() != null}">
      						On ${book.usersBooks.size()} user(s) list.
      					</c:when>
      					<c:otherwise>
      						No users have this book
      					</c:otherwise>
      					</c:choose>        						
      				</p>
      				<p>
      					Owned by: ${bookOwners.size()}
      				</p>
      				<p>      				
      					${currentlyReading.size()} are reading, ${completedReading.size()} have finished.
      				</p>
				</div>
				<div class="row mt-3 text-center">
					<c:choose>
						<c:when test="${bookOnList == true}">
							
							<form:form method="POST" action="/myBooks/ub/${userBook.id}" modelAttribute="userBook" class="row gx-3 gy-2 align-items-center justify-content-center">
								<form:hidden path="user" value="${userBook.user.id}"/>
								<form:hidden path="book" value="${userBook.book.id}"/>
							  <div class="col-sm-3">
							  	<div class="input-group">						
									<span class="input-group-text input-icon justify-content-center"><i class="fas fa-bookmark"></i></span>
									<form:input class="form-control" path="current_page" value="${userBook.current_page}"/>						
								</div>
							  </div>
							  
							  <div class="col-sm-3">
								<div class="form-check-inline form-switch">
									<c:choose>
										<c:when test="${userBook.owned}">
				  							<form:checkbox path="owned" class="form-check-input" role="switch" checked="checked"/>
				  						</c:when>
				  						<c:otherwise>
				  							<form:checkbox path="owned" class="form-check-input" role="switch"/>
				  						</c:otherwise>
			  						</c:choose>
			  						<form:label path="owned" class="form-check-label">Owned</form:label>
								</div>
							  </div>
							  
							  <div class="col-sm-3">
								<div class="form-check-inline form-switch">
			  						<c:choose>
										<c:when test="${userBook.completed}">
				  							<form:checkbox path="completed" class="form-check-input" role="switch" checked="checked"/>
				  						</c:when>
				  						<c:otherwise>
				  							<form:checkbox path="completed" class="form-check-input" role="switch"/>
				  						</c:otherwise>
			  						</c:choose>
			  						<form:label path="completed" class="form-check-label">Completed</form:label>
								</div>
							  </div>
								<div class="mt-3 mb-3 d-flex">
									<div class="col"></div>
									<div class="col">
						        	<button type="submit" class="btn btn-primary">Update</button>
						        	</div>
						        	<div class="col text-end">
						        	<button formaction="/myBooks/${userBook.id}/remove" class="btn btn-danger">Remove</button>
									</div>
						       </div>
						       
						       <div class="row text-start">
						       		<h2>Your Review</h2>
						       </div>
						       <div class="row">
						       		<form:textarea path="review" class="form-control" rows="8" value="userBook.review"/>						       
						       </div>
						       <div class="mt-3">						       		
							       		<div class="input-group" style="width: 100px;">
											<span class="input-group-text input-icon justify-content-center"><i class="fas fa-star"></i></span>
											  <form:select class="form-select" path="rating">
											    <option <c:if test="${userBook.rating == '1'}">selected="selected"</c:if> value=1>1</option>
											    <option <c:if test="${userBook.rating == '2'}">selected="selected"</c:if> value=2>2</option>
											    <option <c:if test="${userBook.rating == '3'}">selected="selected"</c:if> value=3>3</option>
											    <option <c:if test="${userBook.rating == '4'}">selected="selected"</c:if> value=4>4</option>
											    <option <c:if test="${userBook.rating == '5'}">selected="selected"</c:if> value=5>5</option>
											  </form:select>
											  
										</div>										
										<div class="text-end">
							        		<button type="submit" class="btn btn-primary">Submit / Update</button>
							        	</div>	
							  </div>
						</form:form>
											
				   		</c:when>
					    <c:otherwise>
					    	<h3>This book is not on your list</h3>
					    	
					    	
					    	<form:form method="POST" action="/myBooks/addBook" modelAttribute="newUserBook" class="row gx-3 gy-2 align-items-center justify-content-center">
							  
						  <div class="col-sm-3">
							  	<div class="input-group">						
									<span class="input-group-text input-icon justify-content-center"><i class="fas fa-bookmark"></i></span>
									<form:input class="form-control" path="current_page" placeholder="Current Page"/>						
								</div>
							  </div>
							  
							  <div class="col-sm-3">
								<div class="form-check-inline form-switch">
			  						<form:checkbox path="owned" class="form-check-input" role="switch"/>
			  						<form:label path="owned" class="form-check-label">Owned</form:label>
								</div>
							  </div>
							  
							  <div class="col-sm-3">
								<div class="form-check-inline form-switch">
			  						<form:checkbox path="completed" class="form-check-input" role="switch"/>
			  						<form:label path="completed" class="form-check-label">Completed</form:label>
								</div>
							  </div>
							  <form:hidden path="user" value="${loggedInUser}"/>
								<div class="mt-3 mb-3">
						        	<button type="submit" class="btn btn-primary">Add</button>
						        </div>
						        
							</form:form>
					    	
					    	
					    </c:otherwise>
				    </c:choose>
	  			</div>
	  			<div class="row mt-3 border-top border-2"></div>
	  			
	  			<div class="mt-3">
	  				<h4>What Other User's Say About: <span class="text-capitalize">${book.title}</span></h4>
	  			</div>
	  			
	  			<div class="mt-3 reviews-box">
	  				<c:forEach items="${book.usersBooks}" var="usersBooks">		
						<c:if test="${usersBooks.review.length() > 0}">
							<c:if test="${usersBooks.user.id != loggedInUser}">
								<div class="d-flex bd-highlight border border-3 rounded-end">
									<div class="p-2 bd-highlight border-end">
										<h5>${usersBooks.user.displayName}</h5>
										<div>${usersBooks.user.readerType}</div>
										<div>Rating: ${usersBooks.rating} <i class='fas fa-star'></i></div>
									</div>
									<div class="p-2 flex-fill bd-highlight">
										${usersBooks.review}
									</div>								
								</div>
							</c:if>
						</c:if> 
					</c:forEach>
	  			
	  			</div>
	  		</div>
	</div>
</body>
</html>