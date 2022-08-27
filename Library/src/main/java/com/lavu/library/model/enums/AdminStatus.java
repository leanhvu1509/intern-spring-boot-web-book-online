package com.lavu.library.model.enums;

public enum AdminStatus {

	ACTIVE("Active"), 
	HIDE("Hide");

	private final String name;

	public String getName() {
		return name;
	}

	AdminStatus(String name) {
		this.name = name;
	}
}
