package com.meongcare.domain.medicalrecord.domain.entity;

import com.meongcare.domain.dog.domain.entity.Dog;
import com.meongcare.domain.medicalrecord.presentation.dto.request.PutMedicalRecordRequest;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class MedicalRecord {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @JoinColumn(name = "dog_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private Dog dog;

    @NotNull
    private LocalDateTime dateTime;

    private String hospitalName;

    private String doctorName;

    @Column(length = 500)
    private String note;

    private String imageUrl;

    @Builder
    public MedicalRecord(Dog dog, LocalDateTime dateTime, String hospitalName, String doctorName, String note, String imageUrl) {
        this.dog = dog;
        this.dateTime = dateTime;
        this.hospitalName = hospitalName;
        this.doctorName = doctorName;
        this.note = note;
        this.imageUrl = imageUrl;
    }

    public static MedicalRecord of(Dog dog, LocalDateTime dateTime, String hospitalName, String doctorName, String note, String imageUrl){
        return MedicalRecord
                .builder()
                .dog(dog)
                .dateTime(dateTime)
                .hospitalName(hospitalName)
                .doctorName(doctorName)
                .note(note)
                .imageUrl(imageUrl)
                .build();
    }

    public void updateMedicalRecord(PutMedicalRecordRequest putMedicalRecordRequest, String imageUrl){
        this.dateTime = putMedicalRecordRequest.getDateTime();
        this.hospitalName = putMedicalRecordRequest.getHospitalName();
        this.doctorName = putMedicalRecordRequest.getDoctorName();
        this.note = putMedicalRecordRequest.getNote();
        this.imageUrl = imageUrl;
    }
}
