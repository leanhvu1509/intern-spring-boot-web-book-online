package com.lavu.library.service;

import java.util.List;

import com.lavu.library.dto.CategoryDto;
import com.lavu.library.model.Category;

public interface CategoryService {

	List<CategoryDto> getCountProductByCategory();

	List<Category> getCategoryByActive();

	void disableActiceById(Long id);

	void enableActiceById(Long id);

	void deleteCategoryById(Long id);

	Category updateCategory(Category entity);

	Category getCategoryById(Long id);

	Category createCategory(Category entity);

	List<Category> getAllCategories();

	List<Category> getCategoriesByParentIsNull();

}
