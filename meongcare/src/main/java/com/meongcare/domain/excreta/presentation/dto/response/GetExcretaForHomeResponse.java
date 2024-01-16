package com.meongcare.domain.excreta.presentation.dto.response;

import com.meongcare.domain.excreta.domain.entity.ExcretaType;
import com.meongcare.domain.excreta.domain.repository.vo.GetExcretaVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetExcretaForHomeResponse {

    @Schema(description = "대변 횟수", example = "1")
    private int fecesCount;

    @Schema(description = "소변 횟수", example = "2")
    private int urineCount;

    public static GetExcretaForHomeResponse from(List<GetExcretaVO> excretaVO) {
        int fecesCount = getFecesCount(excretaVO);
        int urineCount = excretaVO.size() - fecesCount;

        return new GetExcretaForHomeResponse(fecesCount, urineCount);
    }

    private static int getFecesCount(List<GetExcretaVO> excretaVO) {
        return (int) excretaVO.stream()
                .filter(excreta -> excreta.getExcretaType() == ExcretaType.FECES)
                .count();
    }
}
