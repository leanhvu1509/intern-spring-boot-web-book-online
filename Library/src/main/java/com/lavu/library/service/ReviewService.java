package com.lavu.library.service;

import java.util.List;

import com.lavu.library.model.Review;

public interface ReviewService {

	Review createReview(Review review);

	List<Review> getReviewByProduct(Long id);

	List<Review> getAllReviews();

	List<Review> getReviewByCustomer(Long id);

}
