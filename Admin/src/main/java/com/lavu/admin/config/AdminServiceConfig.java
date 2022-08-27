package com.lavu.admin.config;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.lavu.library.model.Admin;
import com.lavu.library.repository.AdminRepository;

public class AdminServiceConfig implements UserDetailsService {

	@Autowired
	private AdminRepository adminRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Admin admin = adminRepo.findByUsername(username);
		if (admin == null) {
			throw new UsernameNotFoundException("Could not find username!");
		}

		return new User(admin.getUsername(), admin.getPassword(), admin.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList()));
	}

}
