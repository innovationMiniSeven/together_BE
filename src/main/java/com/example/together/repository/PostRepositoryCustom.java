package com.example.together.repository;

import com.example.together.dto.GetPostsResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;


public interface PostRepositoryCustom {
    Slice<GetPostsResponseDto> findAllByCategoryOrderBySort(String sort, String category, Pageable pageable);
}
