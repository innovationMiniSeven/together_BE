package com.example.together.testData;

import com.example.together.model.CategoryEnum;
import com.example.together.model.Comment;
import com.example.together.model.Post;
import com.example.together.model.User;
import com.example.together.repository.PostRepository;
import com.example.together.repository.UserRepository;
import com.example.together.service.PostService;
import com.example.together.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class TestDataRunner implements ApplicationRunner {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    PostService postService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        User testUser1 = new User("1234","정국", passwordEncoder.encode("123"));
        User testUser2 = new User("123","제이홉", passwordEncoder.encode("123"));
        User testUser3 = new User("testnickname","testid",passwordEncoder.encode("test123"));
        userRepository.save(testUser1);
        userRepository.save(testUser2);
        userRepository.save(testUser3);

        CreateData(100,testUser1,testUser2);

    }

    public void  CreateData(int count,User testUser1,User testUser2){
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 3;
        List<Comment> commentList = new ArrayList<>();
        for(int i =0; i<count/2; i++){
            Random random = new Random();
            String title =random.ints(leftLimit, rightLimit + 1)
                    .limit(targetStringLength)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();
            String content = random.ints(leftLimit, rightLimit + 1)
                    .limit(targetStringLength)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();
            int categoryNumber = random.nextInt(4)+1;
            CategoryEnum category = CategoryEnum.ETC;
            if(categoryNumber  == 1){
                category = CategoryEnum.DELIVERY;
            } else if (categoryNumber == 2) {
                category = CategoryEnum.PURCHASE;

            } else if (categoryNumber == 3 ) {
                category = CategoryEnum.EXHIBITION;
            }
            int numberPeople = random.nextInt(10)+1;
            int currentNumberPeople = random.nextInt(numberPeople)+1;
            String contactMethod =  random.ints(leftLimit, rightLimit + 1)
                    .limit(targetStringLength)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();
            User user = testUser1;
            String imageUrl = random.ints(leftLimit, rightLimit + 1)
                    .limit(targetStringLength)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();
            int viewCount = random.nextInt(100)+1;

            int dateTime = random.nextInt(20000000)-10000000;

            LocalDateTime deadline = LocalDateTime.now();
            deadline = deadline.plusSeconds(dateTime);

            Post post = new Post((long)i,title,content,category,deadline,numberPeople,currentNumberPeople,contactMethod,user,imageUrl,viewCount,commentList);

            postRepository.save(post);


        }
        for(int j=count/2; j<count; j++){
            Random random = new Random();
            String title =random.ints(leftLimit, rightLimit + 1)
                    .limit(targetStringLength)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();
            String content = random.ints(leftLimit, rightLimit + 1)
                    .limit(targetStringLength)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();
            int categoryNumber = random.nextInt(4)+1;
            CategoryEnum category = CategoryEnum.ETC;
            if(categoryNumber  == 1){
                category = CategoryEnum.DELIVERY;
            } else if (categoryNumber == 2) {
                category = CategoryEnum.PURCHASE;

            } else if (categoryNumber == 3 ) {
                category = CategoryEnum.EXHIBITION;
            }
            int numberPeople = random.nextInt(10)+1;
            int currentNumberPeople = random.nextInt(numberPeople)+1;
            String contactMethod =  random.ints(leftLimit, rightLimit + 1)
                    .limit(targetStringLength)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();
            User user = testUser2;
            String imageUrl = random.ints(leftLimit, rightLimit + 1)
                    .limit(targetStringLength)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();
            int viewCount = random.nextInt(100)+1;

            int dateTime = random.nextInt(20000000)-10000000;

            LocalDateTime deadline = LocalDateTime.now();
            deadline = deadline.plusSeconds(dateTime);


            Post post = new Post((long)j,title,content,category,deadline,numberPeople,currentNumberPeople,contactMethod,user,imageUrl,viewCount,commentList);

            postRepository.save(post);

        }


    }

}
