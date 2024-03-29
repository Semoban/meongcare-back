package com.meongcare.domain.excreta.application;

import com.meongcare.common.util.LocalDateTimeUtils;
import com.meongcare.domain.dog.domain.DogRepository;
import com.meongcare.domain.dog.domain.entity.Dog;
import com.meongcare.domain.excreta.domain.entity.Excreta;
import com.meongcare.domain.excreta.domain.repository.vo.GetExcretaVO;
import com.meongcare.domain.excreta.presentation.dto.request.SaveExcretaRequest;
import com.meongcare.domain.excreta.presentation.dto.request.PatchExcretaRequest;
import com.meongcare.domain.excreta.presentation.dto.response.GetExcretaDetailResponse;
import com.meongcare.domain.excreta.presentation.dto.response.GetExcretaForHomeResponse;
import com.meongcare.domain.excreta.presentation.dto.response.GetExcretaResponse;
import com.meongcare.domain.excreta.domain.entity.ExcretaType;
import com.meongcare.domain.excreta.domain.repository.ExcretaQueryRepository;
import com.meongcare.domain.excreta.domain.repository.ExcretaRepository;
import com.meongcare.infra.image.ImageDirectory;
import com.meongcare.infra.image.ImageHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

import static com.meongcare.common.util.LocalDateTimeUtils.createNextMidnight;
import static com.meongcare.common.util.LocalDateTimeUtils.createNowMidnight;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ExcretaService {

    private final ExcretaRepository excretaRepository;
    private final ExcretaQueryRepository excretaQueryRepository;
    private final DogRepository dogRepository;
    private final ImageHandler imageHandler;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public void saveExcreta(SaveExcretaRequest request, MultipartFile multipartFile) {
        Dog dog = dogRepository.getDog(request.getDogId());
        String imageURL = imageHandler.uploadImage(multipartFile, ImageDirectory.EXCRETA);

        excretaRepository.save(
                Excreta.of(request.getExcreta(), request.getDateTime(), imageURL, dog)
        );
    }

    public GetExcretaResponse getExcreta(Long dogId, LocalDateTime dateTime) {
        Dog dog = dogRepository.getDog(dogId);

        List<GetExcretaVO> excretaVO = excretaQueryRepository.getByDogIdAndSelectedDate(
                dog.getId(),
                createNowMidnight(dateTime),
                createNextMidnight(dateTime)
        );
        return GetExcretaResponse.from(excretaVO);
    }

    @Transactional
    public void editExcreta(PatchExcretaRequest request, MultipartFile multipartFile) {
        Excreta excreta = excretaRepository.getById(request.getExcretaId());
        String imageURL = imageHandler.uploadImage(multipartFile, ImageDirectory.EXCRETA);

        excreta.updateRecord(
                ExcretaType.of(request.getExcretaString()),
                request.getDateTime(),
                imageURL
        );

    }

    @Transactional
    public void deleteExcreta(List<Long> excretaIds) {
        List<Excreta> excretas = excretaQueryRepository.getByIds(excretaIds);
        for (Excreta excreta : excretas) {
            eventPublisher.publishEvent(excreta.getImageURL());
        }
        excretaQueryRepository.deleteExcreta(excretaIds);
    }

    public String getExcretaImage(Long excretaId) {
        Excreta excreta = excretaRepository.getById(excretaId);
        return excreta.getImageURL();
    }

    public GetExcretaDetailResponse getExcretaDetail(Long excretaId) {
        Excreta excreta = excretaRepository.getById(excretaId);
        return GetExcretaDetailResponse.from(excreta);
    }

    public GetExcretaForHomeResponse getExcretaForHome(Long dogId, LocalDateTime dateTime) {
        List<GetExcretaVO> excretaVO = excretaQueryRepository.getByDogIdAndSelectedDate(
                dogId,
                LocalDateTimeUtils.createNowMidnight(dateTime),
                LocalDateTimeUtils.createNextMidnight(dateTime)
        );
        return GetExcretaForHomeResponse.from(excretaVO);
    }
}
