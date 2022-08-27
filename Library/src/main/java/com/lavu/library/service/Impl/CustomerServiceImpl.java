package com.lavu.library.service.Impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lavu.library.dto.CustomerDto;
import com.lavu.library.model.Customer;
import com.lavu.library.repository.CustomerRepository;
import com.lavu.library.repository.RoleRepository;
import com.lavu.library.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private CustomerRepository customerRepository;
	
	// Đăng ký người dùng
	@Override
	public CustomerDto createCustomer(CustomerDto customerDto) {
		Customer customer = new Customer();
		customer.setName(customerDto.getName());
		customer.setUsername(customerDto.getUsername());
		customer.setPassword(customerDto.getPassword());
		customer.setRoles(Arrays.asList(roleRepository.findByName("CUSTOMER")));
		Customer newCustomer = customerRepository.save(customer);
		return mapperDTO(newCustomer);
	}

	// Tìm người dùng theo username
	@Override
	public Customer findByUsername(String username) {
		return customerRepository.findByUsername(username);
	}

	// Lưu thông tin người dùng
	@Override
	public Customer saveInfor(Customer customer) {
		Customer updateCustomer = customerRepository.findByUsername(customer.getUsername());
		updateCustomer.setAddress(customer.getAddress());
		updateCustomer.setPhone(customer.getPhone());
		return customerRepository.save(updateCustomer);
	}

	// Lấy danh sách khách hàng
	@Override
	public List<Customer> getAllCustomers() {
		return customerRepository.findAll();
	}

	// Xem thông tin chi tiết khách hàng
	@Override
	public Customer getCustomerById(Long id) {
		return customerRepository.findById(id).get();
	}

	@Override
	public long getCountCustomer() {
		return customerRepository.count();
	}
	
	@Override
	public Customer findUserByResetToken(String resetToken) {
		return customerRepository.findByResetToken(resetToken);
	}
	@Override
	public Customer updateToken(Customer admin) {
		return customerRepository.save(admin);
	}
	/*
	 * private
	 */
	private CustomerDto mapperDTO(Customer customer) {
		CustomerDto customerDto = new CustomerDto();
		customerDto.setName(customer.getName());
		customerDto.setPassword(customer.getPassword());
		customerDto.setUsername(customer.getUsername());
		return customerDto;
	}
}
