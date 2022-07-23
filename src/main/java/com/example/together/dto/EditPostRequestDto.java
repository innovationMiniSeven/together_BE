package com.example.together.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
public class EditPostRequestDto {
    private String title;
    private String content;
    private Date deadline;
    private int numberPeople;
    private int currentNumberPeople;
    private String contactMethod;
    private String imageUrl;
}
