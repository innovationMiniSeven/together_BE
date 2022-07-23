package com.example.together.controller;

import com.example.together.dto.PostRequestDto;
import com.example.together.dto.PostResponseDto;
import com.example.together.exception.RestApiException;
import com.example.together.model.Post;
import com.example.together.security.UserDetailsImpl;
import com.example.together.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

        postService.createPost(requestDto, userId);

        // 응답 보내기
        return null;
    }

    @GetMapping("/api/posts")
    public List<PostResponseDto> getPosts() {

        return postService.getPosts();

    }
}
