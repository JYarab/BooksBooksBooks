package com.jy.booksbooksbooks.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jy.booksbooksbooks.models.Author;
import com.jy.booksbooksbooks.models.Book;
import com.jy.booksbooksbooks.repositories.AuthorRepository;

@Service
public class AuthorService {
	
	@Autowired
	private AuthorRepository aRepo;

	public boolean existsByName(String name) {
		Author author = aRepo.findByName(name);
		
		if(author == null) {
			return false;
		} else {
			return true;
		}
		
	}

	public Author findByName(String name) {
		return aRepo.findByName(name);
		
	}

	public Author addAuthor(Author author) {
		return aRepo.save(author);
	}
	
	public Author addAuthor(Author author, Book book) {
		author.getBooks().add(book);
		return aRepo.save(author);
	}
	
	public void addBook(Author author, Book book) {
		if(author.getBooks() == null) {
			ArrayList<Book> newList = new ArrayList<Book>();
			newList.add(book);
			author.setBooks(newList);
		} else {
			author.getBooks().add(book);
		}
		aRepo.save(author);
	}

	public boolean bookIsLinked(Author author, Book book) {
		if(author.getBooks().contains(book)) {
			return true;
		} else {
			return false;
		}		
	}
	

	
}
