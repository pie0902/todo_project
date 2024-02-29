package com.tryagain.tryagain.dto.aboutUser;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class UserSignupRequestDto {
    private String username;
    private String password;
    private String email;
}
