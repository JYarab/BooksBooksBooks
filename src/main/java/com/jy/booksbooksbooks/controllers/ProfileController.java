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

import com.jy.booksbooksbooks.models.User;
import com.jy.booksbooksbooks.services.UserService;
import com.jy.booksbooksbooks.validators.UserValidator;

@Controller
@RequestMapping("/profile")
public class ProfileController {
	
	@Autowired
	private UserService uService;
	
	@Autowired
	private UserValidator uValidator;
	 
	@GetMapping("")
		public String viewProfile(@ModelAttribute("user") User user, HttpSession session, Model viewModel) {
		if(session.getAttribute("loggedInUser") != null) {
			User loggedUser = uService.findUserById((Long) session.getAttribute("loggedInUser"));
			viewModel.addAttribute("user", loggedUser);			
			return "profile.jsp";
		} else {
			return "redirect:/";
		}		
	}
	
	@PostMapping("/update")
	public String updateProfile(@Valid @ModelAttribute("user") User user, BindingResult result, HttpSession session, Model viewModel) {
		User loggedUser = uService.findUserById((Long) session.getAttribute("loggedInUser"));
		if (result.hasErrors()) {
			System.out.println("Result has errors");
			return "profile.jsp";
		} else {
			System.out.println("No errors updating");
			uService.update(user);
			return "redirect:/profile";
		}	
	}
	
	@PostMapping("/update/password")
	public String updatePassword() {
	return "redirect:/profile";
}
	
}
