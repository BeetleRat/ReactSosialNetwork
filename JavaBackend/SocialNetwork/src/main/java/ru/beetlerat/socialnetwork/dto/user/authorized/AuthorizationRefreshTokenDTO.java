package ru.beetlerat.socialnetwork.dto.user.authorized;

import javax.validation.constraints.NotNull;

public class AuthorizationRefreshTokenDTO {
    @NotNull(message = "Refresh token should not be empty")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AuthorizationRefreshTokenDTO that = (AuthorizationRefreshTokenDTO) o;

        return refreshToken != null ? refreshToken.equals(that.refreshToken) : that.refreshToken == null;
    }

    @Override
    public int hashCode() {
        return refreshToken != null ? refreshToken.hashCode() : 0;
    }
}
