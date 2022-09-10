package com.lavu.library.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.lavu.library.dto.ProductDto;
import com.lavu.library.model.Product;

public interface ProductService {

	Page<ProductDto> searchProducts(int pageNo, String keyword);

	Page<ProductDto> pageProducts(int pageNo);

	List<Product> filterLowPrice();

	List<Product> filterHighPrice();

	List<Product> getProductsInCategory(Long categoryId);

	List<Product> getRelatedProducts(Long categoryId);

	List<Product> listViewProducts();

	List<Product> getAllProductsByActice();

	void disableActiveProduct(Long id);

	void enableAciveProduct(Long id);

	void deleteProductById(Long id);

	Product updateProduct(MultipartFile imageProduct, ProductDto dto);

	Product createProduct(MultipartFile imageProduct, ProductDto dto);

	Product getProductById(Long id);

	List<Product> getAllProducts();

	ProductDto getById(Long id);

	long getCountProduct();

	List<Product> getProductTopSell();

	List<Product> getAllTopSell();

	List<Product> searchView(String keyword);

	Boolean checkQuantity(Long id);

}
