package com.example.together.dto;

import com.example.together.model.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CommentResponseDto {
    private Long id;

    private String content;

    private String nickname;

    private LocalDateTime createdAt;


}
