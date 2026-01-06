package com.eeit.vue3.backend.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponse {

	private Integer id;
	private String name;
	private Integer price;
	private String color;
	private String photo;

}
