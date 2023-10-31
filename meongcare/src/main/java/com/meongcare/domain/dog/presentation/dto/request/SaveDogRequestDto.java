package com.meongcare.domain.dog.presentation.dto.request;

import com.meongcare.domain.auth.domain.entity.Member;
import com.meongcare.domain.dog.domain.entity.Dog;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SaveDogRequestDto {

    private String name;
    private String type;
    private String sex;
    private LocalDateTime birthDate;
    private String profileImage;
    private boolean castrate;
    private double weight;
    private double neckRound;
    private double chestRound;

    public Dog toEntity(Member member) {
        return Dog.builder()
                .member(member)
                .name(name)
                .type(type)
                .sex(sex)
                .birthDate(birthDate)
                .profileImage(profileImage)
                .castrate(castrate)
                .neckRound(neckRound)
                .chestRound(chestRound)
                .build();

    }
}
