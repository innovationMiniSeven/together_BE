package com.example.together.dto;

import com.example.together.model.CategoryEnum;
import com.example.together.model.Post;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetPostResponseDto {
    private String title;
    private String content;
    private CategoryEnum category;
    private LocalDateTime deadline;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createdAt;
    private int numberPeople;
    private int currentNumberPeople;
    private String contactMethod;
    private String nickname;
    private String imageUrl;
    private int viewCount;


    public GetPostResponseDto(Post post) {
        this.title = post.getTitle();
        this.content = post.getContent();
        this.category = post.getCategory();
        this.deadline = post.getDeadline();
        this.numberPeople = post.getNumberPeople();
        this.currentNumberPeople = post.getCurrentNumberPeople();
        this.contactMethod = post.getContactMethod();
        this.nickname = post.getUser().getNickname();
        this.imageUrl = post.getImageUrl();
        this.viewCount = post.getViewCount();
        this.createdAt = post.getCreatedAt();
    }
}

