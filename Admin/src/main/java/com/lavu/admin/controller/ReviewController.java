package com.lavu.admin.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	
	@GetMapping("/delete/{id}")
	public String deteleById(@PathVariable("id")Long id, RedirectAttributes	attr) {
		try {
			reviewService.deleteById(id);
			attr.addFlashAttribute("success", "Xóa bình luận thành công");
		} catch (Exception e) {
			attr.addFlashAttribute("failed", "Lỗi Server!");
		}
		return "redirect:/review/";
	}
}
