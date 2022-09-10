package com.lavu.admin.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.lavu.library.dto.ChartDto;
import com.lavu.library.model.Admin;
import com.lavu.library.service.AdminService;
import com.lavu.library.service.CustomerService;
import com.lavu.library.service.OrderService;
import com.lavu.library.service.ProductService;

@Controller
public class HomeController {

	@Autowired
	private ProductService productService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private AdminService adminService;

	@RequestMapping(value = { "/trang-chu", "" })
	public String index(Model model, HttpSession session) {
		model.addAttribute("title", "Trang chủ Admin");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			session.removeAttribute("nameAdmin");
			return "redirect:/login";
		}
		String name = authentication.getName();
		Admin admin = adminService.findByUsername(name);
		session.setAttribute("nameAdmin", admin.getName());
		model.addAttribute("activeD", "active");
		model.addAttribute("productCount", productService.getCountProduct());
		model.addAttribute("customerCount", customerService.getCountCustomer());
		model.addAttribute("orderCount", orderService.getCountOrder());
		model.addAttribute("orders", orderService.getAllByPendingStatus());
		model.addAttribute("total", orderService.getSum());
		model.addAttribute("size", orderService.getAllByPendingStatus().size());
		model.addAttribute("tops", productService.getProductTopSell());

		return "index";
	}

	@RequestMapping(value = "/profile/{id}", method = RequestMethod.GET)
	public String getOrderDetail(@PathVariable("id") Long id, Model model) {
		return "index :: view";
	}

	@RequestMapping("/chart")
	@ResponseBody
	public String getTotal() {
		List<ChartDto> dataList = orderService.getChartData();
		JsonArray month = new JsonArray();
		JsonArray total = new JsonArray();
		JsonObject json = new JsonObject();
		dataList.forEach(data->{
			month.add(data.getMonth());
			total.add(data.getTotal());
		});
		json.add("month", month);
		json.add("total", total);
		return json.toString();
	}

}
