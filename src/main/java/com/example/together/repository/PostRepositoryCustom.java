package com.example.together.repository;

import com.example.together.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostRepositoryCustom {
    Page<Post> findAllByCategoryOrderBySort(String sort, String category, Pageable pageable);
}
