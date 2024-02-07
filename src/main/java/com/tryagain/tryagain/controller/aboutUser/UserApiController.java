package com.tryagain.tryagain.controller.aboutUser;

import com.tryagain.tryagain.dto.SignUpResponse;
import com.tryagain.tryagain.dto.aboutUser.AddUserRequest;
import com.tryagain.tryagain.service.AuthenticationService;
import com.tryagain.tryagain.service.aboutUser.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
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
    public String signUp(@RequestBody AddUserRequest request) {
//       userService.save(request);
        SignUpResponse signUpResponse = authenticationService.createUserAndGenerateToken(request);
//        return "redirect:/login";
        return signUpResponse.getToken();
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request,HttpServletResponse response){
        new SecurityContextLogoutHandler().logout(request,response, SecurityContextHolder.getContext()
                .getAuthentication());
        return "redirect:/login";
    }
}
