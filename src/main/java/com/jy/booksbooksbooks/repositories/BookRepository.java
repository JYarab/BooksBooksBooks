package com.jy.booksbooksbooks.repositories;




import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.jy.booksbooksbooks.models.Book;



public interface BookRepository extends CrudRepository<Book, Long> {

	@Query(value = "SELECT b FROM Book b JOIN b.isbn10 i WHERE i = :isbn10")  
	Book findByIsbn10Contains(String isbn10);
	@Query(value = "SELECT b FROM Book b JOIN b.isbn13 i WHERE i = :isbn13")   
	Book findByIsbn13Contains(String isbn13);
	
	Optional<Book> findByIsbn10(String isbn10);
}
