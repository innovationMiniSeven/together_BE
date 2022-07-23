package com.example.together.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
public class EditPostRequestDto {
    private String title;
    private String content;
    @JsonFormat(pattern = "yyyy.MM.dd") //데이터 포맷 변환
    private Date deadline;
    private int numberPeople;
    private int currentNumberPeople;
    private String contactMethod;
    private String imageUrl;
}
