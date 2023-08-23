package ru.beetlerat.socialnetwork.dto.user.authorized;

import javax.validation.constraints.NotNull;

public class AuthorizationUserInfoDTO {
    @NotNull(message = "Username token should not be empty")
    private String username;
    @NotNull(message = "Password token should not be empty")
    private String password;
    private boolean rememberMe;

    public AuthorizationUserInfoDTO() {

    }

    private AuthorizationUserInfoDTO(String username, String password, boolean rememberMe) {
        this.username = username;
        this.password = password;
        this.rememberMe = rememberMe;
    }

    public static AuthorizationUserInfoDTO FromUsernamePasswordAndRememberMe(String username, String password, boolean rememberMe) {
        return new AuthorizationUserInfoDTO(username, password, rememberMe);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AuthorizationUserInfoDTO that = (AuthorizationUserInfoDTO) o;

        if (rememberMe != that.rememberMe) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        return password != null ? password.equals(that.password) : that.password == null;
    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (rememberMe ? 1 : 0);
        return result;
    }
}
