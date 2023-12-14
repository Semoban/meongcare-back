package com.meongcare.domain.dog.presentation.dto.response;

import com.meongcare.domain.dog.domain.entity.Dog;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class GetDogResponse {
    @Schema(description = "강아지 고유 번호")
    private Long dogId;
    @Schema(description = "강아지 이름")
    private String name;
    @Schema(description = "강아지 imageUrl")
    private String imageUrl;
    @Schema(description = "강아지 품종")
    private String type;
    @Schema(description = "강아지 성별")
    private String sex;
    @Schema(description = "강아지 중성화 여부")
    private boolean castrate;
    @Schema(description = "강아지 생일")
    private LocalDate birthDate;
    @Schema(description = "강아지 등 둘레")
    private double backRound;
    @Schema(description = "강아지 목 둘레")
    private double neckRound;
    @Schema(description = "강아지 가슴 둘레")
    private double chestRound;
    @Schema(description = "강아지 몸무게")
    private double weight;

    @Builder
    public GetDogResponse(Long dogId, String name, String imageUrl, String type, String sex, boolean castrate,
                          LocalDate birthDate, double backRound, double neckRound, double chestRound, double weight) {
        this.dogId = dogId;
        this.name = name;
        this.imageUrl = imageUrl;
        this.type = type;
        this.sex = sex;
        this.castrate = castrate;
        this.birthDate = birthDate;
        this.backRound = backRound;
        this.neckRound = neckRound;
        this.chestRound = chestRound;
        this.weight = weight;
    }

    public static GetDogResponse from(Dog dog) {
        return GetDogResponse
                .builder()
                .dogId(dog.getId())
                .name(dog.getName())
                .imageUrl(dog.getImageUrl())
                .type(dog.getType())
                .sex(dog.getSex().getSex())
                .castrate(dog.isCastrate())
                .birthDate(dog.getBirthDate())
                .backRound(dog.getBackRound())
                .neckRound(dog.getNeckRound())
                .chestRound(dog.getChestRound())
                .weight(dog.getWeight())
                .build();
    }
}
