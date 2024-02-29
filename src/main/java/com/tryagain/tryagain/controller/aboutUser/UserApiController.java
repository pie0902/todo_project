package com.tryagain.tryagain.controller.aboutUser;


import com.tryagain.tryagain.domain.User;
import com.tryagain.tryagain.dto.aboutUser.UserLoginRequest;
import com.tryagain.tryagain.dto.aboutUser.UserLoginResponse;
import com.tryagain.tryagain.dto.aboutUser.UserSignupRequestDto;
import com.tryagain.tryagain.dto.aboutUser.UserSignupResponseDto;
import com.tryagain.tryagain.jwt.JwtUtil;
import com.tryagain.tryagain.security.CustomUserDetails;
import com.tryagain.tryagain.service.aboutUser.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@CrossOrigin
public class UserApiController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Operation(summary = "test", description = "test")
    @GetMapping("/test")
    @CrossOrigin(origins = "http://localhost:3000")
    public void test() {
        User user = new User();
        System.out.println(user);
    }
    @Operation(summary = "회원가입", description = "회원가입")
    @PostMapping("/api/users/signup")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<UserSignupResponseDto> singUp(@RequestBody UserSignupRequestDto requestDto) {
        UserSignupResponseDto userSignupResponseDto = userService.signUp(requestDto);
        return ResponseEntity.ok(userSignupResponseDto);
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getEmail(),
                    loginRequest.getPassword()
                )
            );
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            String jwt = jwtUtil.createJwt(userDetails.getUser()); // JWT 생성
            UserLoginResponse loginResponse = new UserLoginResponse(jwt);
            return ResponseEntity.ok(loginResponse);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
