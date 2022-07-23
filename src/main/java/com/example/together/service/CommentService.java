package com.example.together.service;

import com.example.together.dto.CommentRequestDto;
import com.example.together.dto.CommentResponseDto;
import com.example.together.model.Comment;
import com.example.together.model.Post;
import com.example.together.model.User;
import com.example.together.repository.CommentRepository;
import com.example.together.repository.PostRepository;
import com.example.together.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, PostRepository postRepository, UserRepository userRepository) {

        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public void registerComment(Long postId, CommentRequestDto commentRequestDto, User user) {
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new IllegalArgumentException("존재하지 않는 Post 입니다")
        );

        String content = commentRequestDto.getComment();
        Comment comment = new Comment(post,user,content);

        commentRepository.save(comment);


    }

    public List<CommentResponseDto> getComments(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new IllegalArgumentException("존재하지 않는 Post 입니다")
        );
        List<Comment> commentList = commentRepository.findAllByPost(post);

        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();

        CommentResponseDto commentResponseDto = new CommentResponseDto();

        for(Comment comment:commentList){
            commentResponseDto.setId(comment.getId());
            commentResponseDto.setContent(comment.getContent());
            commentResponseDto.setNickname(comment.getUser().getNickname());
            commentResponseDto.setCreatedAt(comment.getCreatedAt());
            commentResponseDtoList.add(commentResponseDto);
        }

        return commentResponseDtoList;

    }

    public void deleteComment(Long commentId, User user) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()-> new IllegalArgumentException("존재하지 않는 comment입니다")
        );
        if(comment.getUser() != user){
           throw  new IllegalArgumentException("댓글을 삭제 할 권한이 없습니다");
        }
        commentRepository.deleteById(commentId);
    }
}
