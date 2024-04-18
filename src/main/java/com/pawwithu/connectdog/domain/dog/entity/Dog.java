package com.pawwithu.connectdog.domain.dog.entity;

import com.pawwithu.connectdog.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class Dog extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 8, nullable = false)
    private String name; // 강아지 이름
    @Column(nullable = false)
    private DogSize size; // 강아지 사이즈
    @Column(length = 200)
    private String specifics; // 강아지 특이사항

    @Builder
    public Dog(String name, DogSize size, String specifics) {
        this.name = name;
        this.size = size;
        this.specifics = specifics;
    }
}
