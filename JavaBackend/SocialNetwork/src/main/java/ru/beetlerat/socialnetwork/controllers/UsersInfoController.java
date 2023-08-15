package ru.beetlerat.socialnetwork.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.beetlerat.socialnetwork.dto.user.authorized.ResponseToFront;
import ru.beetlerat.socialnetwork.dto.user.full.UserDTO;
import ru.beetlerat.socialnetwork.dto.user.authorized.ShortUserInfoResponse;
import ru.beetlerat.socialnetwork.dto.user.full.FullUserInfoDTO;
import ru.beetlerat.socialnetwork.dto.user.full.UsersResponseDTO;
import ru.beetlerat.socialnetwork.models.User;
import ru.beetlerat.socialnetwork.services.users.AuthUserService;
import ru.beetlerat.socialnetwork.services.users.FindUserService;
import ru.beetlerat.socialnetwork.services.users.UserFollowService;
import ru.beetlerat.socialnetwork.services.users.UserListService;
import ru.beetlerat.socialnetwork.utill.exceptions.*;
import ru.beetlerat.socialnetwork.utill.exceptions.user.NoLoginUserException;
import ru.beetlerat.socialnetwork.utill.exceptions.user.UserAlreadyCreatedException;
import ru.beetlerat.socialnetwork.utill.exceptions.user.UserErrorResponse;
import ru.beetlerat.socialnetwork.utill.exceptions.user.UserNotFoundException;

import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600, allowCredentials = "true")
@RequestMapping("/api/users")
public class UsersInfoController {
    private final UserListService userListService;
    private final FindUserService findUserService;
    private final UserFollowService userFollowService;
    private final AuthUserService authService;
    private final ModelMapper modelMapper;

    @Autowired
    public UsersInfoController(UserListService userListService, FindUserService findUserService, UserFollowService userFollowService, AuthUserService authService, ModelMapper modelMapper) {
        this.userListService = userListService;
        this.findUserService = findUserService;
        this.userFollowService = userFollowService;
        this.authService = authService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<UsersResponseDTO> getUsersFromServer() {
        List<UserDTO> users = userListService.getList().stream().map(this::convertToUserDTO).toList();

        UsersResponseDTO serverAnswer = UsersResponseDTO.FromUsersTotalUsersAndErrors(users, userListService.getUsersCount(), null);
        return ResponseEntity.ok(serverAnswer);
    }

    @GetMapping(params = {"page", "count"})
    public ResponseEntity<UsersResponseDTO> getUsersPaginationFromServer(@RequestParam("page") int page, @RequestParam("count") int count) {
        List<UserDTO> users = userListService.getPaginationData(page, count).stream().map(this::convertToUserDTO).toList();

        UsersResponseDTO serverAnswer = UsersResponseDTO.FromUsersTotalUsersAndErrors(users, userListService.getUsersCount(), null);
        return ResponseEntity.ok(serverAnswer);
    }

    // Конвертация из DTO в модели
    private User convertToUser(FullUserInfoDTO fullUserInfoDTO) {
        // Автоматически конвертируем поля совпадающие по названию геттеров/сеттеров
        User user = modelMapper.map(fullUserInfoDTO, User.class);

        // Вручную конвертируем дополнительные поля
        if (fullUserInfoDTO.getFollowedUsersIds() != null) {
            user.setFollowedUsers(findUserService.getUsersByIds(fullUserInfoDTO.getFollowedUsersIds()));
        }
        if (fullUserInfoDTO.getUsersWhoFollowedMeID() != null) {
            user.setUsersWhoFollowedMe(findUserService.getUsersByIds(fullUserInfoDTO.getUsersWhoFollowedMeID()));
        }

        user.setFacebook(fullUserInfoDTO.getContacts().getFacebook());
        user.setWebsite(fullUserInfoDTO.getContacts().getWebsite());
        user.setVk(fullUserInfoDTO.getContacts().getVk());
        user.setTwitter(fullUserInfoDTO.getContacts().getTwitter());
        user.setInstagram(fullUserInfoDTO.getContacts().getInstagram());
        user.setYoutube(fullUserInfoDTO.getContacts().getYoutube());
        user.setGithub(fullUserInfoDTO.getContacts().getGithub());
        user.setMainlink(fullUserInfoDTO.getContacts().getMainlink());

        return user;
    }

    private User convertToUser(UserDTO userDTO) {
        // Автоматически конвертируем поля совпадающие по названию геттеров/сеттеров
        User user = modelMapper.map(userDTO, User.class);

        user.setFacebook(userDTO.getContacts().getFacebook());
        user.setWebsite(userDTO.getContacts().getWebsite());
        user.setVk(userDTO.getContacts().getVk());
        user.setTwitter(userDTO.getContacts().getTwitter());
        user.setInstagram(userDTO.getContacts().getInstagram());
        user.setYoutube(userDTO.getContacts().getYoutube());
        user.setGithub(userDTO.getContacts().getGithub());
        user.setMainlink(userDTO.getContacts().getMainlink());

        return user;
    }

    // Конвертация из модели в DTO
    private UserDTO convertToFullUserInfoDTO(User user) {
        // Автоматически конвертируем поля совпадающие по названию геттеров/сеттеров
        FullUserInfoDTO fullUserInfoDTO = modelMapper.map(user, FullUserInfoDTO.class);

        // Вручную конвертируем дополнительные поля
        fullUserInfoDTO.setUserRole(user.getSecuritySettings().getUserRole());
        fullUserInfoDTO.setPassword(user.getSecuritySettings().getPassword());
        fullUserInfoDTO.setUsername(user.getSecuritySettings().getUsername());
        fullUserInfoDTO.setFollowedUsersIds(userFollowService.getIdsFollowedUsers(user));
        fullUserInfoDTO.setUsersWhoFollowedMeID(userFollowService.getIdsUsersWhoFollowedMe(user));
        fullUserInfoDTO.setFollow(false);

        return fullUserInfoDTO;
    }

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

    // Обработчики исключений
    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handleNotFoundException(UserNotFoundException exception) {
        UserErrorResponse personErrorResponse = new UserErrorResponse(ResponseToFront.Code.NOT_FOUND.getCode(), "Person with this id was not found!");

        return ResponseEntity.ok(personErrorResponse);
    }

    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handleNotValidException(NotValidException exception) {
        UserErrorResponse personErrorResponse = new UserErrorResponse(ResponseToFront.Code.NOT_VALID.getCode(), "Person has not valid fields:" + exception.getMessage());

        return ResponseEntity.ok(personErrorResponse);
    }

    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handleAlreadyCreatedException(UserAlreadyCreatedException exception) {
        UserErrorResponse personErrorResponse = new UserErrorResponse(ResponseToFront.Code.EXCEPTION.getCode(), "Person is already exist:" + exception.getMessage());

        return ResponseEntity.ok(personErrorResponse);
    }

    @ExceptionHandler
    private ResponseEntity<ShortUserInfoResponse> handleNoLoginUserException(NoLoginUserException exception) {
        return ResponseEntity.ok(ShortUserInfoResponse.NotAuthorized());
    }
}
