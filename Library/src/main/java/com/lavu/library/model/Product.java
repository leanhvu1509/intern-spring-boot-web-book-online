package com.lavu.library.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.lavu.library.model.enums.ProductStatus;

@Entity
@Table(name = "products", uniqueConstraints = @UniqueConstraint(columnNames = {"name","image"}))
public class Product extends BaseDate{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id")
	private Long id;
	
	private String name;
	
	private String author;
	
	private String publisher;
	
	private int publishAt;
	
	private int price;
	
	private double discount;
	
	private int quantity;
	

	@Column(columnDefinition = "TEXT")
	private String description;
	
	@Column(name = "status")
	@Enumerated(EnumType.ORDINAL)
	private ProductStatus status;
	
	@Lob
	@Column(columnDefinition = "MEDIUMBLOB")
	private String image;
	
	@ManyToOne
	@JoinColumn(name = "category_id",referencedColumnName = "category_id")
	private Category category;
	
	@OneToMany(mappedBy = "product")
	private Set<Review> reviews;

	public Product() {
		super();
	}

	public Product(Long id, String name, String author, String publisher, int publishAt, int price, double discount,
			int quantity, String description, ProductStatus status, String image, Category category,
			Set<Review> reviews) {
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
		this.status = status;
		this.image = image;
		this.category = category;
		this.reviews = reviews;
	}

	
	public Product(Long id, String name, String author, String publisher, int publishAt, int price, double discount,
			int quantity, String description, ProductStatus status, String image, Category category) {
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
		this.status = status;
		this.image = image;
		this.category = category;
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

	public ProductStatus getStatus() {
		return status;
	}

	public void setStatus(ProductStatus status) {
		this.status = status;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Set<Review> getReviews() {
		return reviews;
	}

	public void setReviews(Set<Review> reviews) {
		this.reviews = reviews;
	}
	
	
	
	
}
