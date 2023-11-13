package com.meongcare.domain.notice.presentation.controller;

import com.meongcare.domain.notice.application.NoticeService;
import com.meongcare.domain.notice.presentation.dto.request.PatchNoticeRequest;
import com.meongcare.domain.notice.presentation.dto.request.SaveNoticeRequest;
import com.meongcare.domain.notice.presentation.dto.response.GetNoticesResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "공지/이벤트 API")
@RestController
@RequestMapping("/notice")
@AllArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    @Operation(description = "공지사항 등록")
    @PostMapping
    public ResponseEntity<Void> saveNotice(@RequestBody @Valid SaveNoticeRequest saveNoticeRequest){
        noticeService.saveNotice(saveNoticeRequest);
        return ResponseEntity.ok().build();
    }

    @Operation(description = "공지사항 조회")
    @GetMapping
    public ResponseEntity<GetNoticesResponse> getNotices(@RequestParam("type") String noticeType){
        GetNoticesResponse getNoticesResponse = noticeService.getNotices(noticeType);
        return ResponseEntity.ok().body(getNoticesResponse);
    }

    @Operation(description = "공지사항 수정")
    @PatchMapping
    public ResponseEntity<Void> updateNotice(@RequestBody @Valid PatchNoticeRequest patchNoticeRequest){
        noticeService.updateNotice(patchNoticeRequest);
        return ResponseEntity.ok().build();
    }

    @Operation(description = "공지사항 삭제")
    @DeleteMapping("/{noticeId}")
    public ResponseEntity<Void> deleteNotice(@PathVariable Long noticeId){
        noticeService.deleteNotice(noticeId);
        return ResponseEntity.ok().build();
    }

}
