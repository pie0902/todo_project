package com.tryagain.tryagain.config;

import com.tryagain.tryagain.jwt.JwtFilter;
import com.tryagain.tryagain.jwt.JwtUtil;
import com.tryagain.tryagain.security.LoginFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final JwtUtil jwtUtil;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {

        return new BCryptPasswordEncoder();
    }

    //AuthenticationManager Bean 등록
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
        throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(auth -> auth.disable()) // CSRF 보호 기능을 비활성화
            .httpBasic(auth -> auth.disable()) // HTTP 기본 인증 비활성화
            // 폼 로그인 설정 활성화
            .formLogin(auth -> auth
                .loginPage("/login") // 로그인 페이지 URL 설정
                .defaultSuccessUrl("/", true) // 로그인 성공 시 리다이렉트할 기본 URL 설정
                .permitAll() // 로그인 페이지에 대한 접근을 모두에게 허용
            )
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/test","/api/user/info").authenticated()//"index"

                .requestMatchers("/login","/","/index.html","/static/**","/my_img/**","/css/**", "/v3/api-docs/**", "/api/users/signup",
                    "/swagger-ui/**", "/swagger-ui.html").permitAll()
                .anyRequest().authenticated())
            .addFilterBefore(new JwtFilter(jwtUtil), LoginFilter.class)
            .addFilterBefore(
                new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil),
                UsernamePasswordAuthenticationFilter.class)
            .sessionManagement(
                session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }


}