package com.lavu.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lavu.library.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

	Customer findByUsername(String username);

	@Query(value = "SELECT COUNT(*) FROM customers c", nativeQuery = true)
	long getCountCustomer();
	
	Customer findByResetToken(String resetToken);
}
