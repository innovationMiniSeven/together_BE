package com.example.together.service;

import com.example.together.dto.CommentRequestDto;
import com.example.together.dto.CommentResponseDto;
import com.example.together.model.Comment;
import com.example.together.model.Post;
import com.example.together.model.User;
import com.example.together.repository.CommentRepository;
import com.example.together.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    private final PostRepository postRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, PostRepository postRepository) {

        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    public void registerComment(Long postId, CommentRequestDto commentRequestDto, User user) {
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new IllegalArgumentException("존재하지 않는 Post 입니다")
        );

        String content = commentRequestDto.getContent();
        Comment comment = new Comment(post,user,content);

        commentRepository.save(comment);


    }

    public List<CommentResponseDto> getComments(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new IllegalArgumentException("존재하지 않는 Post 입니다")
        );
        List<Comment> commentList = commentRepository.findAllByPost(post);

        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();



        for(Comment comment:commentList){
            CommentResponseDto commentResponseDto = new CommentResponseDto();
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
        if(!comment.getUser().getId().equals(user.getId())){
           throw  new IllegalArgumentException("댓글을 삭제 할 권한이 없습니다");
        }
        commentRepository.deleteById(commentId);
    }
}
