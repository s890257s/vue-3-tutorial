package com.eeit.vue3.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eeit.vue3.backend.model.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Integer> {

	Optional<Member> findByEmail(String email);

	boolean existsByMemberPhotoIsNotNull();

}
