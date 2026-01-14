package com.eeit.vue3.backend.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eeit.vue3.backend.dto.CartItemCreateDto;
import com.eeit.vue3.backend.dto.CartItemResponseDto;
import com.eeit.vue3.backend.model.entity.CartItem;
import com.eeit.vue3.backend.model.entity.Member;
import com.eeit.vue3.backend.model.entity.Product;
import com.eeit.vue3.backend.model.mapper.CartItemMapper;
import com.eeit.vue3.backend.repository.CartItemRepository;
import com.eeit.vue3.backend.repository.MemberRepository;
import com.eeit.vue3.backend.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

/**
 * 購物車服務層 (Service)
 * <p>
 * 處理購物車相關的業務邏輯，包括：
 * 1. 查詢會員購物車
 * 2. 新增/更新/刪除購物車項目
 * 3. 清空購物車
 */
@Service
@RequiredArgsConstructor
public class CartService {

	private final CartItemRepository cartItemRepository;
	private final MemberRepository memberRepository;
	private final ProductRepository productRepository;
	private final CartItemMapper cartItemMapper;

	/**
	 * 取得指定會員的購物車內容
	 *
	 * @param memberId 會員 ID
	 * @return 購物車項目列表
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
	 *
	 * @param memberId 會員 ID
	 * @param dto      購物車新增資料 (商品 ID、數量)
	 * @throws RuntimeException 當找不到會員或商品時拋出
	 */
	@Transactional
	public void addItem(Integer memberId, CartItemCreateDto dto) {
		Optional<CartItem> existingItem = cartItemRepository.findByMemberMemberIdAndProductProductId(
				memberId, dto.getProductId());

		if (existingItem.isPresent()) {
			CartItem item = existingItem.get();
			item.setQty(item.getQty() + dto.getQty());
			cartItemRepository.save(item);
		} else {
			Member member = memberRepository.findById(memberId)
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
	 *
	 * @param cartItemId 購物車項目 ID
	 * @param qty        新的數量
	 * @throws RuntimeException 當找不到購物車項目時拋出
	 */
	public void updateItem(Integer cartItemId, Integer qty) {
		CartItem item = cartItemRepository.findById(cartItemId)
				.orElseThrow(() -> new RuntimeException("Cart item not found"));
		item.setQty(qty);
		cartItemRepository.save(item);
	}

	/**
	 * 移除購物車中的單一商品
	 *
	 * @param cartItemId 購物車項目 ID
	 */
	public void removeItem(Integer cartItemId) {
		cartItemRepository.deleteById(cartItemId);
	}

	/**
	 * 清空指定會員的購物車
	 * <p>
	 * 刪除該會員的所有購物車項目。
	 *
	 * @param memberId 會員 ID
	 */
	@Transactional
	public void clearCart(Integer memberId) {
		cartItemRepository.deleteByMemberMemberId(memberId);
	}
}
