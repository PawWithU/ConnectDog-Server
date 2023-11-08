package com.pawwithu.connectdog.domain.dog.entity;

import com.pawwithu.connectdog.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class Dog extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 10, nullable = false)
    private String name; // 강아지 이름
    @Column(nullable = false)
    private DogSize size; // 강아지 사이즈
    @Column(nullable = false)
    private DogGender gender; // 강아지 성별
    private Float weight; // 강아지 몸무게
    @Column(length = 200)
    private String specifics; // 강아지 특이사항

}
