package com.example.together.repository;

import com.example.together.dto.GetMyPostResponseDto;
import com.example.together.dto.GetPostsResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import java.util.List;


public interface PostRepositoryCustom {
    Slice<GetPostsResponseDto> findAllByCategoryOrderBySort(String sort, String category, Pageable pageable);
    List<GetMyPostResponseDto> findAllByUserIdOrderByCreatedAt(Long userId);
}
