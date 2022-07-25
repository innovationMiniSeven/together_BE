package com.example.together;


import com.example.together.model.CategoryEnum;
import com.example.together.model.Post;
import com.example.together.model.User;
import com.example.together.repository.PostRepository;
import com.example.together.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class QuerydslTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostRepositorySupport postRepositorySupport;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void test1() {
        String username ="aaaa1111";
        String nickname = "aaaa2222";
        String password = "1234";
        User user = new User(nickname,username,passwordEncoder.encode(password));

        userRepository.save(user);

        String title = "사람 구합니다";
        String content ="3명 구해요";
        CategoryEnum category =CategoryEnum.DELIVERY;
        Date deadline = new Date(321321321);
        int numberPepole = 5;
        int currentNumberPeople = 2;
        String contactMethod = "Url";
        String imageUrl = "Url";

        Post post = new Post(1L,title,content,category,deadline,numberPepole,currentNumberPeople,contactMethod,user,imageUrl,0);

        postRepository.save(post);

        List<Post> result = postRepositorySupport.findByCategoryDefault(category);

        System.out.println(result.get(0).getCategory());

        assertEquals(result.size(),1);





    }

}
