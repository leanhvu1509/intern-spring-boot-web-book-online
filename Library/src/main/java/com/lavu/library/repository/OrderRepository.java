package com.lavu.library.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lavu.library.dto.ChartDto;
import com.lavu.library.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

	@Query(value = "SELECT COUNT(*) FROM orders o", nativeQuery = true)
	long getCountOrder();

	@Query(value = "SELECT * FROM orders o WHERE o.status = 0", nativeQuery = true)
	List<Order> getOrderByPendingStatus();

	@Query(value = "SELECT SUM(o.total_price) FROM orders o", nativeQuery = true)
	double getSumOrder();
	

	@Query(value = "SELECT new com.lavu.library.dto.ChartDto(DATE_FORMAT(o.orderDate, '%b'),SUM(o.totalPrice))" +
					"FROM Order o WHERE o.status=2 GROUP BY MONTH(o.orderDate) ORDER BY MONTH(o.orderDate)")
	List<ChartDto> getChartData();
	
}
