package com.lavu.library.dto;

public class ChartDto {

	private String month;
	
	private double total;

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public ChartDto(String month, double total) {
		super();
		this.month = month;
		this.total = total;
	}
	
	public ChartDto() {
	}
	
	
	
}
