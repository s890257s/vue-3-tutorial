package com.eeit.vue3.backend.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.util.Strings;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import jakarta.persistence.criteria.Predicate;

import com.eeit.vue3.backend.model.dto.PageQuery;
import com.eeit.vue3.backend.dto.ProductCreateDto;
import com.eeit.vue3.backend.dto.ProductResponseDto;
import com.eeit.vue3.backend.dto.ProductUpdateDto;
import com.eeit.vue3.backend.model.dto.ProductQuery;
import com.eeit.vue3.backend.model.entity.Product;
import com.eeit.vue3.backend.model.mapper.ProductMapper;
import com.eeit.vue3.backend.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

/**
 * 商品服務層 (Service)
 * <p>
 * 負責商品查詢邏輯，包含：
 * 1. 取得所有商品
 * 2. 動態條件查詢 (Specification)
 * 3. 分頁查詢
 */
@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductMapper productMapper;

	private final ProductRepository productRepository;



	/**
	 * 整合查詢 (列表 + 搜尋 + 分頁)
	 *
	 * @param productQuery 商品搜尋條件
	 * @param pageQuery    分頁條件
	 * @return 商品分頁結果 (包含 DTO 列表)
	 */
	public Page<ProductResponseDto> getProducts(ProductQuery productQuery, PageQuery pageQuery) {

		// 1. 建立 Specification (搜尋條件)
		Specification<Product> spec = (root, query, cb) -> {
			List<Predicate> predicates = new ArrayList<>();

			if (productQuery != null) {
				if (Strings.isNotBlank(productQuery.getName())) {
					predicates.add(cb.like(root.get("productName"), "%" + productQuery.getName() + "%"));
				}

				if (productQuery.getMin() != null) {
					predicates.add(cb.greaterThanOrEqualTo(root.get("price"), productQuery.getMin()));
				}

				if (productQuery.getMax() != null) {
					predicates.add(cb.lessThanOrEqualTo(root.get("price"), productQuery.getMax()));
				}

				if (Strings.isNotBlank(productQuery.getColors())) {
					In<Object> in = cb.in(root.get("color"));
					for (String c : productQuery.getColors().split(",")) {
						in.value(c.trim());
					}
					predicates.add(in);
				}
			}

			return cb.and(predicates.toArray(new Predicate[0]));
		};

		// 2. 建立 PageRequest (分頁與排序)
		PageRequest pageRequest = PageRequest.of(
				pageQuery.getPage(),
				pageQuery.getSize(),
				pageQuery.getDirectionEnum(),
				pageQuery.getSort());

		// 3. 查詢並轉換
		return productRepository.findAll(spec, pageRequest)
				.map(productMapper::toDto);
	}

	/**
	 * 取得單一商品
	 *
	 * @param id 商品 ID
	 * @return 商品詳細資料 (DTO)
	 * @throws RuntimeException 當商品不存在時拋出
	 */
	public ProductResponseDto getProductById(Integer id) {
		return productRepository.findById(id)
				.map(productMapper::toDto)
				.orElseThrow(() -> new RuntimeException("Product not found"));
	}

	/**
	 * 新增商品
	 *
	 * @param dto 商品建立資料
	 * @return 新增後的商品詳細資料
	 */
	public ProductResponseDto createProduct(ProductCreateDto dto) {
		Product product = productMapper.toEntity(dto);
		product = productRepository.save(product);
		return productMapper.toDto(product);
	}

	/**
	 * 更新商品
	 *
	 * @param id  商品 ID
	 * @param dto 商品更新資料
	 * @return 更新後的商品詳細資料
	 * @throws RuntimeException 當商品不存在時拋出
	 */
	public ProductResponseDto updateProduct(Integer id, ProductUpdateDto dto) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Product not found"));
		
		// 使用 MapStruct 更新或手動更新。這裡我們使用 mapper 從 DTO 更新實體。
		// 由於我們的 mapper 實作 `toEntity` 會建立一個「新」實體，我們應該手動更新，
		// 或者在 mapper 中新增 `@MappingTarget` 方法。
		// 為了簡化並遵循提示的 `toEntity` 模式，我暫時採用手動映射，
		// 或假設直接替換欄位。
		// 實際上，讓我們保持簡單：
		product.setProductName(dto.getName());
		product.setPrice(dto.getPrice());
		product.setColor(dto.getColor());
		if (dto.getPhoto() != null && dto.getPhoto().length > 0) {
			product.setProductPhoto(dto.getPhoto());
		}
		
		product = productRepository.save(product);
		return productMapper.toDto(product);
	}

	/**
	 * 刪除商品
	 *
	 * @param id 商品 ID
	 */
	public void removeProduct(Integer id) {
		productRepository.deleteById(id);
	}
}
