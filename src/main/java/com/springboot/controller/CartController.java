package com.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.model.Cart;
import com.springboot.model.CartItem;
import com.springboot.model.User;
import com.springboot.request.AddCartItemRequest;
import com.springboot.request.UpdateCartItemRequest;
import com.springboot.services.CartService;
import com.springboot.services.UserServices;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class CartController {

	@Autowired
	private CartService cartService;
	
	@Autowired
	private UserServices userServices;
	
	@PutMapping("/cart/add")
	public ResponseEntity<CartItem> addItemToCart(
			@RequestBody AddCartItemRequest req,
			@RequestHeader("Authorization") String jwt) throws Exception{
		CartItem cartItem = cartService.addItemToCart(req, jwt);
		return new ResponseEntity<>(cartItem, HttpStatus.OK);
	}
	
//	@PutMapping("/cart-item/update")
//	public ResponseEntity<CartItem> updateCartItemQuantity(
//			@RequestBody UpdateCartItemRequest req,
//			@RequestHeader("Authorization") String jwt) throws Exception{
//		CartItem cartItem = cartService.updateCartItemQuantity(req.getCartItemId(), req.getQuantity());
//		return new ResponseEntity<>(cartItem, HttpStatus.OK);
//	}
	
	@PutMapping("/cart-item/update")
	public ResponseEntity<CartItem> updateCartItemQuantity(
	    @RequestBody UpdateCartItemRequest req,
	    @RequestHeader("Authorization") String jwt) throws Exception {
	    CartItem cartItem = cartService.updateCartItemQuantity(req.getCartItemId(), req.getQuantity());
	    Cart cart = cartService.findCartByUserId(userServices.findUserByJwtToken(jwt).getId());
	    cart.setTotal(cartService.calculateCartTotals(cart));
	    return new ResponseEntity<>(cartItem, HttpStatus.OK);
	}

	
	@DeleteMapping("/cart-item/{id}/remove")
	public ResponseEntity<Cart> removeCartItem(
			@PathVariable Long id,
			@RequestHeader("Authorization") String jwt) throws Exception{
		Cart cart = cartService.removeItemFromCart(id, jwt);
		return new ResponseEntity<>(cart, HttpStatus.OK);
	}
	
	@PutMapping("/cart/clear")
	public ResponseEntity<Cart> clearCart(
			@RequestHeader("Authorization") String jwt) throws Exception{
		User user = userServices.findUserByJwtToken(jwt);
		Cart cart = cartService.clearCart(user.getId());
		return new ResponseEntity<>(cart, HttpStatus.OK);
	}
	
	@GetMapping("/cart")
	public ResponseEntity<Cart> findUserCart(
			@RequestHeader("Authorization") String jwt) throws Exception{
		
		User user = userServices.findUserByJwtToken(jwt);
		Cart cart = cartService.findCartByUserId(user.getId());
		return new ResponseEntity<>(cart, HttpStatus.OK);
	}
	
}
