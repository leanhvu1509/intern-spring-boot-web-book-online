package com.lavu.library.service.Impl;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.lavu.library.dto.ProductDto;
import com.lavu.library.model.Product;
import com.lavu.library.model.enums.ProductStatus;
import com.lavu.library.repository.ProductRepository;
import com.lavu.library.service.ProductService;
import com.lavu.library.utils.ImageUpload;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ImageUpload imageUpload;

	@Autowired
	private ProductRepository productRepository;

	// Lấy danh sách sản phẩm
	@Override
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	// Lấy chi tiết sản phẩm
	@Override
	public Product getProductById(Long id) {
		return productRepository.findById(id).get();
	}

	@Override
	public ProductDto getById(Long id) {
		Product product = productRepository.findById(id).get();
		ProductDto productDto = new ProductDto();
		productDto.setId(product.getId());
		productDto.setName(product.getName());
		productDto.setAuthor(product.getAuthor());
		productDto.setPublisher(product.getPublisher());
		productDto.setPublishAt(product.getPublishAt());
		productDto.setDescription(product.getDescription());
		productDto.setQuantity(product.getQuantity());
		productDto.setCategory(product.getCategory());
		productDto.setPrice(product.getPrice());
		productDto.setDiscount(product.getDiscount());
		productDto.setImage(product.getImage());
		productDto.setStatus(product.getStatus());
		return productDto;
	}

	// Tạo mới
	@Override
	public Product createProduct(MultipartFile imageProduct, ProductDto dto) {
		try {
			Product entity = new Product();
			if (imageProduct == null) {
				entity.setImage(null);
			} else {
				imageUpload.uploadImage(imageProduct);
				entity.setImage(Base64.getEncoder().encodeToString(imageProduct.getBytes()));
			}
			entity.setName(dto.getName());
			entity.setAuthor(dto.getAuthor());
			entity.setPublisher(dto.getPublisher());
			entity.setPublishAt(dto.getPublishAt());
			entity.setDescription(dto.getDescription());
			entity.setQuantity(dto.getQuantity());
			entity.setCategory(dto.getCategory());
			entity.setPrice(dto.getPrice());
			entity.setDiscount(dto.getDiscount());
			entity.setStatus(ProductStatus.ACTIVE);
			return productRepository.save(entity);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// Cập nhật
	@Override
	public Product updateProduct(MultipartFile imageProduct, ProductDto dto) {
		try {
			Product entity = productRepository.findById(dto.getId()).get();
			if (imageProduct == null) {
				entity.setImage(entity.getImage());
			}
			if (imageUpload.checkExisted(imageProduct) == false) {
				imageUpload.uploadImage(imageProduct);
				entity.setImage(Base64.getEncoder().encodeToString(imageProduct.getBytes()));
			}

			entity.setName(dto.getName());
			entity.setAuthor(dto.getAuthor());
			entity.setPublisher(dto.getPublisher());
			entity.setPublishAt(dto.getPublishAt());
			entity.setDescription(dto.getDescription());
			entity.setQuantity(dto.getQuantity());
			entity.setCategory(dto.getCategory());
			entity.setPrice(dto.getPrice());
			entity.setDiscount(dto.getDiscount());
			return productRepository.save(entity);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// Xóa
	@Override
	public void deleteProductById(Long id) {
		Product product = productRepository.findById(id).get();
		product.setStatus(ProductStatus.DELETE);
		productRepository.save(product);

	}

	// Enable
	@Override
	public void enableAciveProduct(Long id) {
		Product product = productRepository.findById(id).get();
		product.setStatus(ProductStatus.ACTIVE);
		productRepository.save(product);
	}

	// Disable
	@Override
	public void disableActiveProduct(Long id) {
		Product product = productRepository.findById(id).get();
		product.setStatus(ProductStatus.HIDE);
		productRepository.save(product);
	}

	// Lấy danh sách active
	@Override
	public List<Product> getAllProductsByActice() {
		return productRepository.getAllProducts();
	}

	// Lấy danh sách hiển thị trang home
	@Override
	public List<Product> listViewProducts() {
		return productRepository.getViewHomeProducts();
	}

	// Lây danh sách sp liên quan
	@Override
	public List<Product> getRelatedProducts(Long categoryId) {
		return productRepository.getRelatedProducts(categoryId);
	}

	// Lấy danh sách theo danh mục
	@Override
	public List<Product> getProductsInCategory(Long categoryId) {
		return productRepository.getProductsWithCategory(categoryId);
	}

	// Sắp xếp giảm
	@Override
	public List<Product> filterHighPrice() {
		return productRepository.filterHighPrice();
	}

	// Sắp xệp tăng
	@Override
	public List<Product> filterLowPrice() {
		return productRepository.filterLowPrice();
	}

	// Phân trang
	@Override
	public Page<ProductDto> pageProducts(int pageNo) {
		Pageable pageable = PageRequest.of(pageNo, 12);
		List<ProductDto> products = transfer(productRepository.findAll());
		@SuppressWarnings("unchecked")
		Page<ProductDto> productPages = toPage(products, pageable);
		return productPages;
	}

	// Tìm kiếm
	@Override
	public Page<ProductDto> searchProducts(int pageNo, String keyword) {
		Pageable pageable = PageRequest.of(pageNo, 5);
		List<ProductDto> productDtoList = transfer(productRepository.searchProducts(keyword));
		@SuppressWarnings("unchecked")
		Page<ProductDto> products = toPage(productDtoList, pageable);
		return products;
	}
	
	@Override
	public List<Product> searchView(String keyword){
		return productRepository.searchProducts(keyword);
	}
	
	@Override
	public long getCountProduct() {
		return productRepository.getCountProduct();
	}

	@Override
	public List<Product> getProductTopSell() {
		return productRepository.getProductTopSell();
	}

	@Override
	public List<Product> getAllTopSell() {
		return productRepository.getAllTopSell();
	}
	
	@Override
	public Boolean checkQuantity(Long id) {
		return productRepository.checkQuantity(id);
	}

	/*
	 * private
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Page toPage(List<ProductDto> list, Pageable pageable) {
		if (pageable.getOffset() >= list.size()) {
			return Page.empty();
		}
		int startIndex = (int) pageable.getOffset();
		int endIndex = ((pageable.getOffset() + pageable.getPageSize()) > list.size()) ? list.size()
				: (int) (pageable.getOffset() + pageable.getPageSize());
		List subList = list.subList(startIndex, endIndex);
		return new PageImpl(subList, pageable, list.size());
	}

	private List<ProductDto> transfer(List<Product> products) {
		List<ProductDto> productDtoList = new ArrayList<>();
		for (Product product : products) {
			ProductDto productDto = new ProductDto();
			productDto.setId(product.getId());
			productDto.setName(product.getName());
			productDto.setAuthor(product.getAuthor());
			productDto.setPublisher(product.getPublisher());
			productDto.setPublishAt(product.getPublishAt());
			productDto.setDescription(product.getDescription());
			productDto.setQuantity(product.getQuantity());
			productDto.setCategory(product.getCategory());
			productDto.setPrice(product.getPrice());
			productDto.setDiscount(product.getDiscount());
			productDto.setImage(product.getImage());
			productDto.setStatus(product.getStatus());

			productDtoList.add(productDto);
		}
		return productDtoList;
	}
}
