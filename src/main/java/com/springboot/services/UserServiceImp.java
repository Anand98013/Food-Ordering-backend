//package com.springboot.services;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.springboot.config.JwtProvider;
//import com.springboot.model.User;
//import com.springboot.repository.UserRepository;
//
//@Service
//public class UserServiceImp implements UserServices{
//
//	@Autowired
//	private UserRepository userRepository;
//	
//	@Autowired
//	private JwtProvider jwtProvider;
//	
//	@Override
//	public User findUserByJwtToken(String jwt) throws Exception {
//		
//		String email = jwtProvider.getEmailFromJwtToken(jwt);
//		User user = findUserByEmail(email);
//		
//		return user;
//	}
//
//	@Override
//	public User findUserByEmail(String email) throws Exception {
//		User user = userRepository.findByEmail(email);
//		
//		if(user==null) {
//			throw new Exception("User not found");
//		}
//		return user;
//	}
//
//}

package com.springboot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.config.JwtProvider;
import com.springboot.model.User;
import com.springboot.repository.UserRepository;

import io.jsonwebtoken.JwtException;

@Service
public class UserServiceImp implements UserServices {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtProvider jwtProvider;

    @Override
    public User findUserByJwtToken(String jwt) throws Exception {
        try {
            String email = jwtProvider.getEmailFromJwtToken(jwt);
            User user = findUserByEmail(email);
            return user;
        } catch (JwtException e) {
            // Invalid JWT token
            throw new Exception("Invalid JWT Token", e);
        } catch (Exception e) {
            // Other exceptions like user not found
            throw new Exception("Error finding user: " + e.getMessage(), e);
        }
    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new Exception("User not found");
        }
        return user;
    }
}

