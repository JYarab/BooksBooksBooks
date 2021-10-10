package com.jy.booksbooksbooks.services;


import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jy.booksbooksbooks.models.Author;
import com.jy.booksbooksbooks.models.Book;
import com.jy.booksbooksbooks.repositories.BookRepository;

@Service
public class BookService {
	
	@Autowired
	private BookRepository bRepo;
	
	
	public Book findByIsbn (List<String> isbn10, List<String> isbn13) {
		if(isbn10 !=null) {
			Optional<Book> b10 = Optional.ofNullable(bRepo.findByIsbn10Contains(isbn10.get(0)));
			
			System.out.println("ISBN10 lookup" + bRepo.findByIsbn10Contains(isbn10.get(0)));
			if(b10.isPresent()) {
				return b10.get();
			}
		}
		
		if(isbn13 !=null) {
			Optional<Book> b13 = Optional.ofNullable(bRepo.findByIsbn13Contains(isbn13.get(0)));
			System.out.println("ISBN13 lookup" + bRepo.findByIsbn13Contains(isbn13.get(0)));
			if(b13.isPresent()) {
				return b13.get();
			}
		}
		// if neither found  
    	return null;

	}

	public Boolean existsByIsbn (Book book) {
		if(findByIsbn(book.getIsbn10(), book.getIsbn13()) != null){
			return true;
		} else {
			return false;
		}
	}
	
	public Book addBookToDB (Book book) {
		return bRepo.save(book);
	}

	public void addAuthor(Author newAuthor, Book book) {
		book.getAuthors().add(newAuthor);
		bRepo.save(book);
		
	}

	public Book findById(Long id) {
    	Optional<Book> b = bRepo.findById(id);
    	
    	if(b.isPresent()) {
            return b.get();
    	} else {
    	    return null;
    	}
		
	}
	
}
