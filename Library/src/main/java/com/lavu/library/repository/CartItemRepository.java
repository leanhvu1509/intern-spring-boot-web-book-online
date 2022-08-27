package com.lavu.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lavu.library.model.CartItem;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long>{

}
