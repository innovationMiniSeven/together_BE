package com.example.together.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentResponseDto {
    private Long id;

    private String content;

    private String nickname;

    private LocalDateTime createdAt;


}
