package ru.beetlerat.socialnetwork.dto.user.authorized;

public class AuthorizationUserInfoDTO {
    private String username;
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
}
