package com.eeit.vue3.backend.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eeit.vue3.backend.dto.CartItemRequestDto;
import com.eeit.vue3.backend.dto.CartItemResponseDto;
import com.eeit.vue3.backend.model.entity.CartItem;
import com.eeit.vue3.backend.model.entity.Member;
import com.eeit.vue3.backend.model.entity.Product;
import com.eeit.vue3.backend.model.mapper.CartItemMapper;
import com.eeit.vue3.backend.repository.CartItemRepository;
import com.eeit.vue3.backend.repository.MemberRepository;
import com.eeit.vue3.backend.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
/**
 * 購物車服務層 (Service)
 * <p>
 * 處理購物車相關的業務邏輯，包括：
 * 1. 查詢會員購物車
 * 2. 新增/更新/刪除購物車項目
 * 3. 清空購物車
 */
public class CartService {

	private final CartItemRepository cartItemRepository;
	private final MemberRepository memberRepository;
	private final ProductRepository productRepository;
	private final CartItemMapper cartItemMapper;

	/**
	 * 取得指定會員的購物車內容
	 */
	public List<CartItemResponseDto> getMemberCart(Integer memberId) {
		return cartItemRepository.findByMemberMemberId(memberId).stream()
				.map(cartItemMapper::toDto)
				.collect(Collectors.toList());
	}

	/**
	 * 新增商品至購物車
	 * <p>
	 * 若商品已存在購物車中，則增加數量；若不存在，則新增一筆記錄。
	 */
	@Transactional
	public void addItem(CartItemRequestDto dto) {
		Optional<CartItem> existingItem = cartItemRepository.findByMemberMemberIdAndProductProductId(
				dto.getMemberId(), dto.getProductId());

		if (existingItem.isPresent()) {
			CartItem item = existingItem.get();
			item.setQty(item.getQty() + dto.getQty());
			cartItemRepository.save(item);
		} else {
			Member member = memberRepository.findById(dto.getMemberId())
					.orElseThrow(() -> new RuntimeException("Member not found"));
			Product product = productRepository.findById(dto.getProductId())
					.orElseThrow(() -> new RuntimeException("Product not found"));

			CartItem newItem = new CartItem();
			newItem.setMember(member);
			newItem.setProduct(product);
			newItem.setQty(dto.getQty());
			cartItemRepository.save(newItem);
		}
	}

	/**
	 * 更新購物車商品數量
	 */
	public void updateItem(Integer cartItemId, Integer qty) {
		CartItem item = cartItemRepository.findById(cartItemId)
				.orElseThrow(() -> new RuntimeException("Cart item not found"));
		item.setQty(qty);
		cartItemRepository.save(item);
	}

	/**
	 * 移除購物車中的單一商品
	 */
	public void removeItem(Integer cartItemId) {
		cartItemRepository.deleteById(cartItemId);
	}

	/**
	 * 清空指定會員的購物車
	 */
	@Transactional
	public void clearCart(Integer memberId) {
		cartItemRepository.deleteByMemberMemberId(memberId);
	}
}
