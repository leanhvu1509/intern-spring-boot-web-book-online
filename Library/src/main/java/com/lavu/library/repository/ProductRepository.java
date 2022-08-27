package com.lavu.library.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lavu.library.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	/*
	 * Admin Module
	 */
	@Query(value="SELECT COUNT(*) FROM products p",nativeQuery = true)
	long getCountProduct();
	
	@Query(value = "SELECT p.*  FROM order_details o \r\n" + 
			"	inner join products p on o.product_id=p.product_id  \r\n" + 
			"		GROUP BY o.product_id \r\n" + 
			"			order by sum(o.quantity) desc limit 5;",nativeQuery = true)
	List<Product> getProductTopSell();
	
	@Query(value = "SELECT p.*,sum(o.quantity) as sum  FROM order_details o \r\n" + 
			"	inner join products p on o.product_id=p.product_id  \r\n" + 
			"		GROUP BY o.product_id \r\n" + 
			"			order by sum desc;",nativeQuery = true)
	List<Product> getAllTopSell();
	
	/*
	 * Customer Module
	 */
	@Query("SELECT p FROM Product p WHERE p.status = 0")
	List<Product> getAllProducts();
	
	
    @Query(value = "SELECT * FROM products p WHERE p.status = 0 ORDER BY p.created_date DESC LIMIT 8 ", nativeQuery = true)
    List<Product> getViewHomeProducts();


    @Query(value = "SELECT * FROM products p INNER JOIN categories c ON c.category_id = p.category_id WHERE p.category_id = ?1", nativeQuery = true)
    List<Product> getRelatedProducts(Long categoryId);


    @Query(value = "SELECT p FROM Product p INNER JOIN Category c ON c.id = p.category.id WHERE c.id = ?1")
    List<Product> getProductsInCategory(Long categoryId);


    @Query("SELECT p FROM Product p WHERE p.status = 0 ORDER BY p.price DESC")
    List<Product> filterHighPrice();


    @Query("SELECT p FROM Product p WHERE p.status = 0 ORDER BY p.price")
    List<Product> filterLowPrice();
    
    @Query("SELECT p FROM Product p")
    Page<Product> pageProducts(Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.description like %?1% OR p.name like %?1%")
    Page<Product> searchPageProducts(String keyword, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.description like %?1% OR p.name like %?1%")
    List<Product> searchProducts(String keyword);
	
}
