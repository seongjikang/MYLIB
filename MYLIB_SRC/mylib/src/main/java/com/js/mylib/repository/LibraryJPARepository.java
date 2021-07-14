package com.js.mylib.repository;

import com.js.mylib.entity.Library;
import com.js.mylib.entity.Member;
import com.js.mylib.entity.QMember;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import static com.js.mylib.entity.QMember.*;

@Repository
@RequiredArgsConstructor
public class LibraryJPARepository {
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

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
}
