package com.meongcare.domain.dog.presentation.dto.response;

import com.meongcare.domain.dog.domain.entity.Dog;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class GetDogsResponse {

    private List<DogInfo> dogs;

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    static class DogInfo{
        @Schema(description = "강아지 고유 번호")
        private Long dogId;
        @Schema(description = "강아지 이름")
        private String name;
        @Schema(description = "강아지 imageUrl")
        private String imageUrl;
    }

    public static GetDogsResponse of(List<Dog> dogs) {
        List<DogInfo> dogInfos = dogs.stream()
                .map(dog -> new DogInfo(
                        dog.getId(),
                        dog.getName(),
                        dog.getImageUrl()
                ))
                .collect(Collectors.toUnmodifiableList());

        return new GetDogsResponse(dogInfos);
    }
}
