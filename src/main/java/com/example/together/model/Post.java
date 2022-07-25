package com.example.together.model;

import com.example.together.TimeStamped;
import com.example.together.dto.EditPostRequestDto;
import com.example.together.dto.PostRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Setter
@Getter // get 함수를 일괄적으로 만들어줍니다.
@NoArgsConstructor // 기본 생성자를 만들어줍니다.
@Entity // DB 테이블 역할을 합니다.
@AllArgsConstructor
public class Post extends TimeStamped {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private CategoryEnum category;

    @Column(nullable = false)
    private LocalDateTime deadline;

    @Column(nullable = false)
    private int numberPeople;

    @Column(nullable = false)
    private int currentNumberPeople;

    @Column(nullable = false)
    private String contactMethod;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private int viewCount;
    private LocalDateTime datetoLocalDateTime(Date date){
        return date.toInstant() // Date -> Instant
                .atZone(ZoneId.systemDefault()) // Instant -> ZonedDateTime
                .toLocalDateTime() // ZonedDateTime -> LocalDateTime
                .plusHours(14).plusMinutes(59).plusSeconds(59); //09시로 저장되는 것 해당 날짜 마지막 시간으로 변경
    }

    public Post(PostRequestDto requestDto, User user) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.category = requestDto.getCategory();
        this.deadline = datetoLocalDateTime(requestDto.getDeadline());
        this.numberPeople = requestDto.getNumberPeople();
        this.currentNumberPeople = requestDto.getCurrentNumberPeople();
        this.contactMethod = requestDto.getContactMethod();
        this.user = user;
        this.imageUrl = requestDto.getImageUrl();
        this.viewCount = 0;
    }

    public void updatePost(EditPostRequestDto requestDto){
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.deadline = requestDto.getDeadline();
        this.numberPeople = requestDto.getNumberPeople();
        this.currentNumberPeople = requestDto.getCurrentNumberPeople();
        this. contactMethod = requestDto.getContactMethod();
        this.imageUrl = requestDto.getImageUrl();
    }

    public void updateViewCount(){
        this.viewCount += 1;
    }
}
