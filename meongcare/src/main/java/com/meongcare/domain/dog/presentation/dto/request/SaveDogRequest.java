package com.meongcare.domain.dog.presentation.dto.request;

import com.meongcare.domain.member.domain.entity.Member;
import com.meongcare.domain.dog.domain.entity.Dog;
import com.meongcare.domain.dog.domain.entity.Sex;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SaveDogRequest {

    @Schema(description = "강아지 이름", example = "김먼지")
    @NotEmpty
    private String name;

    @Schema(description = "강아지 종류", example = "푸들")
    @NotEmpty
    private String type;

    @Schema(description = "강아지 성별 (male, female)", example = "male")
    @NotEmpty
    private String sex;

    @Schema(description = "강아지 생일", example = "2023-08-13")
    private LocalDate birthDate;

    @Schema(description = "중성화 수술 여부", example = "false")
    @NotNull
    private boolean castrate;

    @Schema(description = "강아지 체중", example = "5.10")
    @NotNull
    private double weight;

    @Schema(description = "강아지 등 둘레", example = "10.9")
    @NotNull
    private double backRound;

    @Schema(description = "강아지 목 둘레", example = "3.3")
    @NotNull
    private double neckRound;

    @Schema(description = "강아지 가슴 둘레", example = "10.5")
    @NotNull
    private double chestRound;

    @Schema(description = "S3 이미지 URL")
    private String imageURL;

    public Dog toEntity() {
        return Dog.builder()
                .name(name)
                .type(type)
                .sex(Sex.of(sex))
                .birthDate(birthDate)
                .imageUrl(imageURL)
                .castrate(castrate)
                .backRound(backRound)
                .neckRound(neckRound)
                .chestRound(chestRound)
                .weight(weight)
                .build();

    }
}
