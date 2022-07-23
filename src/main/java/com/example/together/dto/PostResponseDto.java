package com.example.together.dto;

import com.example.together.model.CategoryEnum;

import java.util.Date;

public class PostResponseDto {
    private String title;

    private CategoryEnum category;

    private Date deadline;

    private int numberPeople;

    private int currentNumberPeople;

    private String contactMethod;

    private String viewCount;

    private String nickname;

    private String imageUrl;

    private boolean isDone;
}
