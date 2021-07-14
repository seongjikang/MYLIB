package com.js.mylib.repository;

import com.js.mylib.dto.UpdateMemberRequestDto;
import com.js.mylib.entity.Library;
import com.js.mylib.entity.Member;
import com.js.mylib.entity.MemberType;
import com.js.mylib.entity.QMember;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import static com.js.mylib.entity.QMember.*;

@Repository
@RequiredArgsConstructor
public class MemberJPARepository {
	private final EntityManager em;
	private final JPAQueryFactory queryFactory;
	private final LibraryRepository libraryRepository;
	private final MemberRepository memberRepository;
	public void saveMember(Member member) {
		em.persist(member);

		em.flush();
		em.clear();
	}

	public Long updateMember(Long memberId, UpdateMemberRequestDto request, MemberType type) {
		long execute = queryFactory.update(member)
				.where(member.id.eq(memberId))
				.set(member.name, request.getName())
				.set(member.message, request.getMessage())
				.set(member.accumulateTimeMonth, request.getAccumulateTimeMonth())
				.set(member.accumulateTimeYear, request.getAccumulateTimeYear())
				.set(member.accumulateTimeDay, request.getAccumulateTimeDay())
				.set(member.memberImage, request.getMemberImage())
				.set(member.type, type)
				.execute();

		em.flush();
		em.clear();


		if(execute > 0) {
			return memberId;
		}

		return -1l;
	}

	public Long enterLibrary(Long libraryId, Long memberId) {
		Library library = libraryRepository.findLibraryById(libraryId);
		Member findMember = memberRepository.findMemberById(memberId);

		findMember.setLibrary(library);

		em.flush();
		em.clear();


		return findMember.getId();
	}
}
