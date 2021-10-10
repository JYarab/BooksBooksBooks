package com.jy.booksbooksbooks.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jy.booksbooksbooks.models.Book;
import com.jy.booksbooksbooks.models.User;
import com.jy.booksbooksbooks.models.UserBook;
import com.jy.booksbooksbooks.repositories.UserBookRepository;

@Service
public class UserBookService {
	
	@Autowired
	private UserBookRepository ubRepo;

	public UserBook AddBookToList(@Valid UserBook userBook) {
		return ubRepo.save(userBook);
	}
	
	public Boolean containsBook(User user, Book book) {
		Boolean inList = false;
		for(UserBook ub : user.getUsersBooks()) {
			System.out.println("UB Controller " + ub.getBook());
			if(ub.getBook() == book) {
				System.out.println("UB Controller book in list");
				inList = true;
				break;
			} else {
				System.out.println("UB Controller book not in list");
				inList = false;
			}
		}
		return inList;
	}
	
	public UserBook findById(Long id) {
    	Optional<UserBook> ub = ubRepo.findById(id);
    	
    	if(ub.isPresent()) {
            return ub.get();
    	} else {
    	    return null;
    	}
    }
	
	public void removeFromList(UserBook userBook) {
		ubRepo.delete(userBook);
	}

	public UserBook findByUserAndBook(User user, Book book) {
		Optional<UserBook> ub = ubRepo.findByUserAndBook(user, book);
    	if(ub.isPresent()) {
            return ub.get();
    	} else {
    	    return null;
    	}
	}

	public void update(UserBook userBook) {
		System.out.println("UserBook updated");
		ubRepo.save(userBook);		
	}
	
	public Float totalRatingForBook(Book book) {
		Integer numOfRatings = 0;
		Integer totalOfRatings = 0;		
		
		for(UserBook ub : ubRepo.findAllByBook(book)) {
			if(ub.getRating() != null) {
				numOfRatings ++;
				totalOfRatings += ub.getRating();
			}
		}
		
	return ((float) totalOfRatings) / numOfRatings;
	}

	
}
