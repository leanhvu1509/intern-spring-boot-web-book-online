package com.lavu.admin.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lavu.library.service.ReviewService;

@Controller
@RequestMapping("/review")
public class ReviewController {

	@Autowired
	private ReviewService reviewService;
	
	@GetMapping(value = {"","/list"})
	public String getAllReviews(Model model, Principal principal) {
        if(principal == null){
            return "redirect:/login";
        }
        model.addAttribute("activeR", "active");
		model.addAttribute("title", "Danh sách đánh giá");
		model.addAttribute("reviews", reviewService.getAllReviews());
		return "reviews";
	}
}
