package com.example.together.repository;

import com.example.together.dto.GetPostsResponseDto;

import java.util.List;

public interface PostRepositoryCustom {
    List<GetPostsResponseDto> findAllByCategoryOrderBySort(String sort, String category);
}
