package com.tryagain.tryagain.dto.aboutUser;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class AddUserRequest {
    private String email;
    private String name;
    private String password;
}
