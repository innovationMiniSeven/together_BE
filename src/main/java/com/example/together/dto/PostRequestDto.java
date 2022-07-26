package com.example.together.dto;

import com.example.together.model.CategoryEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.util.Date;

@Getter
public class PostRequestDto {
    private String title;

    private String content;

    private CategoryEnum category;

    @JsonFormat(pattern = "yyyy-MM-dd") //데이터 포맷 변환
    private Date deadline;

    private int numberPeople;

    private int currentNumberPeople;

    private String contactMethod;

    private String imageUrl;

}
