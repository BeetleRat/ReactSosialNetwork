package ru.beetlerat.socialnetwork.services.users;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import ru.beetlerat.socialnetwork.models.User;

public interface AuthUserService {
    User getCurrentLoginUser();
    void setAuthentication(UsernamePasswordAuthenticationToken token) throws BadCredentialsException;

    boolean isUserLogin();

    void logout();
}
