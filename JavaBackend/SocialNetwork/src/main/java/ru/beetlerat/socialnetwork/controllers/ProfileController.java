package ru.beetlerat.socialnetwork.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.beetlerat.socialnetwork.dto.profile.ProfileStatusDTO;
import ru.beetlerat.socialnetwork.dto.user.authorized.ResponseToFront;
import ru.beetlerat.socialnetwork.dto.user.authorized.ShortUserInfoResponse;
import ru.beetlerat.socialnetwork.dto.user.full.UserDTO;
import ru.beetlerat.socialnetwork.models.User;
import ru.beetlerat.socialnetwork.services.users.AuthUserService;
import ru.beetlerat.socialnetwork.services.users.UsersCRUDService;
import ru.beetlerat.socialnetwork.utill.exceptions.user.NoLoginUserException;
import ru.beetlerat.socialnetwork.utill.exceptions.user.UserNotFoundException;
import ru.beetlerat.socialnetwork.utill.exceptions.NotValidException;

import java.util.Set;

@RestController
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600, allowCredentials = "true")
@RequestMapping("/api/profile")
public class ProfileController {
    private final UsersCRUDService UserCRUDService;
    private final AuthUserService authService;
    private final ModelMapper modelMapper;

    @Autowired
    public ProfileController(UsersCRUDService UserCRUDService, AuthUserService authService, ModelMapper modelMapper) {
        this.UserCRUDService = UserCRUDService;
        this.authService = authService;
        this.modelMapper = modelMapper;
    }


    @GetMapping(path = "/{id}")
    public ResponseEntity<UserDTO> getOneUserFromServerByID(@PathVariable("id") int id) {
        return ResponseEntity.ok(convertToUserDTO(UserCRUDService.getByID(id)));
    }

    @GetMapping(path = "/status/{id}")
    public ResponseEntity<String> getUserStatusFromServerByID(@PathVariable("id") int id) {
        return ResponseEntity.ok(UserCRUDService.getByID(id).getStatus());
    }


    @PutMapping
    public ResponseEntity<UserDTO> updateLoggedUserStatus(@RequestBody ProfileStatusDTO profileStatus, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new NotValidException("Status not valid.");
        }

        User user = profileStatusToLoginUser(profileStatus);
        UserCRUDService.update(user.getUserID(), user);

        return ResponseEntity.ok(convertToUserDTO(user));
    }

    // Конвертация из DTO в модели
    private User profileStatusToLoginUser(ProfileStatusDTO profileStatus) {
        User loginUser = authService.getCurrentLoginUser();
        loginUser.setStatus(profileStatus.getStatus());

        return loginUser;
    }

    // Конвертация из модели в DTO
    private UserDTO convertToUserDTO(User user) {
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        if (authService.isUserLogin()) {
            Set<User> followedUsers = authService.getCurrentLoginUser().getFollowedUsers();
            boolean isFollowedThisUser = followedUsers.contains(user);
            userDTO.setFollow(isFollowedThisUser);
        } else {
            userDTO.setFollow(false);
        }

        return userDTO;
    }

    @ExceptionHandler
    private ResponseEntity<ResponseToFront> handleNotFoundException(UserNotFoundException exception) {
        return ResponseEntity.ok(ResponseToFront.NotFound());
    }

    @ExceptionHandler
    private ResponseEntity<ResponseToFront> handleNoLoginUserException(NoLoginUserException exception) {
        return ResponseEntity.ok(ResponseToFront.NotAuthorized());
    }

    @ExceptionHandler
    private ResponseEntity<ResponseToFront> handleNotValidException(NotValidException exception) {
        return ResponseEntity.ok(ResponseToFront.FromExceptionMessage(exception.getMessage()));
    }
}
