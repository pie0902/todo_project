package com.tryagain.tryagain.dto.aboutUser;


import com.tryagain.tryagain.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class UserSignupResponseDto {

    private Long id;
    private String username;
    private String email;

    public UserSignupResponseDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
    }

}
