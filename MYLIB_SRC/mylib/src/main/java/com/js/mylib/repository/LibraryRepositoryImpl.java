package com.js.mylib.repository;

import com.js.mylib.dto.LibraryInfoDto;
import com.js.mylib.dto.LibrarySearchConditionDto;
import com.js.mylib.dto.QLibraryInfoDto;
import com.js.mylib.entity.Library;
import com.js.mylib.entity.QMember;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;

import java.util.List;

import static com.js.mylib.entity.QLibrary.*;
import static com.js.mylib.entity.QMember.member;

public class LibraryRepositoryImpl implements LibraryRepositoryCustom{
	private final JPAQueryFactory queryFactory;

	public LibraryRepositoryImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

	@Override
	public LibraryInfoDto findLibraryInfoById(Long libraryId) {
		QMember member = new QMember("reader");

		return queryFactory
				.select(new QLibraryInfoDto(
						library.id.as("libraryId"),
						library.name.as("libraryName"),
						library.readerId,
						member.name.as("readerName"),
						library.description,
						library.startTime,
						library.endTime
				))
				.from(library)
				.join(member).on(library.readerId.eq(member.id))
				.where(library.id.eq(libraryId))
				.fetchOne();
	}

	@Override
	public Page<LibraryInfoDto> searchLibrariesPage(LibrarySearchConditionDto librarySearchCondition, Pageable pageable) {
		QMember member = new QMember("reader");

		List<LibraryInfoDto> content = queryFactory
				.select(new QLibraryInfoDto(
						library.id.as("libraryId"),
						library.name.as("libraryName"),
						library.readerId,
						member.name.as("readerName"),
						library.description,
						library.startTime,
						library.endTime
				))
				.from(library)
				.join(member).on(library.readerId.eq(member.id))
				.where(
						libraryNameLike(librarySearchCondition.getLibraryName()),
						descriptionLike(librarySearchCondition.getDescription())
				)
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetch();

		JPAQuery<Library> countQuery = queryFactory
				.select(library)
				.from(library)
				.where(
						libraryNameLike(librarySearchCondition.getLibraryName()),
						descriptionLike(librarySearchCondition.getDescription())
				);

		return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);

	}

	private BooleanExpression libraryNameLike(String libraryName) {
		return StringUtils.hasText(libraryName) ? library.name.like(libraryName) : null;
	}

	private BooleanExpression descriptionLike(String description) {
		return StringUtils.hasText(description) ? library.description.like(description) : null;
	}
}
