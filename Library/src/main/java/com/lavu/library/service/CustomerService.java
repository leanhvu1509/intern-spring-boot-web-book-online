package com.lavu.library.service;

import java.util.List;

import com.lavu.library.dto.CustomerDto;
import com.lavu.library.model.Customer;

public interface CustomerService {

	Customer getCustomerById(Long id);

	List<Customer> getAllCustomers();

	Customer saveInfor(Customer customer);

	Customer findByUsername(String username);

	CustomerDto createCustomer(CustomerDto customerDto);

	long getCountCustomer();

	Customer updateToken(Customer admin);

	Customer findUserByResetToken(String resetToken);

}
