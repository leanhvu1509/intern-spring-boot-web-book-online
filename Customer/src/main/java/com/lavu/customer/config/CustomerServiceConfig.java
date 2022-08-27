package com.lavu.customer.config;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.lavu.library.model.Customer;
import com.lavu.library.repository.CustomerRepository;

public class CustomerServiceConfig implements UserDetailsService {

	@Autowired
	private CustomerRepository customerRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Customer customer = customerRepo.findByUsername(username);
		if (customer == null) {
			throw new UsernameNotFoundException("Could not find username!");
		}
		return new User(customer.getUsername(),
						customer.getPassword(), 
						customer.getRoles().stream()
						.map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList()));
	}

}
