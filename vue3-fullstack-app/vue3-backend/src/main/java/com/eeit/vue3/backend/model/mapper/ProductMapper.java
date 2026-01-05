package com.eeit.vue3.backend.model.mapper;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.eeit.vue3.backend.model.dto.ProductDto;
import com.eeit.vue3.backend.model.entity.Product;
import com.eeit.vue3.backend.utils.CommonUtil;

@Component
public class ProductMapper {

	public ProductDto toDto(Product entity) {
		ProductDto dto = new ProductDto();
		BeanUtils.copyProperties(entity, dto);

		dto.setId(entity.getProductId());
		dto.setName(entity.getProductName());

		// 將 photo 的 byte[] 轉換成 Base64 格式
		byte[] photo = entity.getProductPhoto();
		String base64Image = CommonUtil.getBase64Image(photo);
		dto.setPhoto(base64Image);

		return dto;
	}

}
