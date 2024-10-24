package com.springboot.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.model.Category;
import com.springboot.model.Restaurant;
import com.springboot.repository.CategoryRepository;

@Service
public class CategoryServiceImp implements CategoryService{

	@Autowired
	private RestaurantService restaurantService;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Override
	public Category createCategory(String name, Long userId) throws Exception {
		
		Restaurant restaurant = restaurantService.getRestaurantByUserId(userId);
		Category category = new Category();
		category.setName(name);
		category.setRestaurant(restaurant);
		
		return categoryRepository.save(category);
	}

	@Override
	public List<Category> findCategoryByRestaurantId(Long restaurantId) throws Exception {
		Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);
		return categoryRepository.findByRestaurantId(restaurantId);
	}
	


	@Override
	public Category findCategoryById(Long id) throws Exception {
		Optional<Category> optionalCategory = categoryRepository.findById(id);
		if(optionalCategory.isEmpty()) {
			throw new Exception("Category not found");
		}
 		return optionalCategory.get();
	}

}
