package com.js.mylib.repository;

import com.js.mylib.dto.MemberInfoDto;
import com.js.mylib.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {
	Member findMemberById(Long id);
}
