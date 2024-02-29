package com.tryagain.tryagain.dto.aboutUser;

public class UserLoginResponse {
    private String token;
    private String tokenType = "Bearer";
    // 필요한 경우, 추가적인 정보를 여기에 포함시킬 수 있습니다.
    // 예: private String username;

    public UserLoginResponse(String token) {
        this.token = token;
    }

    // Getter와 Setter
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

}
