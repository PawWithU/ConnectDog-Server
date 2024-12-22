package com.pawwithu.connectdog.domain.post.dto.request;

import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record PostExtendRequest(@NotNull(message = "이동봉사가 필요한 날짜는 필수 입력 값입니다.")
                                @DateTimeFormat(pattern = "yyyy-MM-dd")
                                LocalDate startDate,
                                @NotNull(message = "이동봉사가 필요한 날짜는 필수 입력 값입니다.")
                                @DateTimeFormat(pattern = "yyyy-MM-dd")
                                LocalDate endDate,
                                String pickUpTime) {

}