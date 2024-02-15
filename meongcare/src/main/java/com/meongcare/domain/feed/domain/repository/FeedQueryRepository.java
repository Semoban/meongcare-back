package com.meongcare.domain.feed.domain.repository;

import com.meongcare.domain.feed.domain.repository.vo.GetFeedsVO;
import com.meongcare.domain.feed.domain.repository.vo.QGetFeedsVO;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.meongcare.domain.feed.domain.entity.QFeed.feed;

@RequiredArgsConstructor
@Repository
public class FeedQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<GetFeedsVO> getFeedsByDogId(Long dogId) {
        return queryFactory
                .select(new QGetFeedsVO(
                        feed.id,
                        feed.brand,
                        feed.feedName,
                        feed.imageURL
                ))
                .from(feed)
                .where(
                        dogIdEq(dogId),
                        isNotDeleted()
                )
                .fetch();
    }

    public void deleteFeed(Long feedId) {
        queryFactory
                .update(feed)
                .set(feed.deleted, true)
                .where(feedIdEq(feedId))
                .execute();
    }

    public void deleteFeedByDogId(Long dogId) {
        queryFactory
                .update(feed)
                .set(feed.deleted, true)
                .where(dogIdEq(dogId))
                .execute();
    }

    private BooleanExpression isNotDeleted() {
        return feed.deleted.isFalse();
    }

    private BooleanExpression dogIdEq(Long dogId) {
        return feed.dog.id.eq(dogId);
    }

    private BooleanExpression feedIdEq(Long feedId) {
        return feed.id.eq(feedId);
    }
}
