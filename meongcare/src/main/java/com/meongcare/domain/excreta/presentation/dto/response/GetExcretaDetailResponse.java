package com.meongcare.domain.excreta.presentation.dto.response;

import com.meongcare.common.util.LocalDateTimeUtils;
import com.meongcare.domain.excreta.domain.entity.Excreta;
import com.meongcare.domain.excreta.domain.entity.ExcretaType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetExcretaDetailResponse {

    @Schema(description = "대소변 이미지 URL", example = "https://s3.xxx.com")
    private String excretaImageURL;

    @Schema(description = "대소변 날짜 및 시간", example = "2023년 10월 29일")
    private LocalDateTime dateTime;

    @Schema(description = "대소변 타입", example = "FECES")
    private ExcretaType excretaType;

    public static GetExcretaDetailResponse from(Excreta excreta) {
        return new GetExcretaDetailResponse(
                excreta.getImageURL(),
                excreta.getDateTime(),
                excreta.getType()
        );
    }

}
