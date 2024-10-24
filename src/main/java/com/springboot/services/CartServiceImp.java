package com.springboot.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.model.Cart;
import com.springboot.model.CartItem;
import com.springboot.model.Food;
import com.springboot.model.User;
import com.springboot.repository.CartItemRepository;
import com.springboot.repository.CartRepository;
import com.springboot.request.AddCartItemRequest;

@Service
public class CartServiceImp implements CartService{

	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private UserServices userServices;
	
	@Autowired
	private CartItemRepository cartItemRepository;
	
	@Autowired
	private FoodService foodService;
	
	@Override
	public CartItem addItemToCart(AddCartItemRequest req, String jwt) throws Exception {
		User user = userServices.findUserByJwtToken(jwt);
		Food food = foodService.findFoodById(req.getFoodId());
		Cart cart = cartRepository.findByCustomerId(user.getId());

		for (CartItem cartItem : cart.getItems()) {
			if (cartItem.getFood().equals(food)) {
				int newQuantity = cartItem.getQuantity() + req.getQuantity();
				return updateCartItemQuantity(cartItem.getId(), newQuantity);
			}
		}

		CartItem newCartItem = new CartItem();
		newCartItem.setFood(food);
		newCartItem.setCart(cart);
		newCartItem.setQuantity(req.getQuantity());
		newCartItem.setIngredients(req.getIngredients());
		newCartItem.setTotalPrice(req.getQuantity()*food.getPrice());
		
		CartItem saveCartItem = cartItemRepository.save(newCartItem);
		cart.getItems().add(saveCartItem);
		
		return saveCartItem;
	}

	
	@Override
	public CartItem updateCartItemQuantity(Long cartItemId, int quantity) throws Exception {
		
		Optional<CartItem> cartItemOptional =  cartItemRepository.findById(cartItemId);
		if(cartItemOptional.isEmpty()) {
			throw new Exception("Cart Item not found");
		}
		CartItem cartItem = cartItemOptional.get();
		cartItem.setQuantity(quantity);
		
		cartItem.setTotalPrice(cartItem.getFood().getPrice()*quantity);
		
		return cartItemRepository.save(cartItem);
	}

	@Override
	public Cart removeItemFromCart(Long cartItemId, String jwt) throws Exception {
		User user = userServices.findUserByJwtToken(jwt);
		Cart cart = cartRepository.findByCustomerId(user.getId());
		
		Optional<CartItem> cartItemOptional = cartItemRepository.findById(cartItemId);
		if (cartItemOptional.isEmpty()) {
			throw new Exception("Cart Item not found");
		}
		
		CartItem item = cartItemOptional.get();
		cart.getItems().remove(item);
		return cartRepository.save(cart);
	}

	@Override
	public Long calculateCartTotals(Cart cart) throws Exception {
		Long total = 0L;
		
		for(CartItem cartItem : cart.getItems()) {
			total+=cartItem.getFood().getPrice()*cartItem.getQuantity();
		}
		return total;
	}

	@Override
	public Cart findCartById(Long id) throws Exception {
		Optional<Cart> optionalCart = cartRepository.findById(id);
		if (optionalCart.isEmpty()) {
			throw new Exception("Cart not found with id " + id);
		}
		return optionalCart.get();
	}

	@Override
	public Cart findCartByUserId(Long userId) throws Exception {
		Cart cart = cartRepository.findByCustomerId(userId);
		cart.setTotal(calculateCartTotals(cart));
		return cart;
	}

	@Override
	public Cart clearCart(Long userId) throws Exception {
		Cart cart = findCartByUserId(userId);
		cart.getItems().clear();
		return cartRepository.save(cart);
	}
}
