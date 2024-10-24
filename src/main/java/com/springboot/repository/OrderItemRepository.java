package com.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{

}
