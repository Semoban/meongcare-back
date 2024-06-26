package com.meongcare.domain.feed.presentation;

import com.meongcare.domain.feed.application.FeedService;
import com.meongcare.domain.feed.presentation.dto.request.ChangeFeedRequest;
import com.meongcare.domain.feed.presentation.dto.request.EditFeedRequest;
import com.meongcare.domain.feed.presentation.dto.request.SaveFeedRequest;
import com.meongcare.domain.feed.presentation.dto.response.GetFeedDetailResponse;
import com.meongcare.domain.feed.presentation.dto.response.GetFeedRecommendIntakeForHomeResponse;
import com.meongcare.domain.feed.presentation.dto.response.GetFeedResponse;
import com.meongcare.domain.feed.presentation.dto.response.GetFeedsPartResponse;
import com.meongcare.domain.feed.presentation.dto.response.GetFeedRecordsResponse;
import com.meongcare.domain.feed.presentation.dto.response.GetFeedsResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDate;

import static com.meongcare.common.DateTimePattern.DATE_PATTERN;

@Tag(name = "사료 API")
@RequiredArgsConstructor
@RequestMapping("/feed")
@RestController
public class FeedController {

    private final FeedService feedService;

    @Operation(description = "사료 저장")
    @Parameter(name = "AccessToken", in = ParameterIn.HEADER, required = true)
    @PostMapping
    public ResponseEntity<Void> saveFeed(@RequestBody @Valid SaveFeedRequest request) {
        feedService.saveFeed(request);
        return ResponseEntity.ok().build();
    }

    @Operation(description = "사료 조회")
    @Parameter(name = "AccessToken", in = ParameterIn.HEADER, required = true)
    @GetMapping("/{dogId}")
    public ResponseEntity<GetFeedResponse> getFeed(@PathVariable Long dogId) {
        return ResponseEntity.ok(feedService.getFeed(dogId));
    }

    @Operation(description = "이전에 먹은 사료 일부 조회")
    @Parameter(name = "AccessToken", in = ParameterIn.HEADER, required = true)
    @GetMapping("/part/{dogId}")
    public ResponseEntity<GetFeedsPartResponse> getFeedRecordsPart(
            @PathVariable Long dogId, @RequestParam Long feedRecordId
    ) {
        return ResponseEntity.ok(feedService.getFeedRecordsPart(dogId, feedRecordId));
    }

    @Operation(description = "이전에 먹은 사료 조회")
    @Parameter(name = "AccessToken", in = ParameterIn.HEADER, required = true)
    @GetMapping("/list/before/{dogId}")
    public ResponseEntity<GetFeedRecordsResponse> getFeedRecords(
            @PathVariable Long dogId, @RequestParam Long feedRecordId
    ) {
        return ResponseEntity.ok(feedService.getFeedRecords(dogId, feedRecordId));
    }

    @Operation(description = "먹고 있는 사료 변경")
    @Parameter(name = "AccessToken", in = ParameterIn.HEADER, required = true)
    @PatchMapping
    public ResponseEntity<Void> changeFeed(@RequestBody @Valid ChangeFeedRequest request) {
        feedService.changeFeed(request.getDogId(), request.getNewFeedId());
        return ResponseEntity.ok().build();
    }

    @Operation(description = "사료 정보 수정")
    @Parameter(name = "AccessToken", in = ParameterIn.HEADER, required = true)
    @PutMapping
    public ResponseEntity<Void> editFeed(@RequestBody @Valid EditFeedRequest request) {
        feedService.editFeed(request);
        return ResponseEntity.ok().build();
    }

    @Operation(description = "사료 변경을 위한 사료 리스트 조회")
    @Parameter(name = "AccessToken", in = ParameterIn.HEADER, required = true)
    @GetMapping("/list/{dogId}")
    public ResponseEntity<GetFeedsResponse> getFeeds(@PathVariable Long dogId) {
        return ResponseEntity.ok(feedService.getFeeds(dogId));
    }

    @Operation(description = "반려견 메인 홈 사료 권장 섭취량 조회")
    @Parameter(name = "AccessToken", in = ParameterIn.HEADER, required = true)
    @GetMapping("/home/{dogId}")
    public ResponseEntity<GetFeedRecommendIntakeForHomeResponse> getFeedRecommendIntakeForHome(
            @PathVariable Long dogId,
            @RequestParam @DateTimeFormat(pattern = DATE_PATTERN) LocalDate date
    ) {
        return ResponseEntity.ok(feedService.getFeedRecommendIntakeForHome(dogId, date));
    }

    @Operation(description = "사료 기록 삭제")
    @Parameter(name = "AccessToken", in = ParameterIn.HEADER, required = true)
    @DeleteMapping("/{feedRecordId}")
    public ResponseEntity<Void> deleteFeedRecord(@PathVariable Long feedRecordId) {
        feedService.deleteFeedRecord(feedRecordId);
        return ResponseEntity.ok().build();
    }

    @Operation(description = "사료 상세 정보 조회")
    @Parameter(name = "AccessToken", in = ParameterIn.HEADER, required = true)
    @GetMapping("/detail/{feedId}")
    public ResponseEntity<GetFeedDetailResponse> getFeedDetail(@PathVariable Long feedId, @RequestParam Long feedRecordId) {
        return ResponseEntity.ok(feedService.getFeedDetail(feedId, feedRecordId));
    }

    @Operation(description = "사료 중단")
    @Parameter(name = "AccessToken", in = ParameterIn.HEADER, required = true)
    @PatchMapping("/stop/{feedRecordId}")
    public ResponseEntity<Void> stopFeed(@PathVariable Long feedRecordId) {
        feedService.stopFeed(feedRecordId);
        return ResponseEntity.ok().build();
    }
}
