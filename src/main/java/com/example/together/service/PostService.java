package com.example.together.service;

import com.example.together.dto.EditPostRequestDto;
import com.example.together.dto.GetPostResponseDto;
import com.example.together.model.Post;
import com.example.together.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public GetPostResponseDto getPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 포스트입니다."));
        return new GetPostResponseDto(post);
    }


    public void editPost(Long postId, EditPostRequestDto requestDto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 포스트입니다."));
        post.update(requestDto);
    }

    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }
}