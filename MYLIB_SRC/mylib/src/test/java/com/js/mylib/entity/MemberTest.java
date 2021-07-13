package com.js.mylib.entity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Entity;
import javax.persistence.EntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberTest {
	@Autowired
	EntityManager em;

	@Test
	public void testEntity() {
		Member member1 = new Member("kang", 32);
		Member member2 = new Member("kim", 28);

		em.persist(member1);
		em.persist(member2);

		em.flush();
		em.clear();

		List<Member> result = em.createQuery("select m from Member m", Member.class)
				.getResultList();

		for (Member member : result) {
			System.out.println("member = " + member);
		}
	}
}