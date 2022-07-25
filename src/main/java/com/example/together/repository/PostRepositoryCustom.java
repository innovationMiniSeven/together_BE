package com.example.together.repository;

import com.example.together.dto.GetPostsResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface PostRepositoryCustom {
    Page<GetPostsResponseDto> findAllByCategoryOrderBySort(String sort, String category, Pageable pageable);
}
