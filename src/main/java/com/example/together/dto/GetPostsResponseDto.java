package com.example.together.dto;

import com.example.together.model.CategoryEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter
@NoArgsConstructor
public class GetPostsResponseDto {
    private String title;
    private CategoryEnum category;
    private LocalDateTime deadline;
    private int numberPeople;
    private int currentNumberPeople;
    private String contactMethod;
    private int viewCount;
    private String nickname;
    private String imageUrl;

    public Long getDeadline() {
        return deadline.atZone(ZoneId.of("Asia/Seoul")).toInstant().toEpochMilli();
    }
}
