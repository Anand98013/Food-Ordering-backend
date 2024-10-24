package com.springboot.services;

import java.util.List;

import com.springboot.model.Category;
import com.springboot.model.Food;
import com.springboot.model.Restaurant;
import com.springboot.request.CreateFoodRequest;

public interface FoodService {

	public Food createFood(CreateFoodRequest req, Category category, Restaurant restaurant);
	
	void deleteFood(Long foodId) throws Exception;
	
	public List<Food> getRestaurantsFood(Long restaurantId, 
										boolean isVegitarain, 
										boolean isNonveg, 
										boolean isSeasonal, 
										String foodCategory);
	
	public List<Food> searchFood(String keyword);
	
	public Food findFoodById(Long foodId) throws Exception;
	
	public Food updateAvailibilityStatus(Long foodId) throws Exception;
	
	
}
