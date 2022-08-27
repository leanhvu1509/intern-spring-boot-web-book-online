package com.lavu.library.model.enums;

public enum ProductStatus {

	ACTIVE("Hiển thị"), 
	HIDE("Ẩn");

	private final String name;

	public String getName() {
		return name;
	}

	ProductStatus(String name) {
		this.name = name;
	}
}
