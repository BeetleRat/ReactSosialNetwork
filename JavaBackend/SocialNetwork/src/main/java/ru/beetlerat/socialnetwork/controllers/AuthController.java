package ru.beetlerat.socialnetwork.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.beetlerat.socialnetwork.dto.user.authorized.*;
import ru.beetlerat.socialnetwork.models.User;
import ru.beetlerat.socialnetwork.services.users.AuthUserService;
import ru.beetlerat.socialnetwork.services.users.FindUserService;
import ru.beetlerat.socialnetwork.utill.exceptions.user.NoLoginUserException;

@RestController
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600, allowCredentials = "true")
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthUserService authService;
    private final FindUserService findUserService;


    @Autowired
    public AuthController(AuthUserService authService, FindUserService findUserService) {
        this.authService = authService;
        this.findUserService = findUserService;
    }

    @GetMapping("/me")
    public ResponseEntity<ResponseToFront> getCurrentLoginUser() {
        User currentLoginUser;

        try {
            currentLoginUser = authService.getCurrentLoginUser();
        } catch (NoLoginUserException e) {
            return ResponseEntity.ok(ResponseToFront.NotAuthorized());
        }

        return ResponseEntity.ok(
                ShortUserInfoResponse.FromAuthorizedUser(
                        ShortUserInfo.FromUser(currentLoginUser)
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
}
