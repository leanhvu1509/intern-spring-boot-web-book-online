package com.lavu.library.service;

import java.util.List;

import com.lavu.library.dto.ChartDto;
import com.lavu.library.model.Order;
import com.lavu.library.model.OrderDetail;
import com.lavu.library.model.ShoppingCart;

public interface OrderService {

	void saveOrder(ShoppingCart cart,String phone,String address);

	void cancelOrder(Long id);

	void acceptOrder(Long id);

	List<OrderDetail> getOrderDetail(Long id);

	List<Order> getAllOrders();

	void deliveryOrder(Long id);

	Order getOrderById(Long id);

	long getCountOrder();

	List<Order> getAllByPendingStatus();

	Double getSum();

	List<ChartDto> getChartData();


}
