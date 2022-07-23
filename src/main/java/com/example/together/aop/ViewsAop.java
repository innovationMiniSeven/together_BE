package com.example.together.aop;

import com.example.together.repository.PostRepository;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ViewsAop {
    private final PostRepository postRepository;

    @Autowired
    public ViewsAop(PostRepository postRepository) {
        this.postRepository = postRepository;
    }


}
