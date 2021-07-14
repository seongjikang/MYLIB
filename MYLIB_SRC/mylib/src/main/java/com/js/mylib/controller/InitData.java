package com.js.mylib.controller;

import com.js.mylib.entity.Library;
import com.js.mylib.entity.Member;
import com.js.mylib.entity.MemberType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Profile("local")
@Component
@RequiredArgsConstructor
public class InitData {
	private final InitDataService initDataService;

	@PostConstruct
	public void init() {
		initDataService.init();
	}

	@Component
	static class InitDataService{

		@PersistenceContext
		private EntityManager em;

		@Transactional
		public void init() {
			Member member0 = new Member("member0","0abcd@naver.com", "1234",  MemberType.STUDENT);
			Member member1 = new Member("member1","1abcd@naver.com", "1234",  MemberType.STUDENT);
			em.persist(member0);
			em.persist(member1);

			Library library0 = new Library("도서관0", "수능까지 화이팅!!", member0.getId(), LocalDateTime.now(), LocalDateTime.of(2021,12,30,00,00), 20);
			em.persist(library0);
			member0.setLibrary(library0);
			em.persist(member0);

			em.flush();
			em.clear();
//
//			Library library1 = new Library("도서관1", "수능까지 화이팅!!", member1.getId(), LocalDateTime.now(), LocalDateTime.of(2021,12,30,00,00), 20);
//			em.persist(library1);
//			member1.setLibrary(library1);
//			em.persist(member1);

			for(int i = 2; i<100; i= i+2) {
				em.persist(new Member("member"+i,i+"abcd@naver.com", "1234",  MemberType.STUDENT, library0) );
			}
			for(int i = 3; i<100; i= i+2) {
				em.persist(new Member("member"+i,i+"abcd@naver.com", "1234",  MemberType.STUDENT, null) );
			}

			em.flush();
			em.clear();
		}
	}
}
