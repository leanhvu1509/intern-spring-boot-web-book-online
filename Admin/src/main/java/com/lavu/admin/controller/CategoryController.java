package com.lavu.admin.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lavu.library.model.Category;
import com.lavu.library.model.enums.CategoryStatus;
import com.lavu.library.service.CategoryService;

@Controller
@RequestMapping("/category")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@GetMapping(value = {"","/list"})
	public String list(Model model, Principal principal) {
        if(principal == null){
            return "redirect:/login";
        }
        model.addAttribute("activeCa", "active");
		model.addAttribute("title", "Danh sách các danh mục");
		model.addAttribute("categories", categoryService.getAllCategories());
		model.addAttribute("categoriesParent", categoryService.getCategoriesByParentIsNull());
		model.addAttribute("newCategory", new Category());
		return "categories";
	}
	
    @PostMapping("/add")
    public String add(@ModelAttribute("newCategory") Category category, RedirectAttributes attributes){
        try {
            categoryService.createCategory(category);
            attributes.addFlashAttribute("success", "Thêm danh mục thành công!");
        }catch (DataIntegrityViolationException e){
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Tên danh mục đã tồn tại");
        }
        catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Lỗi server!");
        }
        return "redirect:/category/list";

    }

//    @RequestMapping(value = "/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
//    @ResponseBody
//    public Category getById(@PathVariable("id") Long id){
//        return categoryService.getCategoryById(id);
//    }
    
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String getDetail(@PathVariable("id") Long id, Model model) {
		model.addAttribute("detailCate", categoryService.getCategoryById(id));
		return "categories :: viewCate";
	}

    @GetMapping("/update")
    public String update(Category category, RedirectAttributes attributes){
        try {
            categoryService.updateCategory(category);
            attributes.addFlashAttribute("success","Cập nhật thành công");
        }catch (DataIntegrityViolationException e){
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Tên danh mục đã tồn tại");
        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Lỗi server");
        }
        return "redirect:/category/list";
    }

    @RequestMapping(value = "/delete/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String delete(@PathVariable("id") Long id, RedirectAttributes attributes){
        try {
            categoryService.deleteCategoryById(id);
            attributes.addFlashAttribute("success", "Xóa thành công");
        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Lỗi server");
        }
        return "redirect:/category/list";
    }

    @RequestMapping(value = "/active", method = {RequestMethod.PUT, RequestMethod.GET})
    public String enable(Long id, RedirectAttributes attributes){
    	Category cate = categoryService.getCategoryById(id);
    	if(cate.getStatus() == CategoryStatus.ACTIVE) {
    		categoryService.disableActiceById(id);
    	}else {
    		categoryService.enableActiceById(id);
    	}
        return "redirect:/category/list";
    }

}
