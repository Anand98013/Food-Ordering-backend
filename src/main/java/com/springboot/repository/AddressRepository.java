package com.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long>{
	
	

}
