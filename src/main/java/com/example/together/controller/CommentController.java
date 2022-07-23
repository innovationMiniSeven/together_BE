package com.example.together.controller;

import com.example.together.dto.CommentRequestDto;
import com.example.together.dto.CommentResponseDto;
import com.example.together.model.User;
import com.example.together.security.UserDetailsImpl;
import com.example.together.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }


    @PostMapping("/api/comment/{postId}")
    public void registerComment(@PathVariable Long postId, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){

        if(userDetails.getUser() == null){
            throw new IllegalArgumentException("로그인 후 이용해주세요");
        }
        User user = userDetails.getUser();
        commentService.registerComment(postId,commentRequestDto,user);

    }

    @GetMapping("/api/comment/{postId}")
    public List<CommentResponseDto> getComments(@PathVariable Long postId){

        List<CommentResponseDto> commentResponseDtoList = commentService.getComments(postId);

        return commentResponseDtoList;
    }

    @DeleteMapping("/api/comment/{commentId}")
    public void deleteComments(@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        System.out.println(userDetails.getUser().getUsername());
        if(userDetails.getUser() == null){
            throw new IllegalArgumentException("로그인 후 이용해주세요");
        }
        User user = userDetails.getUser();
        System.out.println(user.getNickname());
        commentService.deleteComment(commentId,user);
    }

}
