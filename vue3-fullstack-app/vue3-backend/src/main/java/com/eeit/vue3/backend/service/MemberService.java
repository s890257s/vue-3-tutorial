package com.eeit.vue3.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.eeit.vue3.backend.model.entity.Member;
import com.eeit.vue3.backend.repository.MemberRepository;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public Page<Member> findAll(Pageable pageable) {
        return memberRepository.findAll(pageable);
    }

    public Member insert(Member member) {
        return memberRepository.save(member);
    }

    public Member update(Integer id, Member member) {
        return memberRepository.findById(id).map(existing -> {
            existing.setEmail(member.getEmail());
            existing.setMemberName(member.getMemberName());
            existing.setMemberPhoto(member.getMemberPhoto());
            // Update other fields as necessary, e.g., password if provided
            return memberRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Member not found"));
    }

    public void deleteById(Integer id) {
        memberRepository.deleteById(id);
    }
}
