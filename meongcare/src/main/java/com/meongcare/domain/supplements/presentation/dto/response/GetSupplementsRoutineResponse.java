package com.meongcare.domain.supplements.presentation.dto.response;

import com.meongcare.domain.supplements.domain.repository.vo.GetSupplementsRoutineVO;
import com.meongcare.domain.supplements.domain.repository.vo.GetSupplementsRoutineWithoutStatusVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;


@AllArgsConstructor
@Getter
public class GetSupplementsRoutineResponse {

    private List<Routine> routines;

    @AllArgsConstructor
    @Getter
    static class Routine {

        @Schema(description = "루틴 기록 ID", example = "1")
        private Long supplementsRecordId;

        @Schema(description = "영양제 제품명", example = "밥이보약 하루양갱")
        private String name;

        @Schema(description = "섭취 시간 리스트", example = "오후 12:00")
        @NotNull
        private LocalTime intakeTime;

        @Schema(description = "섭취량", example = "3포")
        private String intakeUnitCount;

        @Schema(description = "섭취 여부", example = "true")
        private boolean intakeStatus;
    }

    public static GetSupplementsRoutineResponse createBeforeRecord(List<GetSupplementsRoutineVO> getSupplementsRoutineVOs) {
        List<Routine> routines = getSupplementsRoutineVOs.stream()
                .map(routine -> new GetSupplementsRoutineResponse.Routine(
                        routine.getSupplementsRecordId(),
                        routine.getName(),
                        routine.getIntakeTime(),
                        createIntakeCountUnit(routine.getIntakeCount(), routine.getIntakeUnit()),
                        routine.isIntakeStatus()
                ))
                .collect(Collectors.toUnmodifiableList());

        return new GetSupplementsRoutineResponse(routines);
    }

    public static GetSupplementsRoutineResponse createAfterRecord(List<GetSupplementsRoutineWithoutStatusVO> GetSupplementsRoutineWithoutStatusVO) {
        List<Routine> routines = GetSupplementsRoutineWithoutStatusVO.stream()
                .map(routine -> new GetSupplementsRoutineResponse.Routine(
                        null,
                        routine.getName(),
                        routine.getIntakeTime(),
                        createIntakeCountUnit(routine.getIntakeCount(), routine.getIntakeUnit()),
                        false
                ))
                .collect(Collectors.toUnmodifiableList());

        return new GetSupplementsRoutineResponse(routines);
    }

    private static String createIntakeCountUnit(int intakeCount, String intakeUnit) {
        return intakeCount + intakeUnit;

    }
}
