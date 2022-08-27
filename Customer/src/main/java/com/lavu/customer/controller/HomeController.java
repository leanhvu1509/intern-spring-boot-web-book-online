package com.lavu.customer.controller;

import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lavu.library.model.Customer;
import com.lavu.library.model.ShoppingCart;
import com.lavu.library.service.CategoryService;
import com.lavu.library.service.CustomerService;
import com.lavu.library.service.ProductService;

@Controller
public class HomeController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CustomerService customerService;

    @RequestMapping(value = {"/trang-chu", ""}, method = RequestMethod.GET)
    public String home(Model model, Principal principal, HttpSession session){
        if(principal != null){
            session.setAttribute("username", principal.getName());
            Customer customer = customerService.findByUsername(principal.getName());
            session.setAttribute("name", customer.getName());
            ShoppingCart cart = customer.getCart();
            if(cart != null) {
            	session.setAttribute("totalItems", cart.getTotalItems());
            	session.setAttribute("totalPrice", cart.getTotalPrice());
            }
        }else{
            session.removeAttribute("username");
        }
        model.addAttribute("categories", categoryService.getCategoriesByParentIsNull());
        //model.addAttribute("categories", categoryService.getCategoryByActive());
        model.addAttribute("products", productService.listViewProducts());
        model.addAttribute("title", "Trang chủ");
        return "index";
    }

}
