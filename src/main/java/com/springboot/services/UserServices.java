package com.springboot.services;
import com.springboot.model.User;

public interface UserServices {

	public User findUserByJwtToken(String jwt) throws Exception;
	
	public User findUserByEmail(String Email) throws Exception;
		
}
