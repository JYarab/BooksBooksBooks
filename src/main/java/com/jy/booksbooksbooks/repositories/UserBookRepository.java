package com.jy.booksbooksbooks.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.jy.booksbooksbooks.models.Book;
import com.jy.booksbooksbooks.models.User;
import com.jy.booksbooksbooks.models.UserBook;

public interface UserBookRepository extends CrudRepository<UserBook, Long> {

	Optional<UserBook> findByUserAndBook(User user, Book book);
	List<UserBook> findAllByUserAndCompleted(User user, Boolean completed);
	List<UserBook> findAllByBook(Book book);

}
