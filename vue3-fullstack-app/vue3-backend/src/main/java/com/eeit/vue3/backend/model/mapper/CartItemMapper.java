package com.eeit.vue3.backend.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.eeit.vue3.backend.dto.CartItemResponseDto;
import com.eeit.vue3.backend.model.entity.CartItem;

@Mapper(componentModel = "spring")
/**
 * 購物車資料轉換器 (Mapper)
 * <p>
 * 使用 MapStruct 處理複雜的巢狀物件轉換 (例如從 Product 實體取出名稱、價格等)。
 */
public interface CartItemMapper {

	@Mapping(source = "product.productId", target = "productId")
	@Mapping(source = "product.productName", target = "productName")
	@Mapping(source = "product.price", target = "price")
	@Mapping(source = "product.productPhoto", target = "productPhoto")
	@Mapping(expression = "java(entity.getQty() * entity.getProduct().getPrice())", target = "subtotal")
	CartItemResponseDto toDto(CartItem entity);

}
