package com.example.together.controller;

import com.example.together.dto.AuthResponseDto;
import com.example.together.dto.LoginRequestDto;
import com.example.together.dto.SignupRequestDto;
import com.example.together.model.User;
import com.example.together.security.UserDetailsImpl;
import com.example.together.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/api/signup")
    public void registerUser(@RequestBody SignupRequestDto signupRequestDto){
        userService.registerUser(signupRequestDto);

    }

    @PostMapping("/api/login")
    public void loginUser(@RequestBody LoginRequestDto loginRequestDto, HttpServletRequest request){

        User user = userService.loginUser(loginRequestDto);

        HttpSession session = request.getSession();
        session.setAttribute("user",user);


    }

    @GetMapping("/api/auth")
    public AuthResponseDto getAuth(@AuthenticationPrincipal UserDetailsImpl userDetails){
        String nickname = userDetails.getUser().getNickname();

        AuthResponseDto authResponseDto = new AuthResponseDto(nickname);

        return authResponseDto;

    }

}
