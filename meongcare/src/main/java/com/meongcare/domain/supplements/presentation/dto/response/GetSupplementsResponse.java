package com.meongcare.domain.supplements.presentation.dto.response;

import com.meongcare.domain.supplements.domain.entity.Supplements;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class GetSupplementsResponse {

    private List<SupplementsInfo> supplementsInfos;

    @Builder(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    static class SupplementsInfo {
        @Schema(description = "영양제 ID", example = "1")
        private Long supplementsId;

        @Schema(description = "영양제 제품명", example = "밥이보약 하루양갱")
        private String name;

        @Schema(description = "영양제 알림 여부", example = "true")
        private boolean pushAgreement;
    }

    public static GetSupplementsResponse of(List<Supplements> supplements) {
        List<SupplementsInfo> supplementsInfos = supplements.stream()
                .map(supplementsInfo -> SupplementsInfo.builder()
                        .supplementsId(supplementsInfo.getId())
                        .name(supplementsInfo.getName())
                        .pushAgreement(supplementsInfo.isPushAgreement())
                        .build()
                )
                .collect(Collectors.toUnmodifiableList());

        return new GetSupplementsResponse(supplementsInfos);
    }
}
