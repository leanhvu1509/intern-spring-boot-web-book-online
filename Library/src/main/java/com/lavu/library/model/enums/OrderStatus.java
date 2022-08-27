package com.lavu.library.model.enums;

public enum OrderStatus {

	PENDING("Chờ xác nhận"),
	ACCEPT("Đã xác nhận"),
	DELIVERED("Đã giao hàng"),
	CANCEL("Đã hủy"),;

	private final String name;

	public String getName() {
		return name;
	}
	OrderStatus(String name) {
		this.name = name;
	}
}
