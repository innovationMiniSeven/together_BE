package com.example.together.repository;

import com.example.together.model.CategoryEnum;
import com.example.together.model.Post;
import com.example.together.model.QPost;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    private QPost post = QPost.post;

    @Override
    public Page<Post> findAllByCategoryOrderBySort(String sort, String category, Pageable pageable) {
        List<Post> returnPost =  queryFactory.selectFrom(post)
                                .where(categoryContains(category))
                                .orderBy(orderByValidDeadline(),getOrderSpecifier(sort))
                                .offset(pageable.getOffset())
                                .limit(pageable.getPageSize())
                                .fetch();

        return new PageImpl<>(returnPost,pageable,returnPost.size());
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
        Date date = new Date();
        System.out.print("Time: ");
        System.out.println(date);
        return new CaseBuilder().when(post.deadline.lt(date)).then(1).otherwise(2).desc();
    }
}
