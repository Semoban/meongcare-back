package com.meongcare.domain.feed.application;

import com.meongcare.common.error.ErrorCode;
import com.meongcare.common.error.exception.EntityNotFoundException;
import com.meongcare.domain.dog.domain.DogRepository;
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
import com.meongcare.infra.image.ImageDirectory;
import com.meongcare.infra.image.ImageHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
    private final ImageHandler imageHandler;

    private static final Integer DEFAULT_RECOMMEND_INTAKE = 0;

    @Transactional
    public void saveFeed(SaveFeedRequest request, MultipartFile multipartFile) {
        Dog dog = dogRepository.getById(request.getDogId());
        boolean isFirstRegisterFeed = true;

        if (feedQueryRepository.existsByDogId(request.getDogId())) {
            isFirstRegisterFeed = false;
        }
        String imageURL = imageHandler.uploadImage(multipartFile, ImageDirectory.FEED);

        Feed feed = request.toEntity(imageURL, dog, isFirstRegisterFeed);
        feedRepository.save(feed);

        feedRecordRepository.save(FeedRecord.of(feed, dog.getId(), request.getStartDate(), request.getEndDate()));
    }

    public GetFeedResponse getFeed(Long dogId) {
        Optional<Feed> optionalFeed = feedQueryRepository.getActiveFeedByDogId(dogId);

        if (optionalFeed.isEmpty()) {
            return GetFeedResponse.empty();
        }
        Feed feed = optionalFeed.get();
        FeedRecord feedRecord = feedRecordQueryRepository.getFeedRecord(feed.getId());

        return GetFeedResponse.of(
                feedRecord.getStartDate(),
                feedRecord.getEndDate(),
                feedRecord.getId(),
                feed
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
        Feed feed = feedQueryRepository.getActiveFeedByDogId(dogId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.FEED_ENTITY_NOT_FOUND));
        feed.disActivate();

        FeedRecord feedRecord = feedRecordQueryRepository.getFeedRecord(feed.getId());
        if (Objects.isNull(feedRecord.getEndDate())) {
            feedRecord.updateEndDate();
        }

        Feed newFeed = feedRepository.getById(newFeedId);
        newFeed.activate();
        FeedRecord endDateNullFeedRecord = feedRecordQueryRepository.getEndDateNullFeedRecord(newFeedId);
        if (Objects.nonNull(endDateNullFeedRecord)) {
            endDateNullFeedRecord.updateEndDate();
        }
        feedRecordRepository.save(FeedRecord.of(feed, dogId, LocalDate.now(), null));
    }

    @Transactional
    public void editFeed(EditFeedRequest request, MultipartFile multipartFile) {
        Feed feed = feedRepository.getById(request.getFeedId());
        String imageURL = imageHandler.uploadImage(multipartFile, ImageDirectory.FEED);
        feed.updateInfo(request, imageURL);

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
    public void deleteFeed(Long feedId) {
        feedQueryRepository.deleteFeed(feedId);
        feedRecordQueryRepository.deleteFeedRecord(feedId);
    }

    public GetFeedDetailResponse getFeedDetail(Long feedId, Long feedRecordId) {
        GetFeedDetailVO feedDetailVO = feedRecordQueryRepository.getFeedDetailById(feedRecordId);
        return GetFeedDetailResponse.from(feedDetailVO);
    }
}
