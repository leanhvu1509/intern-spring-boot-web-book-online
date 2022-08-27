package com.lavu.library.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class AdminForm {
	
	@NotBlank(message = "Không được để trống")
	@Size(min = 3,max = 50,message = "Độ dài tên không phù hợp")
	private String name;

	@NotBlank(message = "Không được để trống")
	private String username;

	@Size(min = 3,max = 10,message = "Password phải có độ đài từ 3-10 ký tự")
	private String password;
	
	private String repeatPassword;

	public AdminForm() {
		super();
	}

	public AdminForm(String name, String username, String password, String repeatPassword) {
		super();
		this.name = name;
		this.username = username;
		this.password = password;
		this.repeatPassword = repeatPassword;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRepeatPassword() {
		return repeatPassword;
	}

	public void setRepeatPassword(String repeatPassword) {
		this.repeatPassword = repeatPassword;
	}
	
}
