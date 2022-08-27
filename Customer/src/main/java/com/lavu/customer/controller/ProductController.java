package com.lavu.customer.controller;

import java.security.Principal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lavu.library.model.Category;
import com.lavu.library.model.Customer;
import com.lavu.library.model.Product;
import com.lavu.library.model.Review;
import com.lavu.library.service.CategoryService;
import com.lavu.library.service.CustomerService;
import com.lavu.library.service.ProductService;
import com.lavu.library.service.ReviewService;

@Controller
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private ProductService productService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ReviewService reviewService;

	@Autowired
	private CustomerService customerService;

	@GetMapping("")
	public String products(Model model) {
		List<Product> products = productService.getAllProducts();
		model.addAttribute("products", products);
		model.addAttribute("title", "Tất cả sản phẩm");
		return "products";
	}

	@GetMapping("/{id}")
	public String findProductById(@PathVariable("id") Long id, Model model) {
		Product product = productService.getProductById(id);
		Long categoryId = product.getCategory().getId();
		model.addAttribute("product", product);
		model.addAttribute("products", productService.getRelatedProducts(categoryId));
		model.addAttribute("title", product.getName());
		model.addAttribute("categories", categoryService.getCategoryByActive());
		
		Review review = new Review();
		review.setProduct(product);
		model.addAttribute("review", review);
		model.addAttribute("reviews", reviewService.getReviewByProduct(id));
		return "product-detail";
	}

	@RequestMapping(value = "/send-review", method = RequestMethod.POST)
	public String sendReview(@ModelAttribute("review") Review review, @RequestParam("number") float number,
			Principal principal) {
		if (principal == null) {
			return "redirect:/login";
		}
		String username = principal.getName();
		Customer customer = customerService.findByUsername(username);
		review.setCustomer(customer);
		review.setDatePost(new Date());
		review.setNumber(number);
		reviewService.createReview(review);
		return "redirect:/product/" + review.getProduct().getId();
	}

	@GetMapping("/category/{id}")
	public String getProductsInCategory(@PathVariable("id") Long categoryId, Model model) {
		model.addAttribute("categories", categoryService.getCategoryByActive());
		Category category = categoryService.getCategoryById(categoryId);
		model.addAttribute("title", category.getName());
		model.addAttribute("category", category);
		model.addAttribute("products", productService.getProductsInCategory(categoryId));
		return "products-in-category";
	}

	@GetMapping("/high-price")
	public String filterHighPrice(Model model) {
		model.addAttribute("categoryDtoList", categoryService.getCountProductByCategory());
		model.addAttribute("products", productService.filterHighPrice());
		model.addAttribute("categories", categoryService.getCategoryByActive());
		return "filter-high-price";
	}

	@GetMapping("/low-price")
	public String filterLowPrice(Model model) {
		model.addAttribute("categoryDtoList", categoryService.getCountProductByCategory());
		model.addAttribute("products", productService.filterLowPrice());
		model.addAttribute("categories", categoryService.getCategoryByActive());
		return "filter-low-price";
	}
}
