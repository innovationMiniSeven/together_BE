package com.example.together.repository;

import com.example.together.model.CategoryEnum;
import com.example.together.model.Post;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.together.model.QPost.post;

@Repository
public class PostRepositorySupport extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public PostRepositorySupport(JPAQueryFactory queryFactory){
        super(Post.class);
        this.queryFactory = queryFactory;
    }

    public List<Post> findByCategoryDefault(CategoryEnum categoryEnum){
        List<Post> returnPostList = new ArrayList<>();

        List<Post> ongoingPostList = queryFactory
                .selectFrom(post)
                .where(post.category.eq(categoryEnum))
                .where(post.deadline.gt(new Date(System.currentTimeMillis())))
                .where(post.currentNumberPeople.lt(post.numberPeople))
                .orderBy(post.createdAt.desc())
                .fetch();

        List<Post> deadlineEndPostList = queryFactory
                .selectFrom(post)
                .where(post.category.eq(categoryEnum))
                .where(post.deadline.lt(new Date(System.currentTimeMillis())))
                .orderBy(post.createdAt.desc())
                .fetch();

        List<Post> peopleEndPostList = queryFactory
                .selectFrom(post)
                .where(post.category.eq(categoryEnum))
                .where(post.currentNumberPeople.eq(post.numberPeople))
                .orderBy(post.createdAt.desc())
                .fetch();


        returnPostList.addAll(ongoingPostList);
        returnPostList.addAll(deadlineEndPostList);
        returnPostList.addAll(peopleEndPostList);

        return returnPostList;



    }

    public List<Post> findByCategoryPopular(CategoryEnum categoryEnum){ //popular
        List<Post> returnPostList = new ArrayList<>();

        List<Post> ongoingPostList = queryFactory
                .selectFrom(post)
                .where(post.category.eq(categoryEnum))
                .where(post.deadline.gt(new Date(System.currentTimeMillis())))
                .where(post.currentNumberPeople.lt(post.numberPeople))
                .orderBy(post.viewCount.desc())
                .fetch();


        List<Post> deadlineEndPostList = queryFactory
                .selectFrom(post)
                .where(post.category.eq(categoryEnum))
                .where(post.deadline.lt(new Date(System.currentTimeMillis())))
                .orderBy(post.viewCount.desc())
                .fetch();

        List<Post> peopleEndPostList = queryFactory
                .selectFrom(post)
                .where(post.category.eq(categoryEnum))
                .where(post.currentNumberPeople.eq(post.numberPeople))
                .orderBy(post.viewCount.desc())
                .fetch();


        returnPostList.addAll(ongoingPostList);
        returnPostList.addAll(deadlineEndPostList);
        returnPostList.addAll(peopleEndPostList);


        return returnPostList;
    }

    public List<Post> findByCategoryAlmost(CategoryEnum categoryEnum){ // almost
        List<Post> returnPostList = new ArrayList<>();

        List<Post> ongoingPostList = queryFactory
                .selectFrom(post)
                .where(post.category.eq(categoryEnum))
                .where(post.deadline.gt(new Date(System.currentTimeMillis())))
                .where(post.currentNumberPeople.lt(post.numberPeople))
                .orderBy(post.deadline.asc())
                .fetch();

        List<Post> deadlineEndPostList = queryFactory
                .selectFrom(post)
                .where(post.category.eq(categoryEnum))
                .where(post.deadline.lt(new Date(System.currentTimeMillis())))
                .orderBy(post.deadline.asc())
                .fetch();

        List<Post> peopleEndPostList = queryFactory
                .selectFrom(post)
                .where(post.category.eq(categoryEnum))
                .where(post.currentNumberPeople.eq(post.numberPeople))
                .orderBy(post.deadline.asc())
                .fetch();



        returnPostList.addAll(ongoingPostList);
        returnPostList.addAll(deadlineEndPostList);
        returnPostList.addAll(peopleEndPostList);



        return returnPostList;
    }

    public List<Post> findAllDefault(){ // all,almost
        List<Post> returnPostList = new ArrayList<>();

        List<Post> ongoingPostList = queryFactory
                .selectFrom(post)
                .where(post.deadline.gt(new Date(System.currentTimeMillis())))
                .where(post.currentNumberPeople.lt(post.numberPeople))
                .orderBy(post.createdAt.desc())
                .fetch();

        List<Post> deadlineEndPostList = queryFactory
                .selectFrom(post)
                .where(post.deadline.lt(new Date(System.currentTimeMillis())))
                .orderBy(post.createdAt.desc())
                .fetch();

        List<Post> peopleEndPostList = queryFactory
                .selectFrom(post)
                .where(post.currentNumberPeople.eq(post.numberPeople))
                .orderBy(post.createdAt.desc())
                .fetch();

        returnPostList.addAll(ongoingPostList);
        returnPostList.addAll(deadlineEndPostList);
        returnPostList.addAll(peopleEndPostList);

        return returnPostList;
    }

    public List<Post> findAllPopular(){
        List<Post> returnPostList = new ArrayList<>();

        List<Post> ongoingPostList = queryFactory
                .selectFrom(post)
                .where(post.deadline.gt(new Date(System.currentTimeMillis())))
                .where(post.currentNumberPeople.lt(post.numberPeople))
                .orderBy(post.viewCount.desc())
                .fetch();

        List<Post> deadlineEndPostList = queryFactory
                .selectFrom(post)
                .where(post.deadline.lt(new Date(System.currentTimeMillis())))
                .orderBy(post.viewCount.desc())
                .fetch();

        List<Post> peopleEndPostList = queryFactory
                .selectFrom(post)
                .where(post.currentNumberPeople.eq(post.numberPeople))
                .orderBy(post.viewCount.desc())
                .fetch();

        returnPostList.addAll(ongoingPostList);
        returnPostList.addAll(deadlineEndPostList);
        returnPostList.addAll(peopleEndPostList);

        return returnPostList;
    }

    public List<Post> findAllAlmost() {
        List<Post> returnPostList = new ArrayList<>();

        List<Post> ongoingPostList = queryFactory
                .selectFrom(post)
                .where(post.deadline.gt(new Date(System.currentTimeMillis())))
                .where(post.currentNumberPeople.lt(post.numberPeople))
                .orderBy(post.deadline.asc())
                .fetch();

        List<Post> deadlineEndPostList = queryFactory
                .selectFrom(post)
                .where(post.deadline.lt(new Date(System.currentTimeMillis())))
                .orderBy(post.viewCount.asc())
                .fetch();

        List<Post> peopleEndPostList = queryFactory
                .selectFrom(post)
                .where(post.currentNumberPeople.eq(post.numberPeople))
                .orderBy(post.viewCount.asc())
                .fetch();

        returnPostList.addAll(ongoingPostList);
        returnPostList.addAll(deadlineEndPostList);
        returnPostList.addAll(peopleEndPostList);

        return returnPostList;
    }





}
