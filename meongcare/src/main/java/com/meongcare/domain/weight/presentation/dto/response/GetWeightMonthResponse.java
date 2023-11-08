package com.meongcare.domain.weight.presentation.dto.response;

import com.meongcare.domain.weight.domain.repository.vo.GetMonthWeightVO;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
public class GetWeightMonthResponse {
    private double lastMonthWeight;
    private double thisMonthWeight;

    public static GetWeightMonthResponse of(List<GetMonthWeightVO> weightVO, LocalDateTime dateTime) {
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
        return new GetWeightMonthResponse(lastMonthWeight, thisMonthWeight);
    }
}
