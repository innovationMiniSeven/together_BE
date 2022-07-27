package com.example.together.security;

import com.example.together.repository.UserRepository;
import com.example.together.security.jwt.JwtAuthenticationFilter;
import com.example.together.security.jwt.JwtAuthorizationFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity // 스프링 Security 지원을 가능하게 함
@EnableGlobalMethodSecurity(securedEnabled = true) // @Secured 어노테이션 활성화
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CorsConfig corsConfig;

    @Autowired
    private UserRepository userRepository;

    @Bean
    public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) {
// h2-console 사용에 대한 허용 (CSRF, FrameOptions 무시)
        web
                .ignoring()
                .antMatchers("/h2-console/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .addFilter(corsConfig.corsFilter())
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http
                .cors()
                .configurationSource(corsConfigurationSource());

        http
                .formLogin().disable()
                .httpBasic().disable()

                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), userRepository));



        http.authorizeRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
// image 폴더를 login 없이 허용
                .antMatchers("/images/**").permitAll()
// css 폴더를 login 없이 허용
                .antMatchers("/css/**").permitAll()
// 회원 관리 처리 API 전부를 login 없이 허용
                .antMatchers(HttpMethod.GET,"/api/**").permitAll()
                .antMatchers(HttpMethod.POST ,"/api/signup").permitAll()
                .antMatchers("/api/login").permitAll()
                //.antMatchers(HttpMethod.POST,"/api/login").permitAll()
                .antMatchers(HttpMethod.GET,"/**").permitAll()
// 그 외 어떤 요청이든 '인증'
                .anyRequest().authenticated()
                .and()
// [로그아웃 기능]
                .logout()
// 로그아웃 요청 처리 URL
                .logoutUrl("/api/logout")
                .logoutSuccessUrl("/")
                .logoutSuccessHandler(new customLogoutSuccessHandler())
                .permitAll();
// "접근 불가" 페이지 URL 설정


        http
                .exceptionHandling()
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint());

    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.addAllowedOrigin("http://localhost:3000");
        configuration.addAllowedOrigin("http://togetherheyyo.s3-website.ap-northeast-2.amazonaws.com/");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;

    }

    private static class customLogoutSuccessHandler implements LogoutSuccessHandler{
        private final ObjectMapper objectMapper = new ObjectMapper();

        @Override
        public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
            response.setStatus(HttpStatus.OK.value());
            response.setCharacterEncoding("UTF-8");
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

            objectMapper.writeValue(response.getWriter(),"로그아웃에 성공했습니다");


        }
    }


    private static class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
        private final ObjectMapper objectMapper = new ObjectMapper();
        @Override
       public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");


            objectMapper.writeValue(response.getWriter(), "로그인 후 이용해주세요");
        }
  }

//  private static class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler{
//      private final ObjectMapper objectMapper = new ObjectMapper();
//      @Override
//      public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//
//
//          response.setStatus(HttpStatus.OK.value());
//          response.setCharacterEncoding("UTF-8");
//          response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//
//          objectMapper.writeValue(response.getWriter(),"로그인에 성공했습니다");
//
//
//
//
//      }
//  }
//  private static class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler{
//      private final ObjectMapper objectMapper = new ObjectMapper();
//      @Override
//      public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
//
//          response.setStatus(HttpStatus.UNAUTHORIZED.value());
//          response.setCharacterEncoding("UTF-8");
//          response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//
//          objectMapper.writeValue(response.getWriter(),"로그인에 실패했습니다");
//
//      }
//  }


}