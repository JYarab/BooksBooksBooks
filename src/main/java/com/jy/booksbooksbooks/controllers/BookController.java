package com.jy.booksbooksbooks.controllers;

import java.util.HashMap;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jy.booksbooksbooks.models.Author;
import com.jy.booksbooksbooks.models.Book;
import com.jy.booksbooksbooks.models.User;
import com.jy.booksbooksbooks.models.UserBook;
import com.jy.booksbooksbooks.services.AuthorService;
import com.jy.booksbooksbooks.services.BookService;
import com.jy.booksbooksbooks.services.UserBookService;
import com.jy.booksbooksbooks.services.UserService;

@Controller
@RequestMapping("/myBooks")
public class BookController {

	@Autowired
	  RestTemplate restTemplate;
	  
	  @Autowired
	  private UserService uService;
	  
	  @Autowired
	  private UserBookService uBookService;
	  
	  @Autowired
	  private BookService bService;
	  
	  @Autowired
	  private AuthorService aService;
	  
	  @GetMapping("")
	    public String addBook(@RequestParam(required = false, value="isbn") String isbn, @RequestParam(required = false, value="search") String search, Model viewModel, @ModelAttribute UserBook userBook, HttpSession session) throws JsonMappingException, JsonProcessingException {
		  if(session.getAttribute("loggedInUser") != null) {
			  session.removeAttribute("searchBook"); 			  
			  viewModel.addAttribute("usersBooks", uService.findUserById((Long) session.getAttribute("loggedInUser")).getUsersBooks());
			  if(search != null) {
				  RestTemplate restTemplate = new RestTemplate();
				  String resp = restTemplate.getForObject("https://openlibrary.org/api/books?bibkeys=ISBN:" + isbn + "&jscmd=data&format=json", String.class);
				 
				  final ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				  final JsonNode jsonNode = objectMapper.readTree(resp);
				  final JsonNode result = jsonNode.get("ISBN:"+isbn);

				  final Book searchBook = objectMapper.treeToValue(result, Book.class);
				  
				  if (searchBook == null) {
					  viewModel.addAttribute("noResults", "No Results Found");
					  session.removeAttribute("searchBook");
					  return "myBooks.jsp";
				  } else {
					  if(bService.existsByIsbn(searchBook)) {
						  System.out.println("Controller - book exists in DB");
						  Book existingBook = bService.findByIsbn(searchBook.getIsbn10(), searchBook.getIsbn13());
						  User loggedUser = uService.findUserById((Long) session.getAttribute("loggedInUser"));
						  viewModel.addAttribute("bookOwners", uBookService.findByBookAndOwned(existingBook, true));
						  viewModel.addAttribute("bookRating", uBookService.totalRatingForBook(existingBook));
						  viewModel.addAttribute("numberOfRatings", uBookService.numberOfRatings(existingBook));
						  viewModel.addAttribute("currentlyReading", uBookService.currentlyReading(existingBook));
						  viewModel.addAttribute("completedReading", uBookService.completedReading(existingBook));
						  if(uBookService.containsBook(loggedUser, existingBook)) {
							  System.out.println("Book already on list");
							  viewModel.addAttribute("bookOnList", "true");
							 }
							 session.setAttribute("searchBook", existingBook);
							 
						 } else {
							 session.setAttribute("searchBook", searchBook);
						 	}
				  	}
				 return "myBooks.jsp";
			  } else {
				  return "myBooks.jsp";
			  }

		  } else {
			  return "redirect:/";
		  }
	  }
	 
	 

	@PostMapping("/addBook")
	 	public String addBook(@Valid @ModelAttribute("userBook") UserBook userBook, BindingResult result, HttpSession session){
				
		 Book book = (Book) session.getAttribute("searchBook");
		 Boolean bookInDB = false;
		 Book existingBook = bService.findByIsbn(book.getIsbn10(), book.getIsbn13());
		 
		// Book existingBook = bService.findByIsbn(book);
		 	if(existingBook == null) {
				 Book newBook = bService.addBookToDB(book);
				 userBook.setBook(newBook);
		 	} else {
		 		bookInDB = true;
		 		userBook.setBook(existingBook);
		 	}
		 
		 //Create new user and book relationship	
		 uBookService.AddBookToList(userBook);
		 
		 //If new book, check authors in search author list, create if new
		 if(!bookInDB) {
			 for(HashMap<String, String> auth : book.getSearchAuthors()) {
				 Author author = new Author();
				 author.setName(auth.get("name"));
				 author.setUrl(auth.get("url"));
				
			 //check if author is already in db and set relationship to added book
				 if(aService.existsByName(author.getName())){
					 Author existingAuthor = aService.findByName(author.getName());
					 //check if author is already linked to book if not set relationship
					 if(aService.bookIsLinked(existingAuthor, userBook.getBook())) {
						 continue;
					 } else {
						 aService.addBook(existingAuthor, userBook.getBook());
					 }				 
				 } else {
					Author newAuthor = aService.addAuthor(author);
					aService.addBook(newAuthor, userBook.getBook());
				 }
			 }
		 }
		 session.removeAttribute("searchBook");
		 return "redirect:/myBooks";
	 }

	@GetMapping("/{id}")
		public String viewBook(@PathVariable(value="id") Long id, @ModelAttribute("userBook") UserBook userBook, @ModelAttribute("newUserBook") UserBook newUserBook, HttpSession session, Model viewModel) {
		Book book = bService.findById(id);
		User loggedUser = uService.findUserById((Long) session.getAttribute("loggedInUser"));
		viewModel.addAttribute("bookRating", uBookService.totalRatingForBook(book));
		viewModel.addAttribute("book", book);
		viewModel.addAttribute("userBook", uBookService.findByUserAndBook(loggedUser ,book));
		viewModel.addAttribute("bookOwners", uBookService.findByBookAndOwned(book, true));
		viewModel.addAttribute("numberOfRatings", uBookService.numberOfRatings(book));
		viewModel.addAttribute("currentlyReading", uBookService.currentlyReading(book));
		viewModel.addAttribute("completedReading", uBookService.completedReading(book));
		if(uBookService.containsBook(loggedUser, book)) {
			  viewModel.addAttribute("bookOnList", "true");
			 }
		return "viewBook.jsp";
	}
	
	@PostMapping("/ub/{id}")
		public String editBookStatus(@PathVariable(value="id") Long id, @Valid @ModelAttribute("userBook") UserBook userBook, BindingResult result, HttpSession session) {
			uBookService.update(userBook);
			session.removeAttribute("searchBook");
		return "redirect:/myBooks";
	}
	
	@PostMapping("/{id}/remove")
	public String removeBook(@PathVariable(value="id") Long id, HttpSession session) {
		uBookService.removeFromList(uBookService.findById(id));
	return "redirect:/myBooks";
}
	
	
}
