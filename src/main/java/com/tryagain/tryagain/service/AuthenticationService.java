package com.tryagain.tryagain.service;


import com.tryagain.tryagain.domain.User;
import com.tryagain.tryagain.dto.aboutUser.AddUserRequest;
import com.tryagain.tryagain.dto.SignUpResponse;
import com.tryagain.tryagain.service.aboutUser.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.tryagain.tryagain.config.jwt.TokenProvider;
import java.time.Duration;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final TokenProvider tokenProvider;
    public SignUpResponse createUserAndGenerateToken(AddUserRequest request) {
        User user = userService.saveUser(request);
        String accessToken = tokenProvider.generateToken(user, Duration.ofHours(2));
        return new SignUpResponse(user.getId(), accessToken);
    }
}
