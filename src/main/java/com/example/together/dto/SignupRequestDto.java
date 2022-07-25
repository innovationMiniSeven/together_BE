package com.example.together.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SignupRequestDto {
    private String username;

    private String password;
    // confirmPassword로 수정할것!
    private String confirmPassword;

    private String nickname;

}
