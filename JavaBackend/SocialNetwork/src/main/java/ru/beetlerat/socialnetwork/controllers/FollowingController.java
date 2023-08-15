package ru.beetlerat.socialnetwork.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.beetlerat.socialnetwork.dto.user.authorized.ResponseToFront;
import ru.beetlerat.socialnetwork.dto.user.authorized.ShortUserInfoResponse;
import ru.beetlerat.socialnetwork.services.users.UserFollowService;
import ru.beetlerat.socialnetwork.utill.exceptions.user.NoLoginUserException;
import ru.beetlerat.socialnetwork.utill.exceptions.user.UserNotFoundException;

@RestController
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600, allowCredentials = "true")
@RequestMapping("/api/follow")
public class FollowingController {
    private final UserFollowService followService;

    @Autowired
    public FollowingController(UserFollowService followService) {
        this.followService = followService;
    }

    @PostMapping("/{userID}")
    public ResponseEntity followUser(@PathVariable("userID") int userID) {
        followService.subscribeLoginUserToUserByID(userID);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{userID}")
    public ResponseEntity unfollowUser(@PathVariable("userID") int userID) {
        followService.unsubscribeLoginUserToUserByID(userID);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<ResponseToFront> handleNoLoginUserException(NoLoginUserException exception) {
        return ResponseEntity.ok(ResponseToFront.NotAuthorized());
    }

    @ExceptionHandler
    private ResponseEntity<ResponseToFront> handleNotFoundException(UserNotFoundException exception) {
        return ResponseEntity.ok(ResponseToFront.NotFound());
    }
}
