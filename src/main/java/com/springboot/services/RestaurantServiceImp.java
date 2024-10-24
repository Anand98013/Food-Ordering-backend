package com.springboot.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.dto.RestaurantDto;
import com.springboot.model.Address;
import com.springboot.model.Restaurant;
import com.springboot.model.User;
import com.springboot.repository.AddressRepository;
import com.springboot.repository.RestaurantRepository;
import com.springboot.repository.UserRepository;
import com.springboot.request.CreateRestaurantRequest;

@Service
public class RestaurantServiceImp implements RestaurantService{
	
	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private UserRepository userRepository;
	

	@Override
	public Restaurant createRestaurant(CreateRestaurantRequest req, User user) {
		
		Address address = addressRepository.save(req.getAddress());
		
		Restaurant restaurant = new Restaurant();
		restaurant.setAddress(address);
		restaurant.setContactInformation(req.getContactInformation());
		restaurant.setCuisineType(req.getCuisineType());
		restaurant.setDescription(req.getDescription());
		restaurant.setImages(req.getImages());
		restaurant.setName(req.getName());
		restaurant.setOpeningHour(req.getOpeningHour());
		restaurant.setRegistrationDate(LocalDateTime.now());
		restaurant.setOwner(user);
		
//		add from gpt
	    restaurant.setOpen(req.getOpen() != null ? req.getOpen() : false); 

		
		return restaurantRepository.save(restaurant);
	}

	
	@Override
	public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updatedRestaurant) throws Exception {
		
		Restaurant restaurant = findRestaurantById(restaurantId);
		
		if(restaurant.getCuisineType()!=null) {
			restaurant.setCuisineType(updatedRestaurant.getCuisineType());
		}
		
		if(restaurant.getDescription()!=null) {
			restaurant.setDescription(updatedRestaurant.getDescription());
		}
		
		if(restaurant.getName()!=null) {
			restaurant.setName(updatedRestaurant.getName());
		}
		
//		add from gpt
		if (updatedRestaurant.getOpeningHour() != null) {
	        restaurant.setOpeningHour(updatedRestaurant.getOpeningHour());
	    }

	    if (updatedRestaurant.getOpen() != null) {
	        restaurant.setOpen(updatedRestaurant.getOpen()); 
	    }
		
		return restaurantRepository.save(restaurant);
	}
	

	@Override
	public void deleteRestaurant(Long restaurantId) throws Exception {

		Restaurant restaurant = findRestaurantById(restaurantId);
		restaurantRepository.delete(restaurant);
			
	}

	
	@Override
	public List<Restaurant> getAllRestaurant() {
		
		return restaurantRepository.findAll();
	}

	@Override
	public List<Restaurant> searchRestaurant(String keyword) {
		
		return restaurantRepository.findBySearchQuery(keyword);
	}

	@Override
	public Restaurant findRestaurantById(Long id) throws Exception {
		
		Optional<Restaurant> optional = restaurantRepository.findById(id);
		
		if(optional.isEmpty()) {
			throw new Exception("Resturant not found with this id"+ id);
		}
		return optional.get();
	}

	@Override
	public Restaurant getRestaurantByUserId(Long userId) throws Exception {
		Restaurant restaurant = restaurantRepository.findByOwnerId(userId);
		
		if(restaurant==null) {
			throw new Exception("Resturant not found with this ownerId"+userId);
		}
		return restaurant;
	}

	@Override
	public RestaurantDto addToFavorites(Long restaurantId, User user) throws Exception {
		
		Restaurant restaurant = findRestaurantById(restaurantId);
		
		RestaurantDto restaurantDto = new RestaurantDto();
		restaurantDto.setDescription(restaurant.getDescription());
		restaurantDto.setImages(restaurant.getImages());
		restaurantDto.setTitle(restaurant.getName());
		restaurantDto.setId(restaurantId);
		
		boolean isFavorited = false;
		List<RestaurantDto> favorites = user.getFavorites();
		for(RestaurantDto favorite : favorites) {
			if(favorite.getId().equals(restaurantId)) {
				isFavorited = true;
				break;
			}
		}
 		
		if(isFavorited) {
			favorites.removeIf(favorite -> favorite.getId().equals(restaurantId));
		}
		else {
			favorites.add(restaurantDto);
		}
		
		userRepository.save(user);
		
		return restaurantDto;
	}

	@Override
	public Restaurant updateRestaurantStatus(Long id) throws Exception {

		Restaurant restaurant = findRestaurantById(id);
		restaurant.setOpen(!restaurant.isOpen()); 
		
		return restaurantRepository.save(restaurant);
	}

}
