package com.eeit.vue3.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eeit.vue3.backend.model.entity.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

}
