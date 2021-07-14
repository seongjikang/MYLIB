package com.js.mylib.repository;

import com.js.mylib.entity.Member;
import com.js.mylib.entity.MemberType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

	@Autowired  MemberRepository memberRepository;

	@Test
	public void saveMemberTest() {
		Member member1 = new Member("kang","abcd1@naver.com", "1234",  MemberType.STUDENT);
		memberRepository.save(member1);

		List<Member> member = memberRepository.findMemberById(member1.getId());

		assertThat(member.get(0)).isEqualTo(member1);
	}
}