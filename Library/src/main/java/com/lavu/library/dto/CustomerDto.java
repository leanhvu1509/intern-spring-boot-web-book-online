package com.lavu.library.dto;

import javax.validation.constraints.Size;

public class CustomerDto {

    @Size(min = 3, max = 50, message = "ko dc it hon 3")
    private String name;

    private String username;

    @Size(min = 5, max = 20, message = "ko dc it hon 3")
    private String password;

    private String repeatPassword;

	public CustomerDto() {
		super();
	}

	public CustomerDto(@Size(min = 3, max = 50, message = "ko dc it hon 3") String name, String username,
			@Size(min = 5, max = 20, message = "ko dc it hon 3") String password, String repeatPassword) {
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
