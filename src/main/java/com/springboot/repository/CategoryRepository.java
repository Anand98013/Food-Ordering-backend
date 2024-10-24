package com.springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{
	
//	public List<Category> findByRestaurantId(Long id);
	List<Category> findByRestaurantId(Long restaurantId);

}
