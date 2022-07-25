package com.example.together.repository;

import com.example.together.model.Post;

import java.util.List;

public interface PostRepositoryCustom {
    List<Post> findAllByCategoryOrderBySort(String sort, String category);
}
