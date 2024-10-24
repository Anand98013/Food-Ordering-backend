package com.springboot.request;

import java.util.List;

import com.springboot.model.Address;
import com.springboot.model.ContactInformation;

import lombok.Data;

@Data
public class CreateRestaurantRequest {
	
	private Long id;
	private String name;
	private String description;
	private String cuisineType;
	private Address address;
	private ContactInformation contactInformation;
	private String openingHour;
	private List<String> images;
	
//	add from gpt
	private Boolean open;
}
