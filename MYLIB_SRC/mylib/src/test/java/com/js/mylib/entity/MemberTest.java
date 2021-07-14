package com.js.mylib.entity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberTest {
	@Autowired
	EntityManager em;

	@Test
	public void testEntity() {
		Member member1 = new Member("kang","abcd@naver.com", "1234",  MemberType.STUDENT);
		Member member2 = new Member("kim","abcde@naver.com", "1234", MemberType.STUDENT);

		em.persist(member1);
		em.persist(member2);

		Library library1 = new Library("도서관1", "수능까지 화이팅!!", member1.getId(),LocalDateTime.now(), LocalDateTime.of(2021,12,30,00,00), 20);
		em.persist(library1);
		member1.setLibrary(library1);
		member2.setLibrary(library1);

		em.persist(member1);
		em.persist(member2);

		for(int i=0; i<library1.getMembers().size(); i++) {
			System.out.println(library1.getMembers().get(i).getName());
		}

	}
}