package com.meongcare.domain.excreta.application;

import com.meongcare.domain.dog.domain.DogRepository;
import com.meongcare.domain.dog.domain.entity.Dog;
import com.meongcare.domain.excreta.domain.entity.Excreta;
import com.meongcare.domain.excreta.domain.repository.ExcretaQueryRepository;
import com.meongcare.domain.excreta.domain.repository.ExcretaRepository;
import com.meongcare.domain.excreta.presentation.dto.request.SaveExcretaRequest;
import com.meongcare.infra.image.ImageDirectory;
import com.meongcare.infra.image.ImageHandler;
import org.assertj.core.api.Assertions;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@Transactional
@SpringBootTest
class ExcretaServiceTest {

    @MockBean
    private DogRepository dogRepository;

    @MockBean
    private ExcretaRepository excretaRepository;

    @MockBean
    private ExcretaQueryRepository excretaQueryRepository;

    @MockBean
    private ImageHandler imageHandler;

    @MockBean
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private ExcretaService excretaService;

    private static final EasyRandom easyRandom = new EasyRandom();

    @Test
    void saveExcreta() {
        //given
        Dog dog = easyRandom.nextObject(Dog.class);
        String imageUrl = "https://www.test.com";

        given(dogRepository.getDog(anyLong()))
                .willReturn(dog);
        given(imageHandler.uploadImage(any(), eq(ImageDirectory.EXCRETA)))
                .willReturn(imageUrl);
        MockMultipartFile mockMultipartFile = new MockMultipartFile("test", new byte[0]);
        SaveExcretaRequest request = new SaveExcretaRequest(dog.getId(), "FECES", LocalDateTime.now());

        //when
        excretaService.saveExcreta(request, mockMultipartFile);

        //then
        then(excretaRepository).should(times(1)).save(any(Excreta.class));
    }

    @Test
    void getExcreta() {
    }

    @Test
    void editExcreta() {
    }

    @Test
    void deleteExcreta() {
    }

    @Test
    void getExcretaImage() {
    }

    @Test
    void getExcretaDetail() {
    }

    @Test
    void getExcretaForHome() {
    }
}