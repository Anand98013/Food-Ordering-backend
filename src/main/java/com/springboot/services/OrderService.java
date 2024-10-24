package com.springboot.services;

import java.util.List;

import com.springboot.model.Order;
import com.springboot.model.User;
import com.springboot.request.OrdereRequest;

public interface OrderService {
	
	public Order createOrder(OrdereRequest  order, User user) throws Exception;
	
	public Order updateOrder(Long orderId, String orderStatus) throws Exception;
	
	public void cancelOrder(Long orderId) throws Exception;
	
	public List<Order> getUsersOrder(Long userId) throws Exception;
	
	public List<Order> getRestaurantOrder(Long restaurantId, String orderStatus) throws Exception;
	
	public Order findOrderById(Long orderId) throws Exception;
	

}
