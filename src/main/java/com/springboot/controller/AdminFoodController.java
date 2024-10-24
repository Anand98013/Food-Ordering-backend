package com.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.model.Food;
import com.springboot.model.Restaurant;
import com.springboot.model.User;
import com.springboot.request.CreateFoodRequest;
import com.springboot.response.MessageResponse;
import com.springboot.services.FoodService;
import com.springboot.services.RestaurantService;
import com.springboot.services.UserServices;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/admin/food")
public class AdminFoodController {
	
	@Autowired
	FoodService foodService;
	
	@Autowired
	UserServices userServices;
	
	@Autowired
	private RestaurantService restaurantService;
	
	@PostMapping
	public ResponseEntity<Food> createFood(@RequestBody CreateFoodRequest req,
			@RequestHeader("Authorization") String jwt) throws Exception{
		
		User user = userServices.findUserByJwtToken(jwt);
		Restaurant restaurant = restaurantService.getRestaurantByUserId(user.getId());
		Food food = foodService.createFood(req, req.getCategory(), restaurant);
		
		return new ResponseEntity<>(food, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<MessageResponse> deleteFood(@PathVariable Long id,
			@RequestHeader("Authorization") String jwt) throws Exception{
		
		User user = userServices.findUserByJwtToken(jwt);
		foodService.deleteFood(id);
		MessageResponse response = new MessageResponse();
		response.setMessage("Food deleted sucessfully");
		
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	 
	@PutMapping("/{id}")
	public ResponseEntity<Food> updateFoodAvaibilityStatus(@PathVariable Long id,
			@RequestHeader("Authorization") String jwt) throws Exception{
		
		User user = userServices.findUserByJwtToken(jwt);
		Food food = foodService.updateAvailibilityStatus(id);
		
		return new ResponseEntity<>(food, HttpStatus.CREATED);
	}
	

}
