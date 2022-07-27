package com.example.together.repository;

import com.example.together.dto.GetMyPostResponseDto;
import com.example.together.dto.GetPostsResponseDto;
import com.example.together.model.CategoryEnum;
import com.example.together.model.QComment;
import com.example.together.model.QPost;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    private QPost post = QPost.post;
    private QComment comment = QComment.comment;

    @Override
    public Slice<GetPostsResponseDto> findAllByCategoryOrderBySort(String sort, String category, Pageable pageable) {
        List<GetPostsResponseDto> returnPost = queryFactory.select(Projections.fields(
                GetPostsResponseDto.class,
                        post.id,
                        post.title,
                        post.category,
                        post.deadline,
                        post.numberPeople,
                        post.currentNumberPeople,
                        post.contactMethod,
                        post.viewCount,
                        post.user.nickname,
                        post.imageUrl,
                        //댓글수
                        ExpressionUtils.as(
                                JPAExpressions
                                        .select(comment.count())
                                        .from(comment)
                                        .where(comment.post.id.eq(post.id)),"commentCount"
                        )
                        ))
                .from(post)
                .where(categoryContains(category))
                .orderBy(orderByValidDeadline(),getOrderSpecifier(sort))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        return new SliceImpl<>(returnPost, pageable, returnPost.iterator().hasNext());
    }



    @Override
    public List<GetMyPostResponseDto> findAllByUserIdOrderByCreatedAt(Long userId) {
        return queryFactory.select(Projections.fields(
                        GetMyPostResponseDto.class,
                        post.id,
                        post.title,
                        post.category,
                        post.deadline,
                        post.numberPeople,
                        post.currentNumberPeople,
                        post.contactMethod,
                        post.viewCount,
                        post.user.nickname,
                        post.imageUrl,
                        //댓글수
                        ExpressionUtils.as(
                                JPAExpressions
                                        .select(comment.count())
                                        .from(comment)
                                        .where(comment.post.id.eq(post.id)),"commentCount"
                        )
                ))
                .from(post)
                .where(post.user.id.eq(userId))
                .orderBy(post.createdAt.desc())
                .fetch();
    }


    private OrderSpecifier<?> getOrderSpecifier(String sort) {
        switch (sort){
            case "popular":
                return post.viewCount.desc();
            case "almost":
                return post.deadline.asc();
            default:
                return post.createdAt.desc();
        }
    }

    private BooleanExpression categoryContains(String category) {
        return category.equals("ALL") ? null : post.category.eq(CategoryEnum.valueOf(category));
    }

    private OrderSpecifier<Integer> orderByValidDeadline() {
        LocalDateTime date = LocalDateTime.now();
        return new CaseBuilder().when(post.deadline.lt(date)).then(1).otherwise(2).desc();
    }


}
