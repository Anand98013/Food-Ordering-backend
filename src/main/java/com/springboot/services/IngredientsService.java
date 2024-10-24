package com.springboot.services;

import java.util.List;

import com.springboot.model.IngredientCategory;
import com.springboot.model.IngredientsItem;

public interface IngredientsService {
	
	public IngredientCategory createIngredientCategory(String name, Long restaurantId) throws Exception;
	
	public IngredientCategory findIngredientById(Long id) throws Exception;
	
	List<IngredientCategory> findIngredientCategoryByRestaurantId(Long id) throws Exception;
	
	public IngredientsItem createIngredientItem(Long restaurantId, String ingredientName, Long categoryId) throws Exception;
	
	public List<IngredientsItem> findRestaurantIngredient(Long restaurantId);
	
	public IngredientsItem updateStock(Long id) throws Exception;

}
