package com.example.together.service;

import com.example.together.dto.PostRequestDto;
import com.example.together.dto.PostResponseDto;
import com.example.together.model.Post;
import com.example.together.model.User;
import com.example.together.repository.PostRepository;
import com.example.together.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
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
        switch (sort){
            case "popular":
                return postRepository.findAllByOrderByViewCountDesc();
            case "almost":
//                return postRepository.findAllByOrderByAlmost();
            default:
                System.out.println("default");
                return postRepository.findAllByOrderByCreatedAtDesc();
        }
    }

    public Post getPost(Long postId) {
        return postRepository.findById(postId) .orElseThrow(()->new IllegalArgumentException("없는 글입니다,"));
    }
}
