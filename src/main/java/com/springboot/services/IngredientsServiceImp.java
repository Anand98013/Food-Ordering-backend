package com.springboot.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.model.IngredientCategory;
import com.springboot.model.IngredientsItem;
import com.springboot.model.Restaurant;
import com.springboot.repository.IngredientCategoryRepository;
import com.springboot.repository.IngredientItemRepository;

@Service
public class IngredientsServiceImp implements IngredientsService{
	
	@Autowired
	private IngredientItemRepository ingredientItemRepository;
	
	@Autowired
	private IngredientCategoryRepository ingredientCategoryRepository;
	
	@Autowired
	RestaurantService restaurantService;

	@Override
	public IngredientCategory createIngredientCategory(String name, Long restaurantId) throws Exception {
		
		Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);
		
		IngredientCategory ingredientCategory = new IngredientCategory();
		ingredientCategory.setRestaurant(restaurant);
		ingredientCategory.setName(name);
		
		return ingredientCategoryRepository.save(ingredientCategory);
	}

	@Override
	public IngredientCategory findIngredientById(Long id) throws Exception {

		Optional<IngredientCategory> optional = ingredientCategoryRepository.findById(id);
		
		if(optional.isEmpty()) {
			throw new Exception("Ingrdient Category not found");
		}
		return optional.get();
	}

	@Override
	public List<IngredientCategory> findIngredientCategoryByRestaurantId(Long id) throws Exception {

		restaurantService.findRestaurantById(id);
		return ingredientCategoryRepository.findByRestaurantId(id);
	}

	@Override
	public IngredientsItem createIngredientItem(Long restaurantId, String ingredientName, Long categoryId)
			throws Exception {
		
		Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);
		IngredientCategory category = findIngredientById(categoryId); //dought hain 6:05:43
		
		IngredientsItem ingredientsItem = new IngredientsItem();
		ingredientsItem.setName(ingredientName);
		ingredientsItem.setRestaurant(restaurant);
		ingredientsItem.setCategory(category);
		
		IngredientsItem ingredientsItem2 = ingredientItemRepository.save(ingredientsItem);
		category.getIngredients().add(ingredientsItem2);
		

		return ingredientsItem2;
	}

	@Override
	public List<IngredientsItem> findRestaurantIngredient(Long restaurantId) {
		
		return ingredientItemRepository.findByRestaurantId(restaurantId);
	}

	@Override
	public IngredientsItem updateStock(Long id) throws Exception {
		
		Optional<IngredientsItem> inOptional = ingredientItemRepository.findById(id);
		if(inOptional.isEmpty()) {
			throw new Exception("Ingredient Not found");
		}
		
		IngredientsItem ingredientsItem = inOptional.get();
		ingredientsItem.setInStoke(!ingredientsItem.isInStoke());
		return ingredientItemRepository.save(ingredientsItem);
	}

}
