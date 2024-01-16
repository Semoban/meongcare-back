package com.meongcare.domain.excreta.presentation.dto.response;

import com.meongcare.domain.excreta.domain.entity.ExcretaType;
import com.meongcare.domain.excreta.domain.repository.vo.GetExcretaVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetExcretaResponse {

    @Schema(description = "대변 횟수", example = "1")
    private int fecesCount;
    @Schema(description = "소변 횟수", example = "3")
    private int urineCount;
    private List<ExcretaRecord> excretaRecords;

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    static class ExcretaRecord {
        @Schema(description = "대소변 ID", example = "1")
        private Long excretaId;
        @Schema(description = "시간", example = "2023-10-27T08:11:13")
        private LocalDateTime time;
        @Schema(description = "대소변 타입", example = "FECES")
        private ExcretaType excretaType;
    }

    public static GetExcretaResponse from(List<GetExcretaVO> excretaVO) {
        int fecesCount = (int) excretaVO.stream()
                .filter(excreta -> excreta.getExcretaType() == ExcretaType.FECES)
                .count();
        int urineCount = excretaVO.size() - fecesCount;

        List<ExcretaRecord> excretaRecords = excretaVO.stream()
                .map(excreta -> new ExcretaRecord(
                        excreta.getExcretaId(),
                        excreta.getDateTime(),
                        excreta.getExcretaType()
                ))
                .collect(Collectors.toUnmodifiableList());

        return new GetExcretaResponse(fecesCount, urineCount, excretaRecords);
    }
}
