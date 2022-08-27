package com.lavu.library.service.Impl;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lavu.library.dto.AdminForm;
import com.lavu.library.model.Admin;
import com.lavu.library.repository.AdminRepository;
import com.lavu.library.repository.RoleRepository;
import com.lavu.library.service.AdminService;

@Service
@Transactional
public class AdminServiceImpl implements AdminService{

	@Autowired
	private AdminRepository adminRepo;
	
	@Autowired
	private RoleRepository roleRepo;
	
	@Override
	public Admin findByUsername(String username) {
		return adminRepo.findByUsername(username);
	}

	@Override
	public Admin findUserByResetToken(String resetToken) {
		return adminRepo.findByResetToken(resetToken);
	}
	
	@Override
	public Admin save(AdminForm dto) {
		Admin admin = new Admin();
		admin.setName(dto.getName());
		admin.setUsername(dto.getUsername());
		admin.setPassword(dto.getPassword());
		admin.setRoles(Arrays.asList(roleRepo.findByName("ADMIN")));
		return adminRepo.save(admin);
	}

	@Override
	public Admin updateToken(Admin admin) {
		return adminRepo.save(admin);
	}
	
}
