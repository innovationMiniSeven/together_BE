package com.example.together.dto;

import com.example.together.model.CategoryEnum;
import com.example.together.model.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.Date;

@Getter
@NoArgsConstructor
public class GetPostRespnseDto {
    private Long id;
    private String title;
    private String content;
    private CategoryEnum category;
    private Date deadline;
    private int numberPeople;
    private int currentNumberPeople;
    private String contactMethod;
    private String nickname;
    private String imageUrl;
    private int viewCount;


    public GetPostRespnseDto(Post post) {
        this.id = post.getId();
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
    }
}
