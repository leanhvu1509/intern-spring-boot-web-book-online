package com.lavu.library.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lavu.library.model.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>{

	@Query("SELECT r FROM Review r WHERE r.product.id = ?1")
	List<Review> getReviewByProduct(Long id);
	
	@Query("SELECT r FROM Review r WHERE r.customer.id = ?1")
	List<Review> getReviewByCustomer(Long id);
}
