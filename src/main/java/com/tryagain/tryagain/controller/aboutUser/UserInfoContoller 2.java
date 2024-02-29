package com.tryagain.tryagain.controller.aboutUser;

import com.tryagain.tryagain.domain.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserInfoContoller {
    @GetMapping("/api/users/info")
    public UserInfo getUserInfo(){
        UserInfo userInfo = new UserInfo("user@example.com","username");
        return userInfo;
    }

}
