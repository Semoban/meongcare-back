package com.meongcare.domain.feed.application;

import com.meongcare.domain.dog.domain.DogRepository;
import com.meongcare.domain.dog.domain.entity.Dog;
import com.meongcare.domain.feed.domain.entity.Feed;
import com.meongcare.domain.feed.domain.entity.FeedRecord;
import com.meongcare.domain.feed.domain.repository.FeedQueryRepository;
import com.meongcare.domain.feed.domain.repository.FeedRecordQueryRepository;
import com.meongcare.domain.feed.domain.repository.FeedRecordRepository;
import com.meongcare.domain.feed.domain.repository.FeedRepository;
import com.meongcare.domain.feed.presentation.dto.request.SaveFeedRequest;
import com.meongcare.domain.feed.presentation.dto.response.GetFeedResponse;
import com.meongcare.domain.feed.presentation.dto.response.GetFeedsPartResponse;
import com.meongcare.domain.feed.presentation.dto.response.GetFeedRecordsResponse;
import com.meongcare.domain.feed.presentation.dto.response.GetFeedsResponse;
import com.meongcare.domain.feed.presentation.dto.response.vo.GetFeedRecordsPartVO;
import com.meongcare.domain.feed.presentation.dto.response.vo.GetFeedRecordsVO;
import com.meongcare.domain.feed.presentation.dto.response.vo.GetFeedsVO;
import com.meongcare.infra.image.ImageDirectory;
import com.meongcare.infra.image.ImageHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

import static com.meongcare.common.util.LocalDateTimeUtils.getBetweenDays;

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

    @Transactional
    public void saveFeed(SaveFeedRequest request, MultipartFile multipartFile) {
        Dog dog = dogRepository.getById(request.getDogId());
        String imageURL = imageHandler.uploadImage(multipartFile, ImageDirectory.FEED);

        Feed feed = request.toEntity(imageURL, dog);
        feedRepository.save(feed);
        //기획에 따라 추가될 수도 있는 부분
//        feedRecordRepository.save(FeedRecord.of(feed, dog.getId()));
    }

    public GetFeedResponse getFeed(Long dogId) {
        FeedRecord feedRecord = feedRecordQueryRepository.getFeedRecord(dogId);

        return GetFeedResponse.of(
                getBetweenDays(feedRecord.getStartDate(), LocalDateTime.now()),
                feedRecord.getFeed()
        );
    }

    public GetFeedsPartResponse getFeedRecordsPart(Long dogId) {
        List<GetFeedRecordsPartVO> feedsPartVO = feedRecordQueryRepository.getFeedRecordsPartByDogId(dogId);
        return GetFeedsPartResponse.from(feedsPartVO);
    }

    public GetFeedRecordsResponse getFeedRecords(Long dogId) {
        List<GetFeedRecordsVO> getFeedRecordsVO = feedRecordQueryRepository.getFeedRecordsByDogId(dogId);
        return GetFeedRecordsResponse.from(getFeedRecordsVO);
    }

    @Transactional
    public void changeFeed(Long dogId, Long newFeedId) {
        FeedRecord feedRecord = feedRecordQueryRepository.getFeedRecord(dogId);
        feedRecord.updateEndDate();
        Feed feed = feedRepository.getById(newFeedId);
        feedRecordRepository.save(FeedRecord.of(feed, dogId));
    }

    public GetFeedsResponse getFeeds(Long dogId) {
        List<GetFeedsVO> feedsVO = feedQueryRepository.getFeedsByDogId(dogId);
        return GetFeedsResponse.from(feedsVO);
    }
}
