//package com.springboot.repository;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import com.springboot.model.CartItem;
//
//public interface CartItemRepository extends JpaRepository<CartItem, Long>{
//	
//	public CartItem findByCustomerId(Long userId);
//
//
//}


package com.springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long>{
    List<CartItem> findByCartId(Long cartId);
}

