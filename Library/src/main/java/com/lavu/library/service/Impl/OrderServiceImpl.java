package com.lavu.library.service.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lavu.library.model.CartItem;
import com.lavu.library.model.Order;
import com.lavu.library.model.OrderDetail;
import com.lavu.library.model.ShoppingCart;
import com.lavu.library.model.enums.OrderStatus;
import com.lavu.library.repository.CartItemRepository;
import com.lavu.library.repository.OrderDetailRepository;
import com.lavu.library.repository.OrderRepository;
import com.lavu.library.repository.ShoppingCartRepository;
import com.lavu.library.service.EmailService;
import com.lavu.library.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderDetailRepository detailRepository;

	@Autowired
	private CartItemRepository itemRepository;

	@Autowired
	private ShoppingCartRepository cartRepository;
	
	@Autowired
	private EmailService emailService;
	
	@Override
	public void saveOrder(ShoppingCart cart,String phone,String address) {
		Order order = new Order();
		order.setOrderDate(new Date());
		order.setAddress(address);
		order.setPhone(phone);
		order.setStatus(OrderStatus.PENDING);
		order.setCustomer(cart.getCustomer());
		order.setTotalPrice(cart.getTotalPrice());
		List<OrderDetail> orderDetails = new ArrayList<>();
		for (CartItem item : cart.getCartItem()) {
			OrderDetail detail = new OrderDetail();
			detail.setOrder(order);
			detail.setQuantity(item.getQuantity());
			detail.setProduct(item.getProduct());
			detail.setTotalPrice(item.getQuantity()*item.getProduct().getPrice());
			detail.setUnitPrice(item.getProduct().getPrice());
			detailRepository.save(detail);
			orderDetails.add(detail);
			itemRepository.delete(item);
		}
		order.setOrderDetail(orderDetails);
		
		try {
			emailService.sendHtmlMessage(order);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		cart.setCartItem(new HashSet<>());
		cart.setTotalItems(0);
		cart.setTotalPrice(0);
		cartRepository.save(cart);
		orderRepository.save(order);
		
	}

	@Override
	public List<OrderDetail> getOrderDetail(Long id){
		return detailRepository.getOrderDetailByOrder(id);
	}
	
	@Override
	public void acceptOrder(Long id) {
		Order order = orderRepository.findById(id).get();
		order.setStatus(OrderStatus.ACCEPT);
		orderRepository.save(order);
	}
	
	@Override
	public void deliveryOrder(Long id) {
		Order order = orderRepository.findById(id).get();
		order.setDeliveryDate(new Date());
		order.setStatus(OrderStatus.DELIVERED);
		orderRepository.save(order);
	}

	@Override
	public void cancelOrder(Long id) {
		Order order = orderRepository.findById(id).get();
		order.setStatus(OrderStatus.CANCEL);
		orderRepository.save(order);
	}
	
	@Override
	public List<Order> getAllOrders(){
		return orderRepository.findAll();
	}

	@Override
	public Order getOrderById(Long id) {
		return orderRepository.findById(id).get();
	}
	@Override
	public long getCountOrder() {
		return orderRepository.getCountOrder();
	}
	
	@Override
	public List<Order> getAllByPendingStatus(){
		return orderRepository.getOrderByPendingStatus();
	}

	@Override
	public Double getSum() {
		return orderRepository.getSumOrder();
	}
}
