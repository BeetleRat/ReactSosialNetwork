package ru.beetlerat.socialnetwork.dto.user.authorized;

public class AuthorizationRefreshTokenDTO {
    private String refreshToken;

    public AuthorizationRefreshTokenDTO() {
    }

    public AuthorizationRefreshTokenDTO(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
