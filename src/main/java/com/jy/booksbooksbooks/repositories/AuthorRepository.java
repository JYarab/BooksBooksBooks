package com.jy.booksbooksbooks.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jy.booksbooksbooks.models.Author;


@Repository
public interface AuthorRepository extends CrudRepository <Author, Long> {

	Author findByName(String name);

	
}
