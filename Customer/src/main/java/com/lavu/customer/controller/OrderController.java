package com.lavu.customer.controller;

import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lavu.library.model.Customer;
import com.lavu.library.model.ShoppingCart;
import com.lavu.library.service.CategoryService;
import com.lavu.library.service.CustomerService;
import com.lavu.library.service.OrderService;
import com.lavu.library.service.ReviewService;

@Controller
public class OrderController {

	@Autowired
	private CustomerService customerService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private ReviewService reviewService;
	
	@Autowired
	private CategoryService categoryService;

	@GetMapping("/account/order")
	public String order(Model model, Principal principal, HttpSession session) {
		if (principal == null) {
			return "redirect:/login";
		}
		String username = principal.getName();
		Customer customer = customerService.findByUsername(username);
		model.addAttribute("orders", customer.getOrders());
		ShoppingCart shoppingCart = customer.getCart();
		session.setAttribute("totalItems", shoppingCart.getTotalItems());
		session.setAttribute("totalPrice", shoppingCart.getTotalPrice());
		model.addAttribute("categories", categoryService.getCategoryByActive());
		return "orders";
	}

	@GetMapping("/account/review")
	public String review(Model model, Principal principal, HttpSession session) {
		if (principal == null) {
			return "redirect:/login";
		}
		String username = principal.getName();
		Customer customer = customerService.findByUsername(username);
		model.addAttribute("reviews", reviewService.getReviewByCustomer(customer.getId()));
		ShoppingCart shoppingCart = customer.getCart();
		session.setAttribute("totalItems", shoppingCart.getTotalItems());
		session.setAttribute("totalPrice", shoppingCart.getTotalPrice());
		model.addAttribute("categories", categoryService.getCategoryByActive());
		
		
		return "reviews";
	}

	@RequestMapping(value = "/account/order/{id}", method = RequestMethod.GET)
	public String getOrderDetail(@PathVariable("id") Long id, Model model) {
		model.addAttribute("details", orderService.getOrderDetail(id));
		return "orders :: view";
	}

	@GetMapping("/checkout")
	public String checkout(Model model, Principal principal, RedirectAttributes attributes) {
		if (principal == null) {
			return "redirect:/login";
		}
		String username = principal.getName();
		Customer customer = customerService.findByUsername(username);

		model.addAttribute("customer", customer);
		ShoppingCart cart = customer.getCart();
		model.addAttribute("cart", cart);

		model.addAttribute("categories", categoryService.getCategoryByActive());
		return "checkout";
	}

	@RequestMapping(value = "/save-order", method = RequestMethod.GET)
	public String saveOrder(Principal principal) {
		if (principal == null) {
			return "redirect:/login";
		}
		String username = principal.getName();
		Customer customer = customerService.findByUsername(username);

		ShoppingCart cart = customer.getCart();
		orderService.saveOrder(cart, customer.getPhone(), customer.getAddress());

		return "redirect:/account/order";
	}
}
