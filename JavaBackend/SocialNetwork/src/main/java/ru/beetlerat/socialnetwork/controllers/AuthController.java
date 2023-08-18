package ru.beetlerat.socialnetwork.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import ru.beetlerat.socialnetwork.dto.user.authorized.*;
import ru.beetlerat.socialnetwork.models.User;
import ru.beetlerat.socialnetwork.security.JWT.JwtUtils;
import ru.beetlerat.socialnetwork.security.types.SecurityUserDetails;
import ru.beetlerat.socialnetwork.services.users.AuthUserService;
import ru.beetlerat.socialnetwork.services.users.FindUserService;
import ru.beetlerat.socialnetwork.utill.exceptions.token.TokenNotFoundException;
import ru.beetlerat.socialnetwork.utill.exceptions.token.TokenRefreshedException;
import ru.beetlerat.socialnetwork.utill.exceptions.user.NoLoginUserException;

@RestController
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600, allowCredentials = "true")
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthUserService authService;
    private final FindUserService findUserService;
    private final JwtUtils jwtUtils;


    @Autowired
    public AuthController(AuthUserService authService, FindUserService findUserService, JwtUtils jwtUtils) {
        this.authService = authService;
        this.findUserService = findUserService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/login")
    public ResponseEntity<? extends ResponseToFront> loginUser(@RequestBody AuthorizationUserInfoDTO authRequest) {
        try {
            authService.setAuthentication(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getUsername(),
                            authRequest.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            return ResponseEntity.ok(ResponseToFront.BadCredentials());
        }

        User user = findUserService.getByUsername(authRequest.getUsername());

        SecurityUserDetails securityUserDetails = new SecurityUserDetails(user.getSecuritySettings());

        String jwtAccess = jwtUtils.generateAccessToken(securityUserDetails);
        String jwtRefresh = "";


        return ResponseEntity.ok(
                ShortUserInfoResponse.FromUserInfoAccessAndRefreshTokens(
                        ShortUserInfo.FromUser(user),
                        jwtAccess, jwtRefresh
                )
        );
    }


    @DeleteMapping("/login")
    public ResponseEntity logoutUser() {
        authService.logout();
        return new ResponseEntity(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<ResponseToFront> handleNoLoginUserException(NoLoginUserException exception) {
        return ResponseEntity.ok(ResponseToFront.NotFound());
    }

    @ExceptionHandler
    private void handleTokenRefreshedException(TokenRefreshedException exception) {
        System.out.println(exception);
    }

    @ExceptionHandler
    private void handleTokenNotFoundException(TokenNotFoundException exception) {
        System.out.println(exception);
    }
}
