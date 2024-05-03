package com.meongcare.domain.feed.domain.repository;

import com.meongcare.domain.feed.domain.entity.FeedRecord;
import com.meongcare.domain.feed.domain.repository.vo.GetFeedDetailVO;
import com.meongcare.domain.feed.domain.repository.vo.GetFeedRecordsPartVO;
import com.meongcare.domain.feed.domain.repository.vo.GetFeedRecordsVO;
import com.meongcare.domain.feed.domain.repository.vo.QGetFeedDetailVO;
import com.meongcare.domain.feed.domain.repository.vo.QGetFeedRecordsPartVO;
import com.meongcare.domain.feed.domain.repository.vo.QGetFeedRecordsVO;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.meongcare.domain.feed.domain.entity.QFeed.feed;
import static com.meongcare.domain.feed.domain.entity.QFeedRecord.feedRecord;

@RequiredArgsConstructor
@Repository
public class FeedRecordQueryRepository {

    private final JPAQueryFactory queryFactory;


    public Optional<FeedRecord> getFeedRecord(Long feedId) {
        return Optional.ofNullable(queryFactory
                .selectFrom(feedRecord)
                .where(
                        feedIdEq(feedId),
                        feedRecordIsNotDeleted()
                )
                .orderBy(feedRecord.id.desc())
                .fetchFirst());
    }

    public Optional<Integer> getFeedRecordByDogIdAndDate(Long dogId, LocalDate selectedDate) {
        return Optional.ofNullable(queryFactory
                .select(feed.recommendIntake)
                .from(feedRecord)
                .innerJoin(feedRecord.feed, feed)
                .where(
                        dogIdEq(dogId),
                        startDateLoeSelectedDate(selectedDate),
                        endDateGoeSelectedDate(selectedDate).or(feedRecord.endDate.isNull()),
                        feedRecordIsNotDeleted()
                )
                .fetchFirst());
    }

    public List<GetFeedRecordsVO> getFeedRecordsByDogId(Long dogId, Long feedRecordId) {
        return queryFactory
                .select(new QGetFeedRecordsVO(
                        feed.brand,
                        feed.feedName,
                        feedRecord.startDate,
                        feedRecord.endDate,
                        feedRecord.id,
                        feed.imageURL,
                        feed.id
                ))
                .from(feedRecord)
                .innerJoin(feedRecord.feed, feed)
                .where(
                        dogIdEq(dogId),
                        feedRecordIdNotEq(feedRecordId),
                        feedRecordIsNotDeleted()
                )
                .orderBy(feedRecord.startDate.desc())
                .fetch();
    }

    public List<GetFeedRecordsPartVO> getFeedRecordsPartByDogId(Long dogId, Long feedRecordId) {
        return queryFactory
                .select(new QGetFeedRecordsPartVO(
                        feed.brand,
                        feed.feedName,
                        feedRecord.startDate,
                        feedRecord.endDate,
                        feed.imageURL,
                        feedRecord.id
                ))
                .from(feedRecord)
                .innerJoin(feedRecord.feed, feed)
                .where(
                        dogIdEq(dogId),
                        feedRecordIdNotEq(feedRecordId),
                        feedRecordIsNotDeleted()
                )
                .orderBy(feedRecord.startDate.desc())
                .limit(2)
                .fetch();
    }

    public FeedRecord getEndDateNullFeedRecord(Long feedId) {
        return queryFactory
                .selectFrom(feedRecord)
                .where(
                        feedIdEq(feedId),
                        feedRecord.endDate.isNull(),
                        feedRecordIsNotDeleted())
                .fetchFirst();
    }

    public void deleteFeedRecord(Long feedRecordId) {
        queryFactory
                .update(feedRecord)
                .set(feedRecord.deleted, true)
                .where(feedRecordIdEq(feedRecordId))
                .execute();
    }

    public GetFeedDetailVO getFeedDetailById(Long feedRecordId) {
        return queryFactory
                .select(new QGetFeedDetailVO(
                        feed.brand,
                        feed.feedName,
                        feed.protein,
                        feed.fat,
                        feed.crudeAsh,
                        feed.moisture,
                        feed.etc,
                        feed.kcal,
                        feed.recommendIntake,
                        feed.imageURL,
                        feedRecord.startDate,
                        feedRecord.endDate
                ))
                .from(feedRecord)
                .innerJoin(feedRecord.feed, feed)
                .where(
                        feedRecordIdEq(feedRecordId),
                        feedRecordIsNotDeleted()
                )
                .fetchFirst();
    }

    public boolean existActiveFeedRecord(Long dogId) {
        return Optional.ofNullable(queryFactory
                .selectFrom(feedRecord)
                .where(
                        dogIdEq(dogId),
                        feedRecordIsNotDeleted()
                )
                .fetchFirst()
        ).isPresent();

    }

    public Optional<FeedRecord> getActiveFeedRecordByDogId(Long dogId) {
        return Optional.ofNullable(queryFactory
                .selectFrom(feedRecord)
                .innerJoin(feedRecord.feed, feed)
                .where(
                        dogIdEq(dogId),
                        feedRecord.isActive.isTrue(),
                        feedRecordIsNotDeleted()
                )
                .fetchFirst()
        );
    }

    public FeedRecord getActiveFeedRecordByFeedRecordId(Long feedRecordId) {
        return queryFactory.selectFrom(feedRecord)
                .where(feedRecordIdEq(feedRecordId))
                .fetchFirst();
    }

    public void deleteFeedRecordByDogId(Long dogId) {
        queryFactory
                .update(feedRecord)
                .set(feedRecord.deleted, true)
                .where(dogIdEq(dogId))
                .execute();
    }

    public int getFeedRecordCountByFeedId(Long feedRecordId) {
        return queryFactory
                .selectFrom(feedRecord)
                .where(feedRecordIdEq(feedRecordId))
                .fetch()
                .size();
    }

    private BooleanExpression feedRecordIdEq(Long feedRecordId) {
        return feedRecord.id.eq(feedRecordId);
    }

    private BooleanExpression feedRecordIsNotDeleted() {
        return feedRecord.deleted.isFalse();
    }

    private BooleanExpression feedRecordIdNotEq(Long feedRecordId) {
        return feedRecord.id.ne(feedRecordId);
    }

    private static BooleanExpression feedIdEq(Long feedId) {
        return feedRecord.feed.id.eq(feedId);
    }

    private BooleanExpression dogIdEq(Long dogId) {
        return feedRecord.dogId.eq(dogId);
    }

    private BooleanExpression startDateLoeSelectedDate(LocalDate selectedDate) {
        return feedRecord.startDate.loe(selectedDate);
    }

    private BooleanExpression endDateGoeSelectedDate(LocalDate selectedDate) {
        return feedRecord.endDate.goe(selectedDate);
    }
}
