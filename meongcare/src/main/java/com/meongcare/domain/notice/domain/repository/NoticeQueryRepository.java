package com.meongcare.domain.notice.domain.repository;

import com.meongcare.domain.notice.domain.entity.NoticeType;
import com.meongcare.domain.notice.domain.repository.vo.GetNoticesVO;
import com.meongcare.domain.notice.domain.repository.vo.QGetNoticesVO;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.meongcare.domain.notice.domain.entity.QNotice.*;


@RequiredArgsConstructor
@Repository
public class NoticeQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<GetNoticesVO> findByNoticeType(NoticeType noticeType) {
        return queryFactory
                .select(new QGetNoticesVO(
                        notice.id,
                        notice.title,
                        notice.text,
                        notice.updatedAt
                ))
                .from(notice)
                .where(noticeTypeEq(noticeType))
                .fetch();
    }

    private BooleanExpression noticeTypeEq(NoticeType noticeType) {
        return notice.noticeType.eq(noticeType);
    }
}
