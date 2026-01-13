package com.eeit.vue3.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eeit.vue3.backend.model.entity.CartItem;

/**
 * 購物車資料存取層 (Repository)
 * <p>
 * 定義了針對 MemberId 查詢或刪除購物車項目的方法。
 */
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

	List<CartItem> findByMemberMemberId(Integer memberId);

	Optional<CartItem> findByMemberMemberIdAndProductProductId(Integer memberId, Integer productId);

	void deleteByMemberMemberId(Integer memberId);
}

