package com.jy.booksbooksbooks.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.jy.booksbooksbooks.models.Book;
import com.jy.booksbooksbooks.models.User;
import com.jy.booksbooksbooks.models.UserBook;

public interface UserBookRepository extends CrudRepository<UserBook, Long> {

	Optional<UserBook> findByUserAndBook(User user, Book book);
	List<UserBook> findAllByUserAndCompleted(User user, Boolean completed);
	List<UserBook> findAllByBook(Book book);
	List<UserBook> findByBookAndOwned(Book book, boolean owned);
	@Query(value = "SELECT * FROM users_books WHERE current_page>0 AND completed=false AND book_id= :book", nativeQuery= true)
	List<UserBook> findByBookWhereCurrentPageGreaterThanZero(Book book);
	List<UserBook> findByBookAndCompleted(Book book, boolean completed);

}
