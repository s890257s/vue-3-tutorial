package com.eeit.vue3.backend.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eeit.vue3.backend.dto.CartItemCreateDto;
import com.eeit.vue3.backend.dto.CartItemQtyUpdateDto;
import com.eeit.vue3.backend.dto.CartItemResponseDto;
import com.eeit.vue3.backend.model.dto.LoginResponse;
import com.eeit.vue3.backend.service.CartService;

import lombok.RequiredArgsConstructor;

/**
 * 購物車控制器 (Controller)
 * <p>
 * 提供購物車 CRUD 的 API。
 * RESTful 設計，使用隱含的 User Context (/api/cart)。
 */
@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

	private final CartService cartService;

	/**
	 * 取得當前使用者的購物車
	 */
	@GetMapping("/items")
	public List<CartItemResponseDto> getMyCart(@AuthenticationPrincipal LoginResponse user) {
		return cartService.getMemberCart(user.getMemberId());
	}

	/**
	 * 新增商品到購物車
	 */
	@PostMapping("/items")
	public void addItem(@AuthenticationPrincipal LoginResponse user, @RequestBody CartItemCreateDto dto) {
		cartService.addItem(user.getMemberId(), dto);
	}

	/**
	 * 更新購物車商品數量 (PATCH)
	 */
	@PatchMapping("/items/{cartItemId}")
	public void updateQty(@PathVariable Integer cartItemId, @RequestBody CartItemQtyUpdateDto dto) {
		cartService.updateItem(cartItemId, dto.getQty());
	}

	/**
	 * 移除購物車商品
	 */
	@DeleteMapping("/items/{cartItemId}")
	public void removeItem(@PathVariable Integer cartItemId) {
		cartService.removeItem(cartItemId);
	}

	/**
	 * 清空購物車
	 */
	@DeleteMapping("/items")
	public void clearCart(@AuthenticationPrincipal LoginResponse user) {
		cartService.clearCart(user.getMemberId());
	}
}
