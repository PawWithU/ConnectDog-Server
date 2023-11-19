package com.pawwithu.connectdog.domain.post.dto.request;

import com.pawwithu.connectdog.domain.dog.entity.DogSize;
import com.pawwithu.connectdog.domain.post.entity.PostStatus;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

public record PostSearchRequest(@RequestParam(value = "postStatus", required = false) PostStatus postStatus,
                                @RequestParam(value = "departureLoc", required = false) String departureLoc,
                                @RequestParam(value = "arrivalLoc", required = false)
                                String arrivalLoc,
                                @DateTimeFormat(pattern = "yyyy-MM-dd")
                                LocalDate startDate,
                                @DateTimeFormat(pattern = "yyyy-MM-dd")
                                LocalDate endDate,
                                @RequestParam(value = "dogSize", required = false) DogSize dogSize,
                                Boolean isKennel,
                                @RequestParam(value = "intermediaryName", required = false)
                                String intermediaryName,
                                @RequestParam(value = "orderCondition", required = false)
                                String orderCondition) {
    
}
