package com.lavu.admin.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lavu.library.dto.ProductDto;
import com.lavu.library.model.Category;
import com.lavu.library.model.Product;
import com.lavu.library.model.enums.ProductStatus;
import com.lavu.library.service.CategoryService;
import com.lavu.library.service.ProductService;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping(value = {"","/list"})
    public String products(Model model, Principal principal){
        if(principal == null){
            return "redirect:/login";
        }
        List<Product> products = productService.getAllProducts();
        model.addAttribute("title", "Danh sách sản phẩm");
        model.addAttribute("products", products);
        model.addAttribute("activeP", "active");
        return "products";
    }

    @GetMapping("/form")
    public String addProductForm(Model model){
        List<Category> categories = categoryService.getCategoriesByParentIsNull();
        model.addAttribute("title", "Thêm mới sản phẩm");
        model.addAttribute("activeP", "active");
        model.addAttribute("categories", categories);
        model.addAttribute("product", new ProductDto());
        return "product_form";
    }

    @PostMapping("/save")
    public String saveProduct(@ModelAttribute("product")ProductDto productDto,
    							BindingResult result,
                              @RequestParam("imageProduct")MultipartFile imageProduct,
                              RedirectAttributes attributes){
        try {
            productService.createProduct(imageProduct, productDto);
            attributes.addFlashAttribute("success", "Thêm mới thành công");
        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Lỗi server");
        }
        return "redirect:/product/list";
    }

    @GetMapping("/{id}")
    public String updateProductForm(@PathVariable("id") Long id, Model model){
        model.addAttribute("title", "Chi tiết sản phẩm");
        List<Category> categories = categoryService.getCategoryByActive();
        ProductDto productDto = productService.getById(id);
        model.addAttribute("categories", categories);
        model.addAttribute("productDto", productDto);
        return "product_detail";
    }
    
    @PostMapping("/do-update")
    public String processUpdate(@ModelAttribute("productDto") ProductDto productDto,
                                @RequestParam("imageProduct")MultipartFile imageProduct,
                                RedirectAttributes attributes
                                ){
        try {
            productService.updateProduct(imageProduct, productDto);
            attributes.addFlashAttribute("success", "Cập nhật thành công");
        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("error", "Lỗi server");
        }
        return "redirect:/product/list";

    }

    @RequestMapping(value = "/active/{id}", method = {RequestMethod.PUT , RequestMethod.GET})
    public String enabledProduct(@PathVariable("id")Long id, RedirectAttributes attributes){
    	Product product = productService.getProductById(id);
    	if(product.getStatus() == ProductStatus.HIDE) {
    		productService.enableAciveProduct(id);
    	}else {
    		productService.disableActiveProduct(id);
    	}
        return "redirect:/product/list";
    }

    @RequestMapping(value = "/delete/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String deletedProduct(@PathVariable("id") Long id, RedirectAttributes attributes){
        try {
            productService.deleteProductById(id);
            attributes.addFlashAttribute("success", "Xóa thành công!");
        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("error", "Lỗi server");
        }
        return "redirect:/product/list";
    }
    
    @GetMapping("/top-sell")
    public String getAllTopSell(Model model, Principal principal) {
    	if(principal == null){
            return "redirect:/login";
        }
    	List<Product> list = productService.getAllTopSell();
    	model.addAttribute("list", list);
    	return "top-sell";
    }
}
