package com.js.mylib.repository;

import com.js.mylib.dto.UpdateLibraryRequestDto;
import com.js.mylib.dto.UpdateMemberRequestDto;
import com.js.mylib.entity.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.js.mylib.entity.QLibrary.*;
import static com.js.mylib.entity.QMember.*;

@Repository
@RequiredArgsConstructor
public class CustomQueryRepository {
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


		return library.getId();
	}

	public Long createLibrary(Long memberId, Library library) {
		em.persist(library);

		Member findMember = queryFactory
				.selectFrom(member)
				.where(member.id.eq(memberId))
				.fetchOne();

		findMember.setLibrary(library);

		em.flush();
		em.clear();

		return library.getId();
	}

	@Transactional
	public void changeReader(Long memberId, Long libraryId) {
		Library library = queryFactory
				.select(QLibrary.library)
				.from(QLibrary.library)
				.where(QLibrary.library.id.eq(libraryId))
				.fetchOne();

		for (int i=0; i<library.getMembers().size(); i++) {
			if(library.getMembers().get(i).getId() == memberId) {
				library.getMembers().remove(i);
				break;
			}
		}
		long execute = queryFactory.update(member)
				.set(member.library, (Library) null)
				.where(member.id.eq(memberId))
				.execute();

		library.setReaderId(library.getMembers().get(0).getId());
	}

	@Modifying
	@Transactional
	public void exitLibrary(Long memberId, Long libraryId) {
		Library findLibrary = queryFactory
				.select(library)
				.from(library)
				.where(library.id.eq(libraryId))
				.fetchOne();

		for (int i=0; i<findLibrary.getMembers().size(); i++) {
			if(findLibrary.getMembers().get(i).getId() == memberId) {
				findLibrary.getMembers().remove(i);
				break;
			}
		}

		long execute = queryFactory.update(member)
				.set(member.library, (Library) null)
				.where(member.id.eq(memberId))
				.execute();

	}

	public Long updateLibrary(Long libraryId, Long memberId, UpdateLibraryRequestDto updateLibraryRequest) {
		long execute = queryFactory.update(library)
				.set(library.name, updateLibraryRequest.getName())
				.set(library.description, updateLibraryRequest.getDescription())
				.set(library.endTime, LocalDateTime.parse(updateLibraryRequest.getEndTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
				.set(library.memberLimit, updateLibraryRequest.getMemberLimit())
				.where(
						library.readerId.eq(memberId), library.id.eq(libraryId)
				)
				.execute();

		em.flush();
		em.clear();

		if(execute > 0) {
			return libraryId;
		}

		return -1l;
	}
}
