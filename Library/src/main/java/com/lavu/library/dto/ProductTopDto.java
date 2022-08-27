package com.lavu.library.dto;

public class ProductTopDto {

	private Long id;
	
	private String name;
	
	private int price;
		
	private String image;
	
	private int sum;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}



	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getSum() {
		return sum;
	}

	public void setSum(int sum) {
		this.sum = sum;
	}

	public ProductTopDto(Long id, String name, int price, String image, int sum) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.image = image;
		this.sum = sum;
	}

	public ProductTopDto() {
		super();
	}


	
	
}
