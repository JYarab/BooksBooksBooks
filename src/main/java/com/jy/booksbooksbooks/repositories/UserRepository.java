package com.jy.booksbooksbooks.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jy.booksbooksbooks.models.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByEmail(String email);
    boolean existsByEmail(String email);
	boolean existsByDisplayName(String displayName);
}
