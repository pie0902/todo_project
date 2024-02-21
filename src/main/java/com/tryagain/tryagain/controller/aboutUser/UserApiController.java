package com.tryagain.tryagain.controller.aboutUser;


import com.tryagain.tryagain.domain.User;
import com.tryagain.tryagain.dto.aboutUser.UserSignupRequestDto;
import com.tryagain.tryagain.dto.aboutUser.UserSignupResponseDto;
import com.tryagain.tryagain.service.aboutUser.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
//    @GetMapping("/logout")
//    public String logout(HttpServletRequest request,HttpServletResponse response){
//        new SecurityContextLogoutHandler().logout(request,response, SecurityContextHolder.getContext()
//                .getAuthentication());
//        return "redirect:/login";
//    }
}
