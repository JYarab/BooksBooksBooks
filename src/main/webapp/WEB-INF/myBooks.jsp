<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<!-- CSS only -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-F3w7mX95PdgyTmZZMECAngseQB83DfGTowi0iMjiWaeVhAn4FJkqJByhZMI3AhiU" crossorigin="anonymous">
<script src="https://kit.fontawesome.com/665249abe1.js" crossorigin="anonymous"></script>
<link rel="shortcut icon" href="https://img.icons8.com/external-itim2101-lineal-color-itim2101/64/000000/external-book-back-to-school-itim2101-lineal-color-itim2101.png" />
<!-- local stylesheet -->
<link href="/css/book-style.css" type="text/css" rel="stylesheet">
<title>My Books</title>
</head>
<body>
	<div class="container">
	
		<div class="row text-center">
			<div class="col">
				<p><a href="#">Home</a> | <a href="/myBooks">My Books</a> | <a href="/profile">Profile</a> | <a href="/logout">Logout</a>
			</div>
		</div>
	
		<div class="row text-center justify-content-center">
			<div class="col-10">
				<form action="/myBooks">
	  				<div class="mb-3">
	    				<label for="isbn" class="form-label">Search For Books by ISBN</label>
	    				<input type="text" class="form-control" id="isbn" name="isbn">
	    				<input type="hidden" name="search"/>
	  				</div>
	  				<button type="submit" class="btn btn-primary">Submit</button>
				</form>
			</div>
		</div>
		
		<div class="row mt-3 border-top border-2"></div>
		<h2>${noResults}</h2>
		<c:if test = "${searchBook != null}">
			<div class="row mt-3 justify-content-center">
				<div class="col-4 d-flex justify-content-end">
					<img src="${searchBook.cover_md}" alt="" />
				</div>
				
				<div class="col-8">
					<h2 class="text-capitalize">${searchBook.title}</h2>
					<p>Author(s):
						<c:forEach items="${searchBook.searchAuthors}" var="author">
							<a href="${author.url}">${author.name}</a>, 
						</c:forEach>
					</p>
					<p>ISBN 10: 
						<c:forEach items="${searchBook.isbn10}" var="isbn10">		
							${isbn10}, 
						</c:forEach>
					</p>
					<p>ISBN 13: 
						<c:forEach items="${searchBook.isbn13}" var="isbn13">		
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
      					<c:when test="${searchBook.usersBooks.size() != null}">
      						On ${searchBook.usersBooks.size()} user(s) list.
      				<p>
      					Owned by: ${bookOwners.size()}
      				</p>
      				<p>      				
      					${currentlyReading.size()} are reading, ${completedReading.size()} have finished.
      				</p>
      					</c:when>
      					<c:otherwise>
      						No users have added this book
      					</c:otherwise>
      					</c:choose>      						
      				</p>
      				
				</div>
				<div class="row mt-3 text-center">
					<c:choose>
						<c:when test="${bookOnList != true}">
							
							<form:form method="POST" action="/myBooks/addBook" modelAttribute="userBook" class="row gx-3 gy-2 align-items-center justify-content-center">
							  
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
							<div class="row mb-3 border-top border-2"></div>
				   		</c:when>
					    <c:otherwise>
					    	<h3>This book is already on your list</h3>
					    	<div class="row mb-3 border-top border-2"></div>
					    </c:otherwise>
				    </c:choose>
	  			</div>
	  		</div>
  		</c:if>
  		
		<div class="row">
<%-- 			<table class="table">
				<thead>
				    <tr>
				      <th scope="col">Cover</th>
				      <th scope="col">Title</th>
				      <th scope="col">Author</th>
				      <th scope="col">On Page</th>
				      <th scope="col">ISBNs</th>
				      <th scope="col">Own</th>
				      <th scope="col">Actions</th>
				    </tr>
			  </thead>
			  <tbody>
				  <c:forEach items="${usersBooks}" var="usersBook">
				    <tr>
				      <td><img src="${usersBook.book.cover_sm}" alt="" /></td>
				      <td class="text-capitalize"><a href="/myBooks/${usersBook.book.id}">${usersBook.book.title}</a></td>
				      <td>
				      	<c:forEach items="${usersBook.book.authors}" var="author">
				      		${author.name}
				      	</c:forEach>
				      </td>
				      <td>
					    <c:choose>
					    	<c:when test="${usersBook.completed}">
					      		Completed
					      	</c:when>
					      	<c:otherwise>
					      		<c:choose>
					      			 <c:when test="${usersBook.current_page == null || usersBook.current_page == 0}">
						        			Not Started
						        	</c:when>
						        	<c:otherwise>
									    	On Page: ${usersBook.current_page}
									    </c:otherwise>
					      		</c:choose>
					      	</c:otherwise>
					  	</c:choose>
				      </td>
				      <td>
				      	<c:forEach items="${usersBook.book.isbn10}" var="isbn10">
				      		${isbn10}, 
				      	</c:forEach>
				      	<c:forEach items="${usersBook.book.isbn13}" var="isbn13">
				      		${isbn13}, 
				      	</c:forEach>  
				      </td>
				      <td>
				      	<c:choose> 
					      	<c:when test="${usersBook.owned == true}">
					        	<img class="own-icon" src="https://img.icons8.com/emoji/48/000000/check-mark-button-emoji.png"/>
					        </c:when>
					        <c:otherwise>
							 	<img class="own-icon" src="https://img.icons8.com/emoji/48/000000/cross-mark-button-emoji.png"/>
							</c:otherwise>
				      	</c:choose>				      
				      </td>
				      <td>
				      	<form action="/myBooks/${usersBook.id}/remove" method="post">
				      		<button>Remove</button>				      	
				      	</form>
				      </td>
				    </tr>
				    </c:forEach>
			  </tbody>
			</table> --%>
				<div class="row justify-content-around">
				<c:forEach items="${usersBooks}" var="usersBook">
					<div class="card mb-2" style="max-width: 425px;">
					  <div class="row g-0">
					    <div class="col-md-4 align-self-center">
					      <img src="${usersBook.book.cover_md}" class="img-fluid rounded-start" alt="...">
					    </div>
					    <div class="col-md-8">
					      <div class="card-body">
					        <h5 class="card-title text-capitalize"><a href="/myBooks/${usersBook.book.id}">${usersBook.book.title}</a></h5>
					        <p class="card-text"> Author(s): 
					        	<c:forEach items="${usersBook.book.authors}" var="author">
				      				${author.name}
				      			</c:forEach></p>
				      		<p class="card-text">Owned: 
				      			<c:choose> 
							      	<c:when test="${usersBook.owned == true}">
							        	<img class="own-icon" src="https://img.icons8.com/emoji/48/000000/check-mark-button-emoji.png"/> - 
							        </c:when>
							        <c:otherwise>
									 	<img class="own-icon" src="https://img.icons8.com/emoji/48/000000/cross-mark-button-emoji.png"/> - 
									</c:otherwise>
				      			</c:choose>					      			

			      				<c:choose>
				    				<c:when test="${usersBook.completed}">
				      					Completed
				      				</c:when>
				      				<c:otherwise>
					      			<c:choose>
					      			 	<c:when test="${usersBook.current_page == null || usersBook.current_page == 0}">
						        			Not Started
						        		</c:when>
						        	<c:otherwise>
									    	On Page: ${usersBook.current_page}
									</c:otherwise>
					      			</c:choose>
					      			</c:otherwise>
				  				</c:choose>				      			
			      			</p>
			      			
					        <p class="card-text"><small class="text-muted">ISBNs: 
					        	<c:forEach items="${usersBook.book.isbn10}" var="isbn10">
				      				${isbn10}, 
				      			</c:forEach>
				      			<c:forEach items="${usersBook.book.isbn13}" var="isbn13">
				      				${isbn13}, 
				      			</c:forEach>					        
					        </small></p>					        
					        	<form action="/myBooks/${usersBook.id}/remove" method="post" class="text-end">
						      		<button type="submit" class="btn btn-secondary btn-sm"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-trash" viewBox="0 0 16 16">
		  							<path d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0V6z"/>
		  							<path fill-rule="evenodd" d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1v1zM4.118 4 4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4H4.118zM2.5 3V2h11v1h-11z"/>
									</svg></button>				      	
						      	</form>					        
					      </div>
					    </div>
					  </div>
					</div>
				</c:forEach>
				</div>			
		</div>			    
	</div>

</body>
</html>