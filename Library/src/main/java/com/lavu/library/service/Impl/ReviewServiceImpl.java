package com.lavu.library.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lavu.library.model.Review;
import com.lavu.library.repository.ReviewRepository;
import com.lavu.library.service.ReviewService;

@Service
public class ReviewServiceImpl implements ReviewService{

	@Autowired
	private ReviewRepository reviewRepository;
	
	@Override
	public List<Review> getAllReviews(){
		return reviewRepository.findAll();
	}
	
	@Override
	public Review createReview(Review review) {
		return reviewRepository.save(review);
	}
	
	@Override
	public List<Review> getReviewByProduct(Long id){
		return reviewRepository.getReviewByProduct(id);
	}
	
	@Override
	public List<Review> getReviewByCustomer(Long id){
		return reviewRepository.getReviewByCustomer(id);
	}
}
