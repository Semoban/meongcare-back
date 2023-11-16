package com.meongcare.domain.feed.presentation;

import com.meongcare.domain.feed.application.FeedService;
import com.meongcare.domain.feed.presentation.dto.request.ChangeFeedRequest;
import com.meongcare.domain.feed.presentation.dto.request.SaveFeedRequest;
import com.meongcare.domain.feed.presentation.dto.response.GetFeedResponse;
import com.meongcare.domain.feed.presentation.dto.response.GetFeedsPartResponse;
import com.meongcare.domain.feed.presentation.dto.response.GetFeedRecordsResponse;
import com.meongcare.domain.feed.presentation.dto.response.GetFeedsResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@Tag(name = "사료 API")
@RequiredArgsConstructor
@RequestMapping("/feed")
@RestController
public class FeedController {

    private final FeedService feedService;

    @Operation(description = "사료 저장")
    @Parameter(name = "AccessToken", in = ParameterIn.HEADER, required = true)
    @PostMapping
    public ResponseEntity<Void> saveFeed(
            @RequestPart(value = "dto") @Valid SaveFeedRequest request,
            @RequestPart(value = "file") MultipartFile multipartFile
    ) {
        feedService.saveFeed(request, multipartFile);
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
    public ResponseEntity<GetFeedsPartResponse> getFeedRecordsPart(@PathVariable Long dogId) {
        return ResponseEntity.ok(feedService.getFeedRecordsPart(dogId));
    }

    @Operation(description = "이전에 먹은 사료 조회")
    @Parameter(name = "AccessToken", in = ParameterIn.HEADER, required = true)
    @GetMapping("/list/before/{dogId}")
    public ResponseEntity<GetFeedRecordsResponse> getFeedRecords(@PathVariable Long dogId) {
        return ResponseEntity.ok(feedService.getFeedRecords(dogId));
    }

    @Operation(description = "사료 변경")
    @Parameter(name = "AccessToken", in = ParameterIn.HEADER, required = true)
    @PatchMapping
    public ResponseEntity<Void> changeFeed(@RequestBody @Valid ChangeFeedRequest request) {
        feedService.changeFeed(request.getDogId(), request.getNewFeedId());
        return ResponseEntity.ok().build();
    }

    @Operation(description = "사료 변경을 위한 사료 리스트 조회")
    @Parameter(name = "AccessToken", in = ParameterIn.HEADER, required = true)
    @GetMapping("/list/{dogId}")
    public ResponseEntity<GetFeedsResponse> getFeeds(@PathVariable Long dogId) {
        return ResponseEntity.ok(feedService.getFeeds(dogId));
    }
}
