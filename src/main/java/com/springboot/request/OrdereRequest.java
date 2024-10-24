package com.springboot.request;

import com.springboot.model.Address;

import lombok.Data;

@Data
public class OrdereRequest {

	private Long restaurantId;
	private Address deliveryAddress;
}
