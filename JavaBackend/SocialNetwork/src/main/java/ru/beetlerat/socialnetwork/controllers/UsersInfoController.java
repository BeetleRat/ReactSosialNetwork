package ru.beetlerat.socialnetwork.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.beetlerat.socialnetwork.dto.ResponseToFront;
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
@RequestMapping("/api/users")
public class UsersInfoController {
    private final UserListService userListService;
    private final FindUserService findUserService;
    private final ModelMapper modelMapper;
    private final JwtUtils jwtUtils;

    @Autowired
    public UsersInfoController(UserListService userListService, FindUserService findUserService, ModelMapper modelMapper, JwtUtils jwtUtils) {
        this.userListService = userListService;
        this.findUserService = findUserService;
        this.modelMapper = modelMapper;
        this.jwtUtils = jwtUtils;
    }

    @GetMapping
    public ResponseEntity<ResponseToFront> getUsersFromServer(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        Optional<User> requestedUser = getAuthUserFromHeader(authHeader);
        List<UserDTO> users =
                userListService.getList().stream()
                        .map(user -> convertToUserDTO(user, requestedUser))
                        .toList();

        UsersResponseDTO serverAnswer = UsersResponseDTO.FromUsersAndTotalUsers(users, userListService.getUsersCount());

        return ResponseEntity.ok(serverAnswer);
    }

    @GetMapping(params = {"page", "count"})
    public ResponseEntity<ResponseToFront> getUsersPaginationFromServer(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
            @RequestParam("page") int page, @RequestParam("count") int count) {

        int defaultPageSize = userListService.getDefaultPageSize();

        return getUsersPaginationResponse(authHeader, defaultPageSize, page, count);
    }

    @GetMapping(params = {"page", "count", "pageSize"})
    public ResponseEntity<ResponseToFront> getUsersPaginationFromServer(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
            @RequestParam("page") int page, @RequestParam("count") int count,
            @RequestParam("pageSize") int pageSize) {

        return getUsersPaginationResponse(authHeader, pageSize, page, count);
    }

    private ResponseEntity<ResponseToFront> getUsersPaginationResponse(String authHeader, int pageSize, int page, int count) {
        Optional<User> requestedUser = getAuthUserFromHeader(authHeader);

        userListService.setPageSize(pageSize);

        List<UserDTO> users =
                userListService.getPaginationData(page, count).stream()
                        .map(user -> convertToUserDTO(user, requestedUser))
                        .toList();

        UsersResponseDTO serverAnswer = UsersResponseDTO.FromUsersAndTotalUsers(users, userListService.getUsersCount());

        return ResponseEntity.ok(serverAnswer);
    }

    private Optional<User> getAuthUserFromHeader(String authHeader) {
        String authUsername = jwtUtils.getAuthUsernameFromHeader(authHeader);
        User authUser = findUserService.getByUsername(authUsername);

        return Optional.ofNullable(authUser);
    }

    // Конвертация из DTO в модели


    // Конвертация из модели в DTO
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
    private ResponseEntity<ResponseToFront> handleNoLoginUserException(NoLoginUserException exception) {
        return ResponseEntity.ok(ResponseToFront.NotAuthorized());
    }
}
