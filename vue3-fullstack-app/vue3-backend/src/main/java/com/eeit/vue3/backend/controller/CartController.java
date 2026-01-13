package com.eeit.vue3.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eeit.vue3.backend.dto.CartItemRequestDto;
import com.eeit.vue3.backend.dto.CartItemResponseDto;
import com.eeit.vue3.backend.service.CartService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/carts")
@RequiredArgsConstructor
/**
 * 購物車控制器 (Controller)
 * <p>
 * 提供購物車 CRUD 的 API 接口。
 */
public class CartController {

	private final CartService cartService;

	/**
	 * 取得特定會員的購物車
	 */
	@GetMapping("/{memberId}")
	public List<CartItemResponseDto> getMemberCart(@PathVariable Integer memberId) {
		return cartService.getMemberCart(memberId);
	}

	/**
	 * 新增商品到購物車
	 */
	@PostMapping("/items")
	public void addItem(@RequestBody CartItemRequestDto dto) {
		cartService.addItem(dto);
	}

	/**
	 * 更新購物車商品數量
	 */
	@PutMapping("/items/{cartItemId}")
	public void updateItem(@PathVariable Integer cartItemId, @RequestParam Integer qty) {
		cartService.updateItem(cartItemId, qty);
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
	@DeleteMapping("/{memberId}")
	public void clearCart(@PathVariable Integer memberId) {
		cartService.clearCart(memberId);
	}
}
