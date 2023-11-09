package com.pawwithu.connectdog.domain.post.dto;

import com.pawwithu.connectdog.domain.dog.entity.Dog;
import com.pawwithu.connectdog.domain.dog.entity.DogGender;
import com.pawwithu.connectdog.domain.dog.entity.DogSize;
import com.pawwithu.connectdog.domain.intermediary.entity.Intermediary;
import com.pawwithu.connectdog.domain.post.entity.Post;
import com.pawwithu.connectdog.domain.post.entity.PostStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record PostCreateRequest(@NotBlank(message = "출발 지역은 필수 입력 값입니다.")
                                String departureLoc,
                                @NotBlank(message = "도착 지역은 필수 입력 값입니다.")
                                String arrivalLoc,
                                @NotNull(message = "이동봉사가 필요한 날짜는 필수 입력 값입니다.")
                                @DateTimeFormat(pattern = "yyyy-MM-dd")
                                LocalDate startDate,
                                @NotNull(message = "이동봉사가 필요한 날짜는 필수 입력 값입니다.")
                                @DateTimeFormat(pattern = "yyyy-MM-dd")
                                LocalDate endDate,
                                String pickUpTime,
                                @NotNull(message = "켄넬 제공 여부는 필수 입력 값입니다.")
                                Boolean isKennel,
                                @NotBlank(message = "이동봉사에 대한 설명은 필수 입력 값입니다.")
                                String content,
                                @NotBlank(message = "강아지 이름은 필수 입력 값입니다.")
                                String dogName,
                                @NotNull(message = "강아지 사이즈는 필수 입력 값입니다.")
                                DogSize dogSize,
                                @NotNull(message = "강아지 성별은 필수 입력 값입니다.")
                                DogGender dogGender,
                                Float dogWeight,
                                String specifics) {

        public static Post postToEntity(PostCreateRequest request, Dog dog, Intermediary intermediary) {
                return Post.builder()
                        .departureLoc(request.departureLoc)
                        .arrivalLoc(request.arrivalLoc)
                        .startDate(request.startDate)
                        .endDate(request.endDate)
                        .pickUpTime(request.pickUpTime)
                        .isKennel(request.isKennel)
                        .content(request.content)
                        .status(PostStatus.RECRUITING)
                        .dog(dog)
                        .intermediary(intermediary)
                        .build();
        }

        public Dog dogToEntity() {
            return Dog.builder()
                    .name(dogName)
                    .size(dogSize)
                    .gender(dogGender)
                    .weight(dogWeight)
                    .specifics(specifics)
                    .build();
        }
}
