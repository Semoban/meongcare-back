package com.meongcare.domain.excreta.presentation.dto.response;

import com.meongcare.domain.excreta.domain.entity.ExcretaType;
import com.meongcare.domain.excreta.domain.repository.vo.GetExcretaVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

import static com.meongcare.common.util.LocalDateTimeUtils.createAMPMTime;

@AllArgsConstructor
@Getter
public class GetExcretaResponse {

    @Schema(description = "대변 횟수", example = "1")
    private int fecesCount;
    @Schema(description = "소변 횟수", example = "3")
    private int urineCount;
    private List<ExcretaRecord> excretaRecords;

    @AllArgsConstructor
    @Getter
    static class ExcretaRecord {
        @Schema(description = "대소변 ID", example = "1")
        private Long excretaId;
        @Schema(description = "시간", example = "오전 08:00")
        private String time;
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
                        createAMPMTime(excreta.getDateTime()),
                        excreta.getExcretaType()
                ))
                .collect(Collectors.toUnmodifiableList());

        return new GetExcretaResponse(fecesCount, urineCount, excretaRecords);
    }
}
