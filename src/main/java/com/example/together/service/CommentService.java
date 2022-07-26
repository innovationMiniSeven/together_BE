package com.example.together.service;

import com.example.together.dto.CommentRequestDto;
import com.example.together.dto.CommentResponseDto;
import com.example.together.model.Comment;
import com.example.together.model.Post;
import com.example.together.model.User;
import com.example.together.repository.CommentRepository;
import com.example.together.repository.CommentRepositoryImpl;
import com.example.together.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    private final PostRepository postRepository;

    private final CommentRepositoryImpl commentRepositoryImpl;

    @Autowired
    public CommentService(CommentRepository commentRepository, PostRepository postRepository,CommentRepositoryImpl commentRepositoryImpl) {

        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.commentRepositoryImpl = commentRepositoryImpl;
    }

    public void registerComment(Long postId, CommentRequestDto commentRequestDto, User user) {
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new IllegalArgumentException("존재하지 않는 포스트 입니다")
        );

        String content = commentRequestDto.getContent();

        Comment comment = new Comment(post,user,content);

        commentRepository.save(comment);


    }

    public List<CommentResponseDto> getComments(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new IllegalArgumentException("존재하지 않는 포스트 입니다")
        );

        List<CommentResponseDto> commentResponseDtoList = commentRepositoryImpl.findAllByPost(post);


        return commentResponseDtoList;

    }

    public void deleteComment(Long commentId, User user) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()-> new IllegalArgumentException("존재하지 않는 댓글입니다")
        );
        if(!comment.getUser().getId().equals(user.getId())){
           throw  new IllegalArgumentException("접근 권한이 없는 사용자입니다");
        }
        commentRepository.deleteById(commentId);
    }
}
