package com.meongcare.domain.weight.presentation.dto.response;

import com.meongcare.domain.weight.domain.repository.vo.GetMonthWeightVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
public class GetMonthWeightResponse {

    @Schema(description = "지난달 몸무게", example = "35.2")
    private double lastMonthWeight;
    @Schema(description = "이번달 몸무게", example = "36.3")
    private double thisMonthWeight;

    public static GetMonthWeightResponse of(List<GetMonthWeightVO> weightVO, LocalDateTime dateTime) {
        double lastMonthWeight = 0;
        double thisMonthWeight = 0;

        for (GetMonthWeightVO vo : weightVO) {
            if (vo.getMonth() == dateTime.getMonthValue()) {
                thisMonthWeight = vo.getWeight();
            }
            if (vo.getMonth() == dateTime.minusMonths(1).getMonthValue()) {
                lastMonthWeight = vo.getWeight();
            }
        }
        return new GetMonthWeightResponse(lastMonthWeight, thisMonthWeight);
    }
}
