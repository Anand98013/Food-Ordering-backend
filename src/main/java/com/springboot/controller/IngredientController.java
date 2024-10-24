package com.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.model.IngredientCategory;
import com.springboot.model.IngredientsItem;
import com.springboot.request.IngredientCategoryRequest;
import com.springboot.request.IngredientRequest;
import com.springboot.services.IngredientsService;

@CrossOrigin(origins = "https://mealminglefood.vercel.app")
@RestController
@RequestMapping("/api/admin/ingredients")
public class IngredientController {

	
	@Autowired
	private IngredientsService ingredientsService;
	
	@PostMapping("/category")
	public ResponseEntity<IngredientCategory> createIngredientCategory
	(@RequestBody IngredientCategoryRequest req) throws Exception{
		IngredientCategory ingredientCategory = ingredientsService.createIngredientCategory
				(req.getName(), req.getRestaurantId());
		return new ResponseEntity<>(ingredientCategory, HttpStatus.CREATED);
	}
	
	@PostMapping()
	public ResponseEntity<IngredientsItem> createIngredientItem
	(@RequestBody IngredientRequest req) throws Exception{
		IngredientsItem ingredientsItem = ingredientsService.createIngredientItem(req.getRestaurantId(), req.getName(), req.getCategoryId());
		return new ResponseEntity<>(ingredientsItem, HttpStatus.CREATED);
	}
	
	
	@PutMapping("/{id}/stoke")
	public ResponseEntity<IngredientsItem> updateIngredientsStoke
	(@PathVariable Long id) throws Exception{
		IngredientsItem ingredientsItem = ingredientsService.updateStock(id);
		return new ResponseEntity<>(ingredientsItem, HttpStatus.OK);
	}
	
	
	@GetMapping("/restaurant/{id}")
	public ResponseEntity<List<IngredientsItem>> getRestaurantIngredient
	(@PathVariable Long id) throws Exception{
		List<IngredientsItem> ingredientsItem = ingredientsService.findRestaurantIngredient(id);
		return new ResponseEntity<>(ingredientsItem, HttpStatus.OK);
	}
	
	@GetMapping("/restaurant/{id}/category")
	public ResponseEntity<List<IngredientCategory>> getRestaurantIngredientCategory
	(@PathVariable Long id) throws Exception{
		List<IngredientCategory> ingredientCategories = ingredientsService.findIngredientCategoryByRestaurantId(id);
		return new ResponseEntity<>(ingredientCategories, HttpStatus.OK);
	}
}
