//package com.springboot.services;
//
//import java.sql.Date;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.springboot.model.Address;
//import com.springboot.model.Cart;
//import com.springboot.model.CartItem;
//import com.springboot.model.Order;
//import com.springboot.model.OrderItem;
//import com.springboot.model.Restaurant;
//import com.springboot.model.User;
//import com.springboot.repository.AddressRepository;
//import com.springboot.repository.OrderItemRepository;
//import com.springboot.repository.OrderRepository;
//import com.springboot.repository.UserRepository;
//import com.springboot.request.OrdereRequest;
//
//@Service
//public class OrderServiceImp implements OrderService{
//	
//	@Autowired
//	private OrderRepository orderRepository;
//	
//	@Autowired
//	private OrderItemRepository orderItemRepository;
//	
//	@Autowired
//	private AddressRepository addressRepository;
//	
//	@Autowired
//	private UserRepository userRepository;
//
//	@Autowired
//	private RestaurantService restaurantService;
//	
//	@Autowired
//	private CartService cartService;
//	
//	
//	@Override
//	public Order createOrder(OrdereRequest order, User user) throws Exception {
//		
//		Address shipAddress = order.getDeliveryAddress();
//		
//		Address savedAddress = addressRepository.save(shipAddress);
//
//		if(!user.getAddresses().contains(savedAddress)) {
//			user.getAddresses().add(savedAddress);
//			userRepository.save(user);
//		}
//		
//		Restaurant restaurant = restaurantService.findRestaurantById(order.getRestaurantId());
//		
//		Order createdOrder = new Order();
//		createdOrder.setCustomer(user);
//		createdOrder.setCreatedAt(new Date(0));  //  Date() correct word
//		createdOrder.setOrderStatus("PENDING");
//		createdOrder.setDileveryAddress(savedAddress);
//		createdOrder.setRestaurant(restaurant);
//		
//		Cart cart = cartService.findCartByUserId(user.getId());
//		
//		List<OrderItem> orderItems = new ArrayList<>();
//		
//		for(CartItem cartItem : cart.getItems()) {
//			OrderItem orderItem = new OrderItem();
//			orderItem.setFood(cartItem.getFood());
//			orderItem.setIngredients(cartItem.getIngredients());
//			orderItem.setQuantity(cartItem.getQuantity());
//			orderItem.setTotalPrize(cartItem.getTotalPrice());
//			
//			OrderItem savedOrderItem = orderItemRepository.save(orderItem);
//			orderItems.add(savedOrderItem);
//		}
//		Long totalPrice = cartService.calculateCartTotals(cart);
//		
//		createdOrder.setItems(orderItems);
//		createdOrder.setTotalPrize(totalPrice);
//		
//		Order savedOrder = orderRepository.save(createdOrder);
//		restaurant.getOrders().add(savedOrder);
//		
//		return createdOrder;
//	}
//
//	@Override
//	public Order updateOrder(Long orderId, String orderStatus) throws Exception {
//		
//		Order order = findOrderById(orderId);
//		if(orderStatus.equals("OUT_FOR_DELIVERY")
//				|| orderStatus.equals("DELIVERED")
//				|| orderStatus.equals("COMPLETE")
//				|| orderStatus.equals("PENDING")
//		){
//			order.setOrderStatus(orderStatus);
//			return orderRepository.save(order);
//		}
//		throw new Exception("Please select a valid order status");
//	}
//
//	@Override
//	public void cancelOrder(Long orderId) throws Exception {
//		
//		Order order = findOrderById(orderId);
//		orderRepository.deleteById(orderId);		
//	}
//
//	@Override
//	public List<Order> getUsersOrder(Long userId) throws Exception {
//		return orderRepository.findByCustomerId(userId);
//	}
//
//	@Override
//	public List<Order> getRestaurantOrder(Long restaurantId, String orderStatus) throws Exception {
//		
//		List<Order> orders = orderRepository.findByRestaurantId(restaurantId);
//		if(orderStatus!=null) {
//			orders = orders.stream().filter(order->
//			order.getOrderStatus().equals(orderStatus)).collect(Collectors.toList());
//		}
//		return orders;
//	}
//
//	@Override
//	public Order findOrderById(Long orderId) throws Exception {
//		
//		Optional<Order> optionalOrder = orderRepository.findById(orderId);
//		if(optionalOrder.isEmpty()) {
//			throw new Exception("Order not found");
//		}
//		return optionalOrder.get();
//	}
//
//}



package com.springboot.services;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.model.Address;
import com.springboot.model.Cart;
import com.springboot.model.CartItem;
import com.springboot.model.Order;
import com.springboot.model.OrderItem;
import com.springboot.model.Restaurant;
import com.springboot.model.User;
import com.springboot.repository.AddressRepository;
import com.springboot.repository.OrderItemRepository;
import com.springboot.repository.OrderRepository;
import com.springboot.repository.UserRepository;
import com.springboot.request.OrdereRequest;

@Service
public class OrderServiceImp implements OrderService{
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RestaurantService restaurantService;
	
	@Autowired
	private CartService cartService;
	
	@Override
	@Transactional
	public Order createOrder(OrdereRequest order, User user) throws Exception {
		
		Address shipAddress = order.getDeliveryAddress();
		Address savedAddress = addressRepository.save(shipAddress);

		if(!user.getAddresses().contains(savedAddress)) {
			user.getAddresses().add(savedAddress);
			userRepository.save(user);
		}
		
		Restaurant restaurant = restaurantService.findRestaurantById(order.getRestaurantId());
		
		Order createdOrder = new Order();
		createdOrder.setCustomer(user);
		createdOrder.setCreatedAt(new Date(System.currentTimeMillis())); 
		createdOrder.setOrderStatus("PENDING");
		createdOrder.setDileveryAddress(savedAddress);
		createdOrder.setRestaurant(restaurant);
		
		Cart cart = cartService.findCartByUserId(user.getId());
		
		List<OrderItem> orderItems = new ArrayList<>();
		
		for(CartItem cartItem : cart.getItems()) {
			OrderItem orderItem = new OrderItem();
			orderItem.setFood(cartItem.getFood());
			orderItem.setIngredients(cartItem.getIngredients());
			orderItem.setQuantity(cartItem.getQuantity());
			orderItem.setTotalPrize(cartItem.getTotalPrice());
			
			OrderItem savedOrderItem = orderItemRepository.save(orderItem);
			orderItems.add(savedOrderItem);
		}
		
		Long totalPrice = cartService.calculateCartTotals(cart); // Use BigDecimal for prices
		createdOrder.setItems(orderItems);
		createdOrder.setTotalPrize(totalPrice);
		
		Order savedOrder = orderRepository.save(createdOrder);
		restaurant.getOrders().add(savedOrder);
		
		return savedOrder;
	}

	@Override
	@Transactional
	public Order updateOrder(Long orderId, String orderStatus) throws Exception {
		if (!isValidOrderStatus(orderStatus)) {
			throw new IllegalArgumentException("Invalid order status");
		}
		
		Order order = findOrderById(orderId);
		order.setOrderStatus(orderStatus);
		return orderRepository.save(order);
	}

	@Override
	@Transactional
	public void cancelOrder(Long orderId) throws Exception {
		Order order = findOrderById(orderId);
		orderRepository.deleteById(orderId);		
	}

	@Override
	public List<Order> getUsersOrder(Long userId) throws Exception {
		return orderRepository.findByCustomerId(userId);
	}

	@Override
	public List<Order> getRestaurantOrder(Long restaurantId, String orderStatus) throws Exception {
		List<Order> orders = orderRepository.findByRestaurantId(restaurantId);
		if (orderStatus != null) {
			orders = orders.stream()
				.filter(order -> order.getOrderStatus().equals(orderStatus))
				.collect(Collectors.toList());
		}
		return orders;
	}

	@Override
	public Order findOrderById(Long orderId) throws Exception {
		Optional<Order> optionalOrder = orderRepository.findById(orderId);
		if (optionalOrder.isEmpty()) {
			throw new Exception("Order not found");
		}
		return optionalOrder.get();
	}
	
	private boolean isValidOrderStatus(String status) {
		return "OUT_FOR_DELIVERY".equals(status) || 
		       "DELIVERED".equals(status) || 
		       "COMPLETE".equals(status) || 
		       "PENDING".equals(status);
	}
}

