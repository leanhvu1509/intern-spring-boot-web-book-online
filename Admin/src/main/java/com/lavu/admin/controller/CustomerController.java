package com.lavu.admin.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lavu.library.service.CustomerService;

@Controller
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	
	@GetMapping(value = {"","/list"})
	public String getAllCustomer(Model model, Principal principal) {
        if(principal == null){
            return "redirect:/login";
        }
        model.addAttribute("activeCu", "active");
		model.addAttribute("title", "Danh sách khách hàng");
		model.addAttribute("customers", customerService.getAllCustomers());
		return "customers";
	}
}
