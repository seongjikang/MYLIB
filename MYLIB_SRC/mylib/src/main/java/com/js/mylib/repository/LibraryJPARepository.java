package com.js.mylib.repository;

import com.js.mylib.dto.MemberInfoDto;
import com.js.mylib.entity.Library;
import com.js.mylib.entity.Member;
import com.js.mylib.entity.QLibrary;
import com.js.mylib.entity.QMember;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.stream.Collectors;

import static com.js.mylib.entity.QLibrary.*;
import static com.js.mylib.entity.QMember.*;

@Repository
@RequiredArgsConstructor
public class LibraryJPARepository {
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;
    private final MemberJPARepository memberJPARepository;

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
        int update = em.createQuery("update Member m set m.library = :library where m.id = :id")
                .setParameter("library", null)
                .setParameter("id", memberId)
                .executeUpdate();

        library.setReaderId(library.getMembers().get(0).getId());
    }

    @Modifying
    @Transactional
    public void exitLibrary(Long memberId, Long libraryId) {
        Library findLibrary = queryFactory
                .select(QLibrary.library)
                .from(QLibrary.library)
                .where(QLibrary.library.id.eq(libraryId))
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
}
