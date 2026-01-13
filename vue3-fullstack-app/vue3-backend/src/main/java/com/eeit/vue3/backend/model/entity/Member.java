package com.eeit.vue3.backend.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table
/**
 * 會員實體類 (Entity)
 * <p>
 * 對應資料庫中的 Member 資料表。
 */
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer memberId;

	private Boolean isAdmin;

	private String email;

	private String password;

	private String memberName;

	@Lob
	private byte[] memberPhoto;

}
