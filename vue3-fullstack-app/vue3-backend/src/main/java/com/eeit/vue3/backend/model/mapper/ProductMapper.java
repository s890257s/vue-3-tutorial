package com.eeit.vue3.backend.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.eeit.vue3.backend.dto.ProductCreateDto;
import com.eeit.vue3.backend.dto.ProductResponseDto;
import com.eeit.vue3.backend.dto.ProductUpdateDto;
import com.eeit.vue3.backend.model.entity.Product;

@Mapper(componentModel = "spring")
/**
 * 商品資料轉換器 (Mapper)
 * <p>
 * 改用 MapStruct 自動生成實作類別。
 * 負責將 Entity 轉換為 DTO，並處理 Base64 圖片轉換。
 */
public interface ProductMapper {

	@Mapping(source = "productId", target = "id")
	@Mapping(source = "productName", target = "name")
	@Mapping(source = "productPhoto", target = "photo")
	ProductResponseDto toDto(Product entity);

	@Mapping(source = "name", target = "productName")
	@Mapping(source = "photo", target = "productPhoto")
	@Mapping(target = "productId", ignore = true)
	Product toEntity(ProductCreateDto dto);

	@Mapping(source = "name", target = "productName")
	@Mapping(source = "photo", target = "productPhoto")
	@Mapping(target = "productId", ignore = true)
	Product toEntity(ProductUpdateDto dto);

}
