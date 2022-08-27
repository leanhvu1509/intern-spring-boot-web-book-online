package com.lavu.library.dto;

import com.lavu.library.model.Category;
import com.lavu.library.model.enums.ProductStatus;

public class ProductDto {

	private Long id;
	

	private String name;

	private String author;

	private String publisher;

	private int publishAt;

	private int price;
	
	
	private double discount;
	
	private int quantity;
	
	private String description;
	private Category category;
	private String image;
	private ProductStatus status;

	public ProductDto() {
		super();
	}

	public ProductDto(Long id, String name, String author, String publisher, int publishAt, int price, double discount,
			int quantity, String description, Category category, String image, ProductStatus status) {
		super();
		this.id = id;
		this.name = name;
		this.author = author;
		this.publisher = publisher;
		this.publishAt = publishAt;
		this.price = price;
		this.discount = discount;
		this.quantity = quantity;
		this.description = description;
		this.category = category;
		this.image = image;
		this.status = status;
	}

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

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public int getPublishAt() {
		return publishAt;
	}

	public void setPublishAt(int publishAt) {
		this.publishAt = publishAt;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public ProductStatus getStatus() {
		return status;
	}

	public void setStatus(ProductStatus status) {
		this.status = status;
	}

	
}
