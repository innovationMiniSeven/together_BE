package com.example.together.service;

import com.example.together.dto.LoginRequestDto;
import com.example.together.dto.SignupRequestDto;
import com.example.together.model.User;
import com.example.together.repository.UserRepository;
import com.example.together.security.UserDetailsImpl;
import com.example.together.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final UserDetailsServiceImpl userDetailsService;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserDetailsServiceImpl userDetailsService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;

    }

    public void registerUser(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();
        String nickname = signupRequestDto.getNickname();
        String password = signupRequestDto.getPassword();
        String confirmPassword = signupRequestDto.getConfirmPassword();

        Optional<User> usernameUserFound = userRepository.findByUsername(username);
        if(usernameUserFound.isPresent()){
            throw new IllegalArgumentException("중복된 username이 존재합니다");
        }
        Optional<User> nicknameUserFound = userRepository.findByNickname(nickname);
        if(nicknameUserFound.isPresent()){
            throw new IllegalArgumentException("중복된 nickname이 존재합니다");
        }
        if(!password.equals(confirmPassword)){
            throw new IllegalArgumentException("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
        }
        User user = new User(nickname,username,passwordEncoder.encode(password));

        userRepository.save(user);

    }

    public Authentication getAuthentication(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

}
