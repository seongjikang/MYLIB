package com.js.mylib.service;

import com.js.mylib.dto.LibraryInfoDto;
import com.js.mylib.entity.Library;
import com.js.mylib.entity.Member;
import com.js.mylib.entity.MemberType;

import com.querydsl.jpa.impl.JPAQueryFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

	@Autowired
	EntityManager em;

	@Autowired LibraryService libraryService;

	JPAQueryFactory queryFactory;

	@BeforeEach
	public void before() {
		queryFactory = new JPAQueryFactory(em);
	}

	@Test
	public void deleteLibraryTest() {
		Member member0 = new Member("member0","0abcd@naver.com", "1234",  MemberType.STUDENT);
		Member member1 = new Member("member1","1abcd@naver.com", "1234",  MemberType.STUDENT);
		em.persist(member0);
		em.persist(member1);

		Library library0 = new Library("도서관0", "수능까지 화이팅!!", member0.getId(), LocalDateTime.now(), LocalDateTime.of(2021,12,30,00,00), 20);
		em.persist(library0);

		member0.setLibrary(library0);
		em.persist(member0);

		LibraryInfoDto findLibrary = libraryService.findLibraryInfoById(library0.getId());
		assertThat(library0.getId()).isEqualTo(findLibrary.getLibraryId());
		assertThat(library0.getMembers().size()).isEqualTo(1);

		libraryService.exitLibrary(member0.getId(), library0.getId());

		LibraryInfoDto removeLibrary = libraryService.findLibraryInfoById(library0.getId());
		assertThat(removeLibrary).isEqualTo(null);

		em.flush();
		em.clear();

	}

	@Test
	public void exitLibraryTest() {
		Member member0 = new Member("member0","0abcd@naver.com", "1234",  MemberType.STUDENT);
		Member member1 = new Member("member1","1abcd@naver.com", "1234",  MemberType.STUDENT);
		em.persist(member0);
		em.persist(member1);

		Library library0 = new Library("도서관0", "수능까지 화이팅!!", member0.getId(), LocalDateTime.now(), LocalDateTime.of(2021,12,30,00,00), 20);
		em.persist(library0);

		member0.setLibrary(library0);
		em.persist(member0);
		member1.setLibrary(library0);
		em.persist(member1);

		LibraryInfoDto findLibrary = libraryService.findLibraryInfoById(library0.getId());
		assertThat(library0.getId()).isEqualTo(findLibrary.getLibraryId());
		assertThat(library0.getMembers().size()).isEqualTo(2);
		assertThat(library0.getReaderId()).isEqualTo(member0.getId());

		libraryService.exitLibrary(member1.getId(), library0.getId());

		Library result = libraryService.findLibraryById(library0.getId());
		assertThat(result.getMembers().size()).isEqualTo(1);
		assertThat(result.getReaderId()).isEqualTo(member0.getId());

		em.flush();
		em.clear();
	}

	@Test
	public void changeReaderTest() {
		Member member0 = new Member("member0","0abcd@naver.com", "1234",  MemberType.STUDENT);
		Member member1 = new Member("member1","1abcd@naver.com", "1234",  MemberType.STUDENT);
		em.persist(member0);
		em.persist(member1);

		Library library0 = new Library("도서관0", "수능까지 화이팅!!", member0.getId(), LocalDateTime.now(), LocalDateTime.of(2021,12,30,00,00), 20);
		em.persist(library0);

		member0.setLibrary(library0);
		em.persist(member0);
		member1.setLibrary(library0);
		em.persist(member1);

		LibraryInfoDto findLibrary = libraryService.findLibraryInfoById(library0.getId());
		assertThat(library0.getId()).isEqualTo(findLibrary.getLibraryId());
		assertThat(library0.getMembers().size()).isEqualTo(2);
		assertThat(library0.getReaderId()).isEqualTo(member0.getId());

		libraryService.exitLibrary(member0.getId(), library0.getId());

		Library result = libraryService.findLibraryById(library0.getId());
		assertThat(result.getMembers().size()).isEqualTo(1);
		assertThat(result.getReaderId()).isEqualTo(member1.getId());

		em.flush();
		em.clear();
	}

}