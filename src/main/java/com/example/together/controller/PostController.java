package com.example.together.controller;

import com.example.together.dto.EditPostRequestDto;
import com.example.together.dto.GetPostResponseDto;
import com.example.together.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/api/post/{postId}")
    public GetPostResponseDto getPost(@PathVariable Long postId){
        return postService.getPost(postId);
    }

    @PutMapping("/api/post/{postId}")
    public void editPost(@PathVariable Long postId, @RequestBody EditPostRequestDto reqeustDto){
        postService.editPost(postId, reqeustDto);
    }

    @DeleteMapping("/api/post/{postId}")
    public void deletePost(@PathVariable Long postId){
        postService.deletePost(postId);
    }
}