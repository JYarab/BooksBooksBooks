package com.jy.booksbooksbooks.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.jy.booksbooksbooks.models.User;
import com.jy.booksbooksbooks.repositories.UserRepository;

@Component
public class UserValidator {
	
	@Autowired
	private UserRepository uRepo;
	
	public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }
	
	public void validate(Object target, Errors errors) {
		User user = (User) target;
		
		if(!user.getPassword().equals(user.getPasswordConfirmation())) {
			errors.rejectValue("password", "Match", "Passwords do not match");
		}
		
		if(this.uRepo.existsByEmail(user.getEmail())) {
			errors.rejectValue("email", "Exisiting", "Email already in use");
		}
		
		if(this.uRepo.existsByDisplayName(user.getDisplayName())) {
			errors.rejectValue("displayName", "Exisiting", "Display name already in use");
		}
		
	}
	
	public void validateUpdate(Object target, Errors errors) {
		User user = (User) target;
		
		if(this.uRepo.existsByEmail(user.getEmail())) {
			errors.rejectValue("email", "Exisiting", "Email already in use");
		}
	}
	
}
