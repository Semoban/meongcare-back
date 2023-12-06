package com.meongcare.domain.weight.presentation.dto.response;

import com.meongcare.domain.weight.domain.repository.vo.GetMonthWeightVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class GetMonthWeightResponse {

    @Schema(description = "지난달 몸무게", example = "35.2")
    private double lastMonthWeight;
    @Schema(description = "이번달 몸무게", example = "36.3")
    private double thisMonthWeight;

    public static GetMonthWeightResponse of(List<GetMonthWeightVO> weightVO, LocalDate date) {
        double lastMonthWeight = 0;
        double thisMonthWeight = 0;

        for (GetMonthWeightVO vo : weightVO) {
            if (vo.getMonth() == date.getMonthValue()) {
                thisMonthWeight = vo.getWeight();
            }
            if (vo.getMonth() == date.minusMonths(1).getMonthValue()) {
                lastMonthWeight = vo.getWeight();
            }
        }
        return new GetMonthWeightResponse(lastMonthWeight, thisMonthWeight);
    }
}
