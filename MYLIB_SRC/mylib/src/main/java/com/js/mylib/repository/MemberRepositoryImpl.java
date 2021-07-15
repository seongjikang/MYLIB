package com.js.mylib.repository;

import com.js.mylib.dto.MemberInfoDto;
import com.js.mylib.dto.QMemberInfoDto;
import com.js.mylib.entity.QMember;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

import static com.js.mylib.entity.QMember.*;

public class MemberRepositoryImpl implements MemberRepositoryCustom {
	private final JPAQueryFactory queryFactory;

	public MemberRepositoryImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

	@Override
	public List<MemberInfoDto> findAllMember() {
		return queryFactory
				.select(new QMemberInfoDto(
						member.id.as("memberId"),
						member.name.as("memberName"),
						member.email,
						member.accumulateTimeMonth,
						member.accumulateTimeDay,
						member.accumulateTimeYear,
						member.type,
						member.message
				))
				.from(member)
				.fetch();
	}

	@Override
	public MemberInfoDto findMemberInfoById(Long memberId) {
		return queryFactory
				.select(new QMemberInfoDto(
						member.id.as("memberId"),
						member.name.as("memberName"),
						member.email,
						member.accumulateTimeMonth,
						member.accumulateTimeDay,
						member.accumulateTimeYear,
						member.type,
						member.message
				))
				.from(member)
				.where(member.id.eq(memberId))
				.fetchOne();
	}

	@Override
	public void deleteMember(Long memberId) {
		queryFactory
				.delete(member)
				.where(member.id.eq(memberId))
				.execute();
	}
}
