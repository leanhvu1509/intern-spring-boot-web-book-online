package com.lavu.library.service;

import com.lavu.library.dto.AdminForm;
import com.lavu.library.model.Admin;

public interface AdminService {

	Admin findByUsername(String username);
	
	Admin save(AdminForm adminDto);

	Admin findUserByResetToken(String resetToken);

	Admin updateToken(Admin admin);
	
}
