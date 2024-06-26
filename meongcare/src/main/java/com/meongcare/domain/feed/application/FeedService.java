package com.meongcare.domain.feed.application;

import com.meongcare.domain.dog.domain.repository.DogRepository;
import com.meongcare.domain.dog.domain.entity.Dog;
import com.meongcare.domain.feed.domain.entity.Feed;
import com.meongcare.domain.feed.domain.entity.FeedRecord;
import com.meongcare.domain.feed.domain.repository.FeedQueryRepository;
import com.meongcare.domain.feed.domain.repository.FeedRecordQueryRepository;
import com.meongcare.domain.feed.domain.repository.FeedRecordRepository;
import com.meongcare.domain.feed.domain.repository.FeedRepository;
import com.meongcare.domain.feed.domain.repository.vo.GetFeedDetailVO;
import com.meongcare.domain.feed.presentation.dto.request.EditFeedRequest;
import com.meongcare.domain.feed.presentation.dto.request.SaveFeedRequest;
import com.meongcare.domain.feed.presentation.dto.response.GetFeedDetailResponse;
import com.meongcare.domain.feed.presentation.dto.response.GetFeedRecommendIntakeForHomeResponse;
import com.meongcare.domain.feed.presentation.dto.response.GetFeedResponse;
import com.meongcare.domain.feed.presentation.dto.response.GetFeedsPartResponse;
import com.meongcare.domain.feed.presentation.dto.response.GetFeedRecordsResponse;
import com.meongcare.domain.feed.presentation.dto.response.GetFeedsResponse;
import com.meongcare.domain.feed.domain.repository.vo.GetFeedRecordsPartVO;
import com.meongcare.domain.feed.domain.repository.vo.GetFeedRecordsVO;
import com.meongcare.domain.feed.domain.repository.vo.GetFeedsVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class FeedService {

    private final DogRepository dogRepository;
    private final FeedRepository feedRepository;
    private final FeedQueryRepository feedQueryRepository;
    private final FeedRecordRepository feedRecordRepository;
    private final FeedRecordQueryRepository feedRecordQueryRepository;

    private static final Integer DEFAULT_RECOMMEND_INTAKE = 0;
    private static final Integer REST_FEED_RECORD = 1;

    @Transactional
    public void saveFeed(SaveFeedRequest request) {
        Dog dog = dogRepository.getDog(request.getDogId());

        boolean isFirstRegisterFeed = true;
        if (feedRecordQueryRepository.existActiveFeedRecord(dog.getId())) {
            isFirstRegisterFeed = false;
        }

        Feed feed = request.toEntity(dog);
        feedRepository.save(feed);

        feedRecordRepository.save(
                FeedRecord.of(feed, dog.getId(), request.getStartDate(), request.getEndDate(), isFirstRegisterFeed)
        );
    }

    public GetFeedResponse getFeed(Long dogId) {
        Optional<FeedRecord> optionalFeedRecord = feedRecordQueryRepository.getActiveFeedRecordByDogId(dogId);
        if (optionalFeedRecord.isEmpty()) {
            return GetFeedResponse.empty();
        }
        FeedRecord feedRecord = optionalFeedRecord.get();

        return GetFeedResponse.of(
                feedRecord.getStartDate(),
                feedRecord.getEndDate(),
                feedRecord.getId(),
                feedRecord.getFeed()
        );
    }

    public GetFeedsPartResponse getFeedRecordsPart(Long dogId, Long feedRecordId) {
        List<GetFeedRecordsPartVO> feedsPartVO = feedRecordQueryRepository.getFeedRecordsPartByDogId(dogId, feedRecordId);
        return GetFeedsPartResponse.from(feedsPartVO);
    }

    public GetFeedRecordsResponse getFeedRecords(Long dogId, Long feedRecordId) {
        List<GetFeedRecordsVO> getFeedRecordsVO = feedRecordQueryRepository.getFeedRecordsByDogId(dogId, feedRecordId);
        return GetFeedRecordsResponse.from(getFeedRecordsVO);
    }

    @Transactional
    public void changeFeed(Long dogId, Long newFeedId) {
        feedRecordQueryRepository.getActiveFeedRecordByDogId(dogId)
                .ifPresent(feedRecord -> {
                    feedRecord.disActivate();
                    if (Objects.isNull(feedRecord.getEndDate())) {
                        feedRecord.updateEndDate();
                    }
                });

        feedRecordQueryRepository.getFeedRecord(newFeedId)
                .filter(feedRecord -> {
                    if (Objects.isNull(feedRecord.getEndDate())) {
                        return true;
                    }
                    return feedRecord.getEndDate().isAfter(LocalDate.now());
                })
                .ifPresent(FeedRecord::updateEndDate);

        Feed feed = feedRepository.getById(newFeedId);
        feedRecordRepository.save(FeedRecord.of(feed, dogId, LocalDate.now(), null, true));
    }

    @Transactional
    public void editFeed(EditFeedRequest request) {
        Feed feed = feedRepository.getById(request.getFeedId());
        feed.updateInfo(request);

        FeedRecord feedRecord = feedRecordRepository.getById(request.getFeedRecordId());
        feedRecord.updateDate(request.getStartDate(), request.getEndDate());
    }

    public GetFeedsResponse getFeeds(Long dogId) {
        List<GetFeedsVO> feedsVO = feedQueryRepository.getFeedsByDogId(dogId);
        return GetFeedsResponse.from(feedsVO);
    }

    public GetFeedRecommendIntakeForHomeResponse getFeedRecommendIntakeForHome(Long dogId, LocalDate date) {
        Integer recommendIntake = feedRecordQueryRepository.getFeedRecordByDogIdAndDate(dogId, date)
                .orElse(DEFAULT_RECOMMEND_INTAKE);
        return GetFeedRecommendIntakeForHomeResponse.from(recommendIntake);
    }

    @Transactional
    public void deleteFeedRecord(Long feedRecordId) {
        FeedRecord feedRecord = feedRecordRepository.getById(feedRecordId);
        Feed feed = feedRecord.getFeed();
        int feedRecordCount = feedRecordQueryRepository.getFeedRecordCountByFeedId(feed.getId());
        if (feedRecordCount == REST_FEED_RECORD) {
            feedQueryRepository.deleteFeed(feed.getId());
        }
        feedRecordQueryRepository.deleteFeedRecord(feedRecordId);
    }

    public GetFeedDetailResponse getFeedDetail(Long feedId, Long feedRecordId) {
        GetFeedDetailVO feedDetailVO = feedRecordQueryRepository.getFeedDetailById(feedRecordId);
        return GetFeedDetailResponse.from(feedDetailVO);
    }

    @Transactional
    public void stopFeed(Long feedRecordId) {
        FeedRecord activeFeedRecord = feedRecordQueryRepository.getActiveFeedRecordByFeedRecordId(feedRecordId);
        activeFeedRecord.disActivate();
        activeFeedRecord.updateEndDate();
    }
}
