package com.eeit.vue3.backend.model.dto;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import lombok.Getter;

/**
 * 此類無 setter 方法，只能透過建構子建立。
 */
@Getter
public class PageQuery {
	/**
	 * page: 當前頁數，0 為第一頁 <br>
	 * size: 每頁筆數 <br>
	 * direction: ASC(升冪)、DESC(降冪) <br>
	 * sort: 排序欄位
	 */
	public PageQuery(Integer page, Integer size, String direction, String sort) {
		this.page = (page == null || page < 1) ? 0 : page - 1; // 轉為 0-indexed
		this.size = (size == null || size < 1) ? 10 : size;
		this.direction = (direction == null) ? "ASC" : direction;
		this.sort = (sort == null) ? "productId" : sort;

		this.directionEnum = switch (this.direction.trim().toLowerCase()) {
			case "asc" -> Sort.Direction.ASC;
			case "desc" -> Sort.Direction.DESC;
			default -> Sort.Direction.ASC;
		};
	}

	private Integer page;
	private Integer size;
	private String direction;
	private String sort;
	private Direction directionEnum;
}
