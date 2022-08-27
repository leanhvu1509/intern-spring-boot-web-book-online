package com.lavu.customer.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lavu.library.model.Category;
import com.lavu.library.model.Customer;
import com.lavu.library.model.Product;
import com.lavu.library.model.ShoppingCart;
import com.lavu.library.service.CategoryService;
import com.lavu.library.service.CustomerService;
import com.lavu.library.service.ProductService;
import com.lavu.library.service.ShoppingCartService;

@Controller
public class CartController {

	@Autowired
	private CustomerService customerService;

	@Autowired
	private ShoppingCartService cartService;

	@Autowired
	private ProductService productService;
	
	@Autowired
	private CategoryService categoryService;

	@GetMapping("/cart")
	public String cart(Model model, Principal principal, HttpSession session) {
		if (principal == null) {
			return "redirect:/login";
		}
		String username = principal.getName();
		Customer customer = customerService.findByUsername(username);
		ShoppingCart shoppingCart = customer.getCart();
		if (shoppingCart == null) {
			model.addAttribute("check", "No item in your cart");
		} else {
        	session.setAttribute("totalItems", shoppingCart.getTotalItems());
        	session.setAttribute("totalPrice", shoppingCart.getTotalPrice());
			model.addAttribute("subTotal", shoppingCart.getTotalPrice());
			model.addAttribute("shoppingCart", shoppingCart);
		}
        model.addAttribute("categories", categoryService.getCategoryByActive());
        model.addAttribute("title", "Giỏ hàng");
		return "cart";
	}

	@GetMapping("/add-to-cart/{id}")
	public String addItemToCart(@PathVariable("id") Long productId,
			@RequestParam(value = "quantity", required = false, defaultValue = "1") int quantity, Principal principal,
			HttpServletRequest request) {

		if (principal == null) {
			return "redirect:/login";
		}
		Product product = productService.getProductById(productId);
		String username = principal.getName();
		Customer customer = customerService.findByUsername(username);

		ShoppingCart cart = cartService.addItemToCart(product, quantity, customer);
		
		return "redirect:" + request.getHeader("Referer");

	}

	@RequestMapping(value = "/update-cart", method = RequestMethod.POST, params = "action=update")
	public String updateCart(@RequestParam("quantity") int quantity, @RequestParam("id") Long productId, Model model,
			Principal principal) {

		if (principal == null) {
			return "redirect:/login";
		} else {
			String username = principal.getName();
			Customer customer = customerService.findByUsername(username);
			Product product = productService.getProductById(productId);
			ShoppingCart cart = cartService.updateItemInCart(product, quantity, customer);

			model.addAttribute("shoppingCart", cart);
			return "redirect:/cart";
		}

	}

	@RequestMapping(value = "/update-cart", method = RequestMethod.POST, params = "action=delete")
	public String deleteItemFromCart(@RequestParam("id") Long productId, Model model, Principal principal) {
		if (principal == null) {
			return "redirect:/login";
		} else {
			String username = principal.getName();
			Customer customer = customerService.findByUsername(username);
			Product product = productService.getProductById(productId);
			ShoppingCart cart = cartService.deleteItemFromCart(product, customer);
			model.addAttribute("shoppingCart", cart);
			return "redirect:/cart";
		}

	}
}
