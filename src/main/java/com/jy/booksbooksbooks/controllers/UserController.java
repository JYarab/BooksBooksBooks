package com.jy.booksbooksbooks.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jy.booksbooksbooks.models.User;
import com.jy.booksbooksbooks.services.UserService;
import com.jy.booksbooksbooks.validators.UserValidator;

@Controller
public class UserController {
    
	@Autowired
	private UserService userService;
    
	@Autowired
	private UserValidator uValidator;
	

  
    
    @RequestMapping("/")
    public String registerForm(@ModelAttribute("user") User user) {
        return "register.jsp";
    }

    
    @GetMapping("/login")
	  	public String login() {
		  return "login.jsp";
	}
    
    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult result, HttpSession session) {
        // if result has errors, return main page 
    	// else, save user in the db, put user id in session, and redirect to user page route
    	uValidator.validate(user, result);
		if (result.hasErrors()) {
            return "register.jsp";
        } else {
        	User newUser = userService.registerUser(user);
        	session.setAttribute("loggedInUser", newUser.getId());
        	return "redirect:/myBooks";
        }
        
    }
    
    @PostMapping("/login")
    public String loginUser(@RequestParam("email") String email, @RequestParam("password") String password, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        // if the user is authenticated, put user id in session
        // else, add error messages and return the login page
    	if (userService.authenticateUser(email, password)) {
    		User loggedUser = userService.findByEmail(email);
    		session.setAttribute("loggedInUser", loggedUser.getId());
    		return "redirect:/myBooks";
    	}else {
    		redirectAttributes.addFlashAttribute("loginError", "Invalid E-mail or Password");
    		return "redirect:/login";
    	}
    }
    

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        // invalidate session
    	session.invalidate();
        // redirect to login page
    	return "redirect:/login";
    }
}
