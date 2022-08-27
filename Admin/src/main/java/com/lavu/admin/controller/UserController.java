package com.lavu.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

	
	@GetMapping(value = {"","/list"})
	public String getAllUsers(Model model) {
		model.addAttribute("title", "Danh sách người dùng");
        model.addAttribute("activeU", "active");
		return "users";
	}
	
	
}
