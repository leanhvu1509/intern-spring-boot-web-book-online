package com.lavu.library.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lavu.library.dto.CategoryDto;
import com.lavu.library.model.Category;
import com.lavu.library.model.enums.CategoryStatus;
import com.lavu.library.repository.CategoryRepository;
import com.lavu.library.service.CategoryService;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepo;
	
	//Lấy danh sách các danh mục
	@Override
	public List<Category> getAllCategories(){
		return categoryRepo.findAll();
	}
	
	//Tạo mới danh mục
	@Override
	public Category createCategory(Category entity) {
		
		entity.setStatus(CategoryStatus.ACTIVE);
		return categoryRepo.save(entity);
	}
	
	//Lấy chi tiết danh mục
	@Override
	public Category getCategoryById(Long id) {
		return categoryRepo.findById(id).get();
	}
	
	//Cập nhật danh mục
	@Override
	public Category updateCategory(Category entity) {
        Category categoryUpdate = null;
        try {
            categoryUpdate= categoryRepo.findById(entity.getId()).get();
            categoryUpdate.setName(entity.getName());
        }catch (Exception e){
            e.printStackTrace();
        }
		return categoryRepo.save(categoryUpdate);
	}
	
	//Xóa danh mục
	@Override
	public void deleteCategoryById(Long id) {
		categoryRepo.deleteById(id);
	}
	
	//Actice danh mục
	@Override
	public void enableActiceById(Long id) {
		Category category = categoryRepo.findById(id).get();
		category.setStatus(CategoryStatus.ACTIVE);
		categoryRepo.save(category);
	}
	
	//Hide danh mục
	@Override
	public void disableActiceById(Long id) {
		Category category = categoryRepo.findById(id).get();
		category.setStatus(CategoryStatus.HIDE);
		categoryRepo.save(category);
	}
	
	//Lấy danh sách các danh mục active
	@Override
	public List<Category> getCategoryByActive(){
		return categoryRepo.findAllByActived();
	}
	
	//Lấy số sp theo danh mục
	@Override
	public List<CategoryDto> getCountProductByCategory(){
		return categoryRepo.getCountProductByCategory();
	}

	@Override
	public List<Category> getCategoriesByParentIsNull(){
		return categoryRepo.findByParentIsNull();
	}
}
