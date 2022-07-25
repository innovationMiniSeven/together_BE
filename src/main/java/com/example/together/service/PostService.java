package com.example.together.service;


import com.example.together.dto.EditPostRequestDto;
import com.example.together.dto.GetPostRespnseDto;
import com.example.together.dto.PostRequestDto;
import com.example.together.dto.PostResponseDto;
import com.example.together.model.CategoryEnum;
import com.example.together.model.Post;
import com.example.together.model.User;
import com.example.together.repository.PostRepository;
import com.example.together.repository.PostRepositorySupport;
import com.example.together.repository.UserRepository;
import com.example.together.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    private final PostRepositorySupport postRepositorySupport;

    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository, PostRepositorySupport postRepositorySupport) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.postRepositorySupport = postRepositorySupport;
    }

    public void createPost(PostRequestDto requestDto, Long userId) {
        //게시글을 작성하고자 하는 user정보 가져오기
        User user = userRepository.findById(userId)
                .orElseThrow(()->new IllegalArgumentException("해당 유저가 없습니다."));

        //저장할 post 객체 생성
        Post post = new Post(requestDto, user);

        post = postRepository.save(post);
    }

    public List<Post> getPosts(String sort, String category) {
        CategoryEnum categoryEnum = CategoryEnum.ETC;
        if(category.equals("PURCHASE")){
           categoryEnum =  CategoryEnum.PURCHASE;
        } else if (category.equals("DELIVERY")) {
            categoryEnum=CategoryEnum.DELIVERY;
        } else if (category.equals("EXHIBITION")) {
            categoryEnum = CategoryEnum.EXHIBITION;
        }
        switch (sort){
            case "popular":
                if(category.equals("all")){
                    return postRepositorySupport.findAllPopular();
                }
                return postRepositorySupport.findByCategoryPopular(categoryEnum);
            case "almost":
                if(category.equals("all")){
                    return postRepositorySupport.findAllAlmost();
                }
                return postRepositorySupport.findByCategoryAlmost(categoryEnum);
            default:
                if(category.equals("all")){
                    return postRepositorySupport.findAllDefault();
                }
                return postRepositorySupport.findByCategoryDefault(categoryEnum);
        }
    }

    public GetPostRespnseDto getPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 포스트입니다."));
        // 조회될 때 마다 veiwCount 1 증가
        post.updateViewCount();
        return new GetPostRespnseDto(post);
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
}