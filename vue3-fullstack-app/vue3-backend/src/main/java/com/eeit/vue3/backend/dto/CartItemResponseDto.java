package com.eeit.vue3.backend.dto;

import lombok.Data;

/**
 * 購物車項目回應 DTO
 * <p>
 * 用於回傳購物車內容給前端顯示，包含商品詳細資訊 (名稱、圖片、價格等)。
 */
@Data
public class CartItemResponseDto {

	private Integer cartItemId;
	private Integer productId;
	private String productName;
	private Integer price;
	private Integer qty;
	private byte[] productPhoto;
	private Integer subtotal; 	// 衍生欄位，方便顯示使用

}
