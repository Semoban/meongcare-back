package com.meongcare.domain.dog.domain.entity;

import com.meongcare.common.BaseEntity;
import com.meongcare.domain.member.domain.entity.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberDog extends BaseEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private Member member;

    @JoinColumn(name = "dog_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private Dog dog;

    public MemberDog(Member member, Dog dog) {
        this.member = member;
        this.dog = dog;
    }

    public static MemberDog of (Member member, Dog dog) {
        return new MemberDog(member, dog);
    }
}
