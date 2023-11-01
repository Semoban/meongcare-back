package com.meongcare.domain.dog.presentation.dto.request;

import com.meongcare.domain.auth.domain.entity.Member;
import com.meongcare.domain.dog.domain.entity.Dog;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SaveDogRequestDto {

    @NotNull
    private String name;

    @NotNull
    private String type;

    @NotNull
    private String sex;

    private LocalDateTime birthDate;

    @NotNull
    private String profileImage;

    @NotNull
    private boolean castrate;

    @NotNull
    private double weight;

    @NotNull
    private double neckRound;

    @NotNull
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
