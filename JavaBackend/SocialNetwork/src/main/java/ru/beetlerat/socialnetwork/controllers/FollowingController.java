package ru.beetlerat.socialnetwork.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.beetlerat.socialnetwork.dto.follow.FollowDTO;
import ru.beetlerat.socialnetwork.dto.ResponseToFront;
import ru.beetlerat.socialnetwork.services.users.UserFollowService;
import ru.beetlerat.socialnetwork.utill.exceptions.NotValidException;
import ru.beetlerat.socialnetwork.utill.exceptions.user.NoLoginUserException;
import ru.beetlerat.socialnetwork.utill.exceptions.user.UserNotFoundException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/follow")
public class FollowingController {
    private final UserFollowService followService;

    @Autowired
    public FollowingController(UserFollowService followService) {
        this.followService = followService;
    }

    @PostMapping
    public ResponseEntity<ResponseToFront> followUnfollowUser(@RequestBody @Valid FollowDTO followDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()
                || followDTO.equals(new FollowDTO())) {
            throw new NotValidException("Follow request not valid.");
        }

        if (followDTO.isFollow()) {
            followService.subscribeUserToUserByID(followDTO.getUserID(), followDTO.getFollowedUserID());
        } else {
            followService.unsubscribeUserToUserByID(followDTO.getUserID(), followDTO.getFollowedUserID());
        }

        return ResponseEntity.ok(ResponseToFront.Ok());
    }

    @PostMapping("/{userID}")
    public ResponseEntity<ResponseToFront> followUser(@PathVariable("userID") int userID) {
        followService.subscribeLoginUserToUserByID(userID);

        return ResponseEntity.ok(ResponseToFront.Ok());
    }

    @DeleteMapping("/{userID}")
    public ResponseEntity<ResponseToFront> unfollowUser(@PathVariable("userID") int userID) {
        followService.unsubscribeLoginUserToUserByID(userID);

        return ResponseEntity.ok(ResponseToFront.Ok());
    }

    @ExceptionHandler
    private ResponseEntity<ResponseToFront> handleNoLoginUserException(NoLoginUserException exception) {
        return ResponseEntity.ok(ResponseToFront.NotAuthorized());
    }

    @ExceptionHandler
    private ResponseEntity<ResponseToFront> handleNotFoundException(UserNotFoundException exception) {
        return ResponseEntity.ok(ResponseToFront.NotFound());
    }

    @ExceptionHandler
    private ResponseEntity<ResponseToFront> handleNotValidException(NotValidException exception) {
        return ResponseEntity.ok(ResponseToFront.FromExceptionMessage(exception.getMessage()));
    }
}
