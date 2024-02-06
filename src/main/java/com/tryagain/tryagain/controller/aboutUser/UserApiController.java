package com.tryagain.tryagain.controller;

import com.tryagain.tryagain.dto.AddUserRequest;
import com.tryagain.tryagain.dto.CreateAccessTokenResponse;
import com.tryagain.tryagain.service.AuthenticationService;
import com.tryagain.tryagain.service.TokenService;
import com.tryagain.tryagain.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@Controller
public class UserApiController {
    private final UserService userService;
    private final AuthenticationService authenticationService;
    @PostMapping("/user")
    public String signUp(AddUserRequest request) {
//       userService.save(request);
        authenticationService.createUserAndGenerateToken(request);
        return "redirect:/login";
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request,HttpServletResponse response){
        new SecurityContextLogoutHandler().logout(request,response, SecurityContextHolder.getContext()
                .getAuthentication());
        return "redirect:/login";
    }
}
