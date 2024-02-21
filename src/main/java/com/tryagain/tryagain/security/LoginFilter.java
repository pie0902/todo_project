package com.tryagain.tryagain.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tryagain.tryagain.jwt.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public LoginFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        //setFilterProcessesUrl();
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
        HttpServletResponse response) throws AuthenticationException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, String> requestBody = objectMapper.readValue(request.getInputStream(),
                Map.class);
            //클라이언트 요청에서 email,password 추출
            String email = requestBody.get("email");
            String password = requestBody.get("password");
            //스프링 시큐리티에서 email과 password를 검증하기 위해서는 token에 담아야 함
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                email, password);
            //token에 담은 검증을 위한 AuthenticationManager로 전달
            return authenticationManager.authenticate(authToken);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //로그인 실패시 실행하는 메소드
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
        HttpServletResponse response, AuthenticationException failed) throws IOException {
        //로그인 실패시 401 응답 코드 반환
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        //응답 내용 타입 설정
        response.setContentType("application/json;charset=UTF-8");
        Map<String, Object> responseData = Map.of(
            "statusCode", 401,
            "message", "로그인 실패: " + failed.getMessage());
        //Map 객체를 JSON 문자열로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(responseData);
        // 응답 본문에 JSON 문자열 작성
        PrintWriter out = response.getWriter();
        out.print(jsonResponse);
        out.flush();
    }

    //로그인 성공시 실행하는 메소드 (여기서 JWT를 발급하면 됨)
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
        HttpServletResponse response, FilterChain chain, Authentication authentication)
        throws IOException {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        String token = jwtUtil.createJwt(customUserDetails.getUser());

        // 로그인 성공 응답 본문에 JWT 토큰 포함
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);
        PrintWriter out = response.getWriter();
        // JWT 토큰을 응답 본문에 포함
        String responseBody = String.format(
            "{\"statusCode\": 200, \"msg\": \"로그인 성공\", \"token\": \"%s\"}", token);
        out.print(responseBody);
        out.flush();
    }
}

//    protected void successfulAuthentication(HttpServletRequest request,
//        HttpServletResponse response, FilterChain chain, Authentication authentication)
//        throws IOException {
//        //UserDetailsS
//        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
//        String username = customUserDetails.getUsername();
//        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
//        //System.out.println(customUserDetails.getUser());
//        System.out.println("로그인 완료");
//        String token = jwtUtil.createJwt(customUserDetails.getUser());
//        response.addHeader("Authorization", "Bearer " + token);
//        // 로그인 성공 응답 본문 작성
//        response.setContentType("application/json;charset=UTF-8");
//        response.setStatus(HttpServletResponse.SC_OK);
//        PrintWriter out = response.getWriter();
//        out.print("{\"statusCode\": 200, \"msg\": \"로그인 성공\"}");
//        out.flush();
//    }
