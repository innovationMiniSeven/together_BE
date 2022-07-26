package com.example.together.repository;

import com.example.together.dto.CommentResponseDto;
import com.example.together.model.Post;
import com.example.together.model.QComment;
import com.example.together.model.QPost;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    private QPost post = QPost.post;

    private QComment comment = QComment.comment;

    @Override
    public List<CommentResponseDto> findAllByPost(Post post) {
        List<CommentResponseDto> commentResponseDtoList = queryFactory.select(Projections.fields(
                CommentResponseDto.class,
                comment.id,
                comment.content,
                comment.user.nickname,
                comment.createdAt
                ))
                .from(comment)
                .where(comment.post.eq(post))
                .fetch();


        return commentResponseDtoList;




    }
}
