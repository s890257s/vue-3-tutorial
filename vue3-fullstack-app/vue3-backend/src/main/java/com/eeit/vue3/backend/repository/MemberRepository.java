package com.eeit.vue3.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eeit.vue3.backend.model.entity.Member;

/**
 * 會員資料存取層 (Repository)
 * <p>
 * 繼承 JpaRepository 以獲得標準的 CRUD 功能。
 */
public interface MemberRepository extends JpaRepository<Member, Integer> {

	Optional<Member> findByEmail(String email);

	boolean existsByMemberPhotoIsNotNull();

}
