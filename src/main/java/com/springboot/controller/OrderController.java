package com.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.model.Order;
import com.springboot.model.User;
import com.springboot.request.OrdereRequest;
import com.springboot.response.PaymentResponse;
import com.springboot.services.OrderService;
import com.springboot.services.PaymentService;
import com.springboot.services.UserServices;

@CrossOrigin(origins = "https://mealminglefood.vercel.app")
@RestController
@RequestMapping("/api")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private PaymentService paymentService;
	
	@Autowired
	private UserServices userServices;
	
	@PostMapping("/order")
	public ResponseEntity<PaymentResponse> createOrder(
			@RequestBody OrdereRequest req,
			@RequestHeader("Authorization") String jwt) throws Exception{
		User user = userServices.findUserByJwtToken(jwt);
		Order order = orderService.createOrder(req, user);
		PaymentResponse response = paymentService.createPaymentLink(order);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@GetMapping("/order/user")
	public ResponseEntity<List<Order>> getOrderHistory(
			@RequestHeader("Authorization") String jwt) throws Exception{
		User user = userServices.findUserByJwtToken(jwt);
		List<Order> orders = orderService.getUsersOrder(user.getId());
		return new ResponseEntity<>(orders, HttpStatus.OK);
	}

}
