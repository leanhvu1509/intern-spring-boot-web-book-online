package com.lavu.library.model.enums;

public enum CategoryStatus {

	ACTIVE("Hiển thị"), 
	HIDE("Ẩn");

	private final String name;

	public String getName() {
		return name;
	}

	CategoryStatus(String name) {
		this.name = name;
	}
}
