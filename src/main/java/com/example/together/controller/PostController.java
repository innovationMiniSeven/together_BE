package com.example.together.controller;

import com.example.together.dto.*;
import com.example.together.exception.RestApiException;
import com.example.together.model.User;
import com.example.together.security.UserDetailsImpl;
import com.example.together.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    //게시글 생성
    @PostMapping("/api/post")
    public Optional<RestApiException> createPost(@RequestBody PostRequestDto requestDto,
                                                 @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // 로그인 되어 있는 회원 테이블의 ID
        Long userId = userDetails.getUser().getId();
        System.out.println("userId : " + userId);
        postService.createPost(requestDto, userId);

        // 응답 보내기
        return null;
    }

    @GetMapping("/api/posts")
    public List<PostResponseDto> getPosts() {
        return postService.getPosts();
    }


    @GetMapping("/api/post/{postId}")
    public GetPostRespnseDto getPost(@PathVariable Long postId){
        return postService.getPost(postId);
    }

    @PutMapping("/api/post/{postId}")
    public void editPost(@PathVariable Long postId,
                         @RequestBody EditPostRequestDto requestDto,
                         @AuthenticationPrincipal UserDetailsImpl userDetails){
        User user = userDetails.getUser();
        if(user == null){
            throw new IllegalArgumentException("사용자 정보를 찾을 수 없습니다.");
        }
        postService.editPost(postId, user, requestDto);
    }

    @DeleteMapping("/api/post/{postId}")
    public void deletePost(@PathVariable Long postId,
                           @AuthenticationPrincipal UserDetailsImpl userDetails){
        User user = userDetails.getUser();
        if(user == null){
            throw new IllegalArgumentException("사용자 정보를 찾을 수 없습니다.");
        }
        postService.deletePost(postId, user);
    }
}
