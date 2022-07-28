package com.example.together.controller;

import com.example.together.dto.AuthResponseDto;
import com.example.together.dto.LoginRequestDto;
import com.example.together.dto.SignupRequestDto;
import com.example.together.security.UserDetailsImpl;
import com.example.together.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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


    @GetMapping("/api/auth")
    public AuthResponseDto getAuth(@AuthenticationPrincipal UserDetailsImpl userDetails){
        String nickname = userDetails.getUser().getNickname();

        AuthResponseDto authResponseDto = new AuthResponseDto(nickname);
        return authResponseDto;
    }

}
