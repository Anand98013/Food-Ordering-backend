package com.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.model.Order;
import com.springboot.model.User;
import com.springboot.services.OrderService;
import com.springboot.services.UserServices;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/admin")
public class AdminOrderController {

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private UserServices userServices;
	
	
	@GetMapping("/order/restaurant/{id}")
	public ResponseEntity<List<Order>> getOrderHistory(
			@PathVariable Long id, 
			@RequestParam(required = false) String order_status,
			@RequestHeader("Authorization") String jwt) throws Exception{
		User user = userServices.findUserByJwtToken(jwt);
		List<Order> orders = orderService.getRestaurantOrder(id, order_status);
		return new ResponseEntity<>(orders, HttpStatus.OK);
	}
	
	@PutMapping("/order/{id}/{orderStatus}")
	public ResponseEntity<Order> updateOrderStatus(
			@PathVariable Long id, 
			@PathVariable String orderStatus,
			@RequestHeader("Authorization") String jwt) throws Exception{
		User user = userServices.findUserByJwtToken(jwt);
		Order orders = orderService.updateOrder(id, orderStatus);
		return new ResponseEntity<>(orders, HttpStatus.OK);
	}
	
	
}
