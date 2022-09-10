package com.lavu.customer.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lavu.library.dto.CustomerDto;
import com.lavu.library.model.Category;
import com.lavu.library.model.Customer;
import com.lavu.library.service.CategoryService;
import com.lavu.library.service.CustomerService;

@Controller
public class AuthController {

	@Autowired
	private CustomerService customerService;

	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model) {
        model.addAttribute("categories", categoryService.getCategoriesByParentIsNull());

		 model.addAttribute("title", "Đăng nhập");
		 return "login";
	}

	@GetMapping("/register")
	public String register(Model model) {
        model.addAttribute("categories", categoryService.getCategoriesByParentIsNull());
        model.addAttribute("title", "Đăng ký");
		model.addAttribute("customerDto", new CustomerDto());
		return "register";
	}

	@PostMapping("/do-register")
	public String processRegister(@Valid @ModelAttribute("customerDto") CustomerDto customerDto, BindingResult result,
			Model model,RedirectAttributes attributes) {
		try {
			if (result.hasErrors()) {
				model.addAttribute("customerDto", customerDto);
				return "register";
			}
			Customer customer = customerService.findByUsername(customerDto.getUsername());
			if (customer != null) {
				model.addAttribute("error", "Username/Email đã tồn tại");
				model.addAttribute("customerDto", customerDto);
				return "register";
			}
			if (customerDto.getPassword().equals(customerDto.getRepeatPassword())) {
				customerDto.setPassword(passwordEncoder.encode(customerDto.getPassword()));
				customerService.createCustomer(customerDto);
				attributes.addFlashAttribute("success", "Đăng ký thành công");
				return "redirect:/login";
			} else {
				model.addAttribute("error", "Mật khẩu xác nhận không khớp");
				model.addAttribute("customerDto", customerDto);
				return "register";
			}
		} catch (Exception e) {
			model.addAttribute("error", "Server have ran some problems");
			model.addAttribute("customerDto", customerDto);
		}
		return "register";
	}
}
