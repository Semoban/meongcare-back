package com.meongcare.domain.feed.domain.repository;

import com.meongcare.domain.feed.domain.entity.FeedRecord;
import com.meongcare.domain.feed.presentation.dto.response.vo.GetFeedRecordsPartVO;
import com.meongcare.domain.feed.presentation.dto.response.vo.GetFeedRecordsVO;
import com.meongcare.domain.feed.presentation.dto.response.vo.QGetFeedRecordsPartVO;
import com.meongcare.domain.feed.presentation.dto.response.vo.QGetFeedRecordsVO;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.meongcare.domain.feed.domain.entity.QFeed.feed;
import static com.meongcare.domain.feed.domain.entity.QFeedRecord.feedRecord;

@RequiredArgsConstructor
@Repository
public class FeedRecordQueryRepository {

    private final JPAQueryFactory queryFactory;


    public FeedRecord getFeedRecord(Long dogId) {
        return queryFactory
                .selectFrom(feedRecord)
                .innerJoin(feedRecord.feed, feed)
                .where(dogIdEq(dogId),
                        feedRecord.endDate.isNull()
                )
                .fetchFirst();
    }

    public Optional<Integer> getFeedRecordByDogIdAndDate(Long dogId, LocalDateTime selectedDate) {
        return Optional.ofNullable(queryFactory
                .select(feed.recommendIntake)
                .from(feedRecord)
                .innerJoin(feedRecord.feed, feed)
                .where(
                        dogIdEq(dogId),
                        startDateLoeSelectedDate(selectedDate),
                        endDateGoeSelectedDate(selectedDate).or(feedRecord.endDate.isNull())
                )
                .fetchFirst());
    }

    public List<GetFeedRecordsVO> getFeedRecordsByDogId(Long dogId) {
        return queryFactory
                .select(new QGetFeedRecordsVO(
                        feed.brand,
                        feed.feedName,
                        feedRecord.startDate,
                        feedRecord.endDate
                ))
                .from(feedRecord)
                .innerJoin(feedRecord.feed, feed)
                .where(dogIdEq(dogId), feedRecord.endDate.isNotNull())
                .orderBy(feedRecord.startDate.desc())
                .fetch();
    }

    public List<GetFeedRecordsPartVO> getFeedRecordsPartByDogId(Long dogId) {
        return queryFactory
                .select(new QGetFeedRecordsPartVO(
                        feed.brand,
                        feed.feedName,
                        feedRecord.startDate,
                        feedRecord.endDate,
                        feed.imageURL
                ))
                .from(feedRecord)
                .innerJoin(feedRecord.feed, feed)
                .where(dogIdEq(dogId), feedRecord.endDate.isNotNull())
                .orderBy(feedRecord.startDate.desc())
                .limit(2)
                .fetch();
    }

    private BooleanExpression dogIdEq(Long dogId) {
        return feedRecord.dogId.eq(dogId);
    }

    private BooleanExpression startDateLoeSelectedDate(LocalDateTime selectedDate) {
        return feedRecord.startDate.loe(selectedDate);
    }

    private BooleanExpression endDateGoeSelectedDate(LocalDateTime selectedDate) {
        return feedRecord.endDate.goe(selectedDate);
    }
}
