package com.example.together.service;

import com.example.together.dto.*;
import com.example.together.model.Post;
import com.example.together.model.User;
import com.example.together.repository.PostRepository;
import com.example.together.repository.PostRepositoryImpl;
import com.example.together.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostRepositoryImpl postRepositoryImpl;

    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository, PostRepositoryImpl postRepositoryImpl, EntityManager em) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.postRepositoryImpl = postRepositoryImpl;
    }

    public void createPost(PostRequestDto requestDto, Long userId) {
        Date date = new Date();
        //게시글을 작성하고자 하는 user정보 가져오기
        User user = userRepository.findById(userId)
                .orElseThrow(()->new IllegalArgumentException("해당 유저가 없습니다."));
        if(requestDto.getNumberPeople()<=requestDto.getCurrentNumberPeople()){
            throw new IllegalArgumentException("모집 인원보다 현재 인원이 더 많거나 같습니다.");
        }
        if(requestDto.getDeadline().before(date)){
            throw new IllegalArgumentException("deadline이 오늘보다 빠릅니다.");
        }

        //저장할 post 객체 생성
        Post post = new Post(requestDto, user);

        post = postRepository.save(post);
    }

    public Slice<GetPostsResponseDto> getPosts(String sort, String category, int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        return postRepositoryImpl.findAllByCategoryOrderBySort(sort,category,pageable);
    }

    public GetPostResponseDto getPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 포스트입니다."));
        // 조회될 때 마다 veiwCount 1 증가
        post.updateViewCount();
        return new GetPostResponseDto(post);
    }


    public void editPost(Long postId, User user, EditPostRequestDto requestDto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 포스트입니다."));
        if(!user.getId().equals(post.getUser().getId())){
            throw new IllegalArgumentException("접근 권한이 없는 사용자입니다.");
        }
        post.updatePost(requestDto);
    }

    public void deletePost(Long postId, User user) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 포스트입니다."));
        if(!user.getId().equals(post.getUser().getId())){
            throw new IllegalArgumentException("접근 권한이 없는 사용자입니다.");
        }
        postRepository.deleteById(postId);
    }

    public List<GetMyPostResponseDto> getMyPost(User user) {
        Long userId = user.getId();
        return postRepositoryImpl.findAllByUserIdOrderByCreatedAt(userId);
    }
}