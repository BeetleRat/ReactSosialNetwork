package ru.beetlerat.socialnetwork.utill.exceptions.token;

public class TokenRefreshedException extends RuntimeException {
    public TokenRefreshedException(String token, String message) {
        super(String.format("Failed for [%s]: %s", token, message));
    }
}
