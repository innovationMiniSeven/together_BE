package com.example.together.repository;

import com.example.together.dto.CommentResponseDto;
import com.example.together.model.Post;

import java.util.List;

public interface CommentRepositoryCustom {

    List<CommentResponseDto> findAllByPost(Post post);

}
