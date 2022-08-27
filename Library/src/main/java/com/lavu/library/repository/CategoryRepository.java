package com.lavu.library.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lavu.library.dto.CategoryDto;
import com.lavu.library.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{

	@Query("SELECT c FROM Category c WHERE c.status = 0")
	List<Category> findAllByActived();
	
    /*
     * Customer Module
     */
	List<Category> findByParentIsNull();
	
    @Query("SELECT new com.lavu.library.dto.CategoryDto(c.id, c.name, COUNT(p.category.id)) "
    		+ "FROM Category c INNER JOIN Product p ON p.category.id = c.id " 
    		+ " WHERE c.status = 0 GROUP BY c.id")
    List<CategoryDto> getCountProductByCategory();
}
