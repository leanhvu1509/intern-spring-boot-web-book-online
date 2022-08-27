package com.lavu.admin.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lavu.library.model.Order;
import com.lavu.library.model.enums.OrderStatus;
import com.lavu.library.service.OrderService;

@Controller
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@GetMapping(value = { "", "/list" })
	public String getAllOrders(Model model, Principal principal) {
		if (principal == null) {
			return "redirect:/login";
		}
        model.addAttribute("activeO", "active");
		model.addAttribute("title", "Danh sách đơn hàng");
		model.addAttribute("orders", orderService.getAllOrders());
		return "orders";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String getOrderDetail(@PathVariable("id") Long id, Model model) {
		model.addAttribute("details", orderService.getOrderDetail(id));
		return "orders :: view";
	}

	@RequestMapping(value = "/action/{id}",method = {RequestMethod.GET,RequestMethod.PUT})
	public String actionOrder(@PathVariable("id")Long id, Model model, RedirectAttributes redirectAttributes) {
		Order order = orderService.getOrderById(id);
		if(order.getStatus() == OrderStatus.PENDING) {
			orderService.acceptOrder(id);
		}else{
			orderService.deliveryOrder(id);
		}
		return "redirect:/order";
	}
	@RequestMapping(value = "/delete/{id}",method = {RequestMethod.GET,RequestMethod.PUT})
	public String deleteOrder(@PathVariable("id")Long id, Model model, RedirectAttributes redirectAttributes) {
		orderService.cancelOrder(id);
		return "redirect:/order";
	}
}
