package com.meongcare.domain.excreta.presentation.dto.response;

import com.meongcare.domain.excreta.domain.entity.ExcretaType;
import com.meongcare.domain.excreta.domain.repository.vo.GetExcretaVO;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

import static com.meongcare.common.util.LocalDateTimeUtils.createAMPMTime;

@AllArgsConstructor
@Getter
public class GetExcretaResponse {

    private int fecesCount;
    private int urineCount;
    private List<Record> recordList;

    @AllArgsConstructor
    @Getter
    static class Record {
        private Long excretaId;
        private String time;
        private ExcretaType excretaType;
    }

    public static GetExcretaResponse of(List<GetExcretaVO> excretaVO) {
        int fecesCount = (int) excretaVO.stream()
                .filter(excreta -> excreta.getExcretaType() == ExcretaType.FECES)
                .count();
        int urineCount = excretaVO.size() - fecesCount;

        List<Record> records = excretaVO.stream()
                .map(excreta -> new Record(
                        excreta.getExcretaId(),
                        createAMPMTime(excreta.getDateTime()),
                        excreta.getExcretaType()
                ))
                .collect(Collectors.toUnmodifiableList());

        return new GetExcretaResponse(fecesCount, urineCount, records);
    }
}