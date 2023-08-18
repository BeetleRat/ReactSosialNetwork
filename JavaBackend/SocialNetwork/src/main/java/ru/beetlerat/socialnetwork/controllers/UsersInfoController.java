package ru.beetlerat.socialnetwork.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.beetlerat.socialnetwork.dto.user.authorized.ResponseToFront;
import ru.beetlerat.socialnetwork.dto.user.full.UserDTO;
import ru.beetlerat.socialnetwork.dto.user.full.FullUserInfoDTO;
import ru.beetlerat.socialnetwork.dto.user.full.UsersResponseDTO;
import ru.beetlerat.socialnetwork.models.User;
import ru.beetlerat.socialnetwork.security.JWT.JwtUtils;
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
import java.util.Optional;
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
    private final JwtUtils jwtUtils;

    @Autowired
    public UsersInfoController(UserListService userListService, FindUserService findUserService, UserFollowService userFollowService, AuthUserService authService, ModelMapper modelMapper, JwtUtils jwtUtils) {
        this.userListService = userListService;
        this.findUserService = findUserService;
        this.userFollowService = userFollowService;
        this.authService = authService;
        this.modelMapper = modelMapper;
        this.jwtUtils = jwtUtils;
    }

    @GetMapping
    public ResponseEntity<UsersResponseDTO> getUsersFromServer(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        Optional<User> requestedUser = getAuthUserFromHeader(authHeader);
        List<UserDTO> users =
                userListService.getList().stream()
                        .map(user -> convertToUserDTO(user, requestedUser))
                        .toList();

        UsersResponseDTO serverAnswer = UsersResponseDTO.FromUsersTotalUsersAndErrors(users, userListService.getUsersCount(), null);

        return ResponseEntity.ok(serverAnswer);
    }

    @GetMapping(params = {"page", "count"})
    public ResponseEntity<UsersResponseDTO> getUsersPaginationFromServer(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
            @RequestParam("page") int page, @RequestParam("count") int count) {

        int defaultPageSize = 5;

        return getUsersPaginationResponse(authHeader, defaultPageSize, page, count);
    }

    @GetMapping(params = {"page", "count", "pageSize"})
    public ResponseEntity<UsersResponseDTO> getUsersPaginationFromServer(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
            @RequestParam("page") int page, @RequestParam("count") int count,
            @RequestParam("pageSize") int pageSize) {

        return getUsersPaginationResponse(authHeader, pageSize, page, count);
    }

    private ResponseEntity<UsersResponseDTO> getUsersPaginationResponse(String authHeader, int pageSize, int page, int count) {
        Optional<User> requestedUser = getAuthUserFromHeader(authHeader);

        userListService.setPageSize(pageSize);

        List<UserDTO> users =
                userListService.getPaginationData(page, count).stream()
                        .map(user -> convertToUserDTO(user, requestedUser))
                        .toList();

        UsersResponseDTO serverAnswer = UsersResponseDTO.FromUsersTotalUsersAndErrors(users, userListService.getUsersCount(), null);

        return ResponseEntity.ok(serverAnswer);
    }

    private Optional<User> getAuthUserFromHeader(String authHeader) {
        String jwtToken = jwtUtils.headerToToken(authHeader);
        String requestedUserUsername = jwtUtils.getUsername(jwtToken);

        return Optional.ofNullable(findUserService.getByUsername(requestedUserUsername));
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

    private UserDTO convertToUserDTO(User user, Optional<User> loginUser) {
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        if (loginUser.isPresent()) {
            Set<User> followedUsers = loginUser.get().getFollowedUsers();
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
    private ResponseEntity<ResponseToFront> handleNoLoginUserException(NoLoginUserException exception) {
        return ResponseEntity.ok(ResponseToFront.NotAuthorized());
    }
}
