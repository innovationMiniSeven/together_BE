package com.example.together.controller;

import com.example.together.model.Post;
import com.example.together.dto.*;
import com.example.together.model.User;
import com.example.together.security.UserDetailsImpl;
import com.example.together.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
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
    public Optional<Post> createPost(@RequestBody PostRequestDto requestDto,
                                     @AuthenticationPrincipal UserDetailsImpl userDetails
   ) {
        // 로그인 되어 있는 회원 테이블의 ID
        Long userId = userDetails.getUser().getId();
        // 응답 보내기
        postService.createPost(requestDto, userId);

        return null;
    }

    @GetMapping("/api/posts")
    public Slice<GetPostsResponseDto> getPosts(@RequestParam String sort, @RequestParam String category , @RequestParam int page, @RequestParam int size) {
        return postService.getPosts(sort, category,page,size);
    }

    @GetMapping("/api/post/{postId}")
    public GetPostResponseDto getPost(@PathVariable Long postId){
        return postService.getPost(postId);
    }

    @PutMapping("/api/post/{postId}")
    public void editPost(@PathVariable Long postId,
                         @RequestBody EditPostRequestDto requestDto,
                         @AuthenticationPrincipal UserDetailsImpl userDetails){
        User user = userDetails.getUser();
        postService.editPost(postId, user, requestDto);
    }

    @DeleteMapping("/api/post/{postId}")
    public void deletePost(@PathVariable Long postId,
                           @AuthenticationPrincipal UserDetailsImpl userDetails){
        User user = userDetails.getUser();
        postService.deletePost(postId, user);
    }

    @GetMapping("/api/mypost")
    public List<GetMyPostResponseDto> getMyPost(@AuthenticationPrincipal UserDetailsImpl userDetails){
        User user = userDetails.getUser();
        return postService.getMyPost(user);
    }

}
