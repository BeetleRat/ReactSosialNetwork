package ru.beetlerat.socialnetwork.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.beetlerat.socialnetwork.dto.ResponseToFront;
import ru.beetlerat.socialnetwork.dto.dbrequest.PaginationUserRequestDTO;
import ru.beetlerat.socialnetwork.dto.user.full.UserDTO;
import ru.beetlerat.socialnetwork.dto.user.full.UsersResponseDTO;
import ru.beetlerat.socialnetwork.dto.users.PaginatedUsersListFromDB;
import ru.beetlerat.socialnetwork.models.UserModel;
import ru.beetlerat.socialnetwork.security.JWT.JwtUtils;
import ru.beetlerat.socialnetwork.services.users.FindUserService;
import ru.beetlerat.socialnetwork.services.users.UserListService;
import ru.beetlerat.socialnetwork.utill.exceptions.NotValidException;
import ru.beetlerat.socialnetwork.utill.exceptions.user.NoLoginUserException;
import ru.beetlerat.socialnetwork.utill.exceptions.user.UserNotFoundException;

import java.util.*;
import java.util.function.Supplier;

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
    public ResponseEntity<ResponseToFront> getUsersPaginationFromServer(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "count", required = false) Long count,
            @RequestParam(name = "pageSize", required = false) Integer pageSize,
            @RequestParam(name = "term", required = false) String term,
            @RequestParam(name = "friend", required = false) Boolean friend
    ) {

        Optional<UserModel> requestedUser = getAuthUserFromHeader(authHeader);

        userListService.setPageSize(Objects.requireNonNullElse(pageSize, userListService.getDefaultPageSize()));

        PaginationUserRequestDTO paginationRequest = PaginationUserRequestDTO.FromRequestedUser(requestedUser);
        paginationRequest.setCount(
                Objects.requireNonNullElse(
                        count,
                        userListService.getTotalUsersCount()
                ).intValue()
        );
        paginationRequest.setPage(Objects.requireNonNullElse(page, 0));

        if (term != null) {
            return getUsersWithNamePatternPaginationResponse(paginationRequest, term, friend);
        }

        return getUsersPaginationResponse(paginationRequest, friend);
    }

    private Optional<UserModel> getAuthUserFromHeader(String authHeader) {
        String authUsername = jwtUtils.getAuthUsernameFromHeader(authHeader);
        UserModel authUser = findUserService.getByUsername(authUsername);

        return Optional.ofNullable(authUser);
    }

    private ResponseEntity<ResponseToFront> getUsersPaginationResponse(PaginationUserRequestDTO paginationRequest, Boolean friend) {

        return getUsersWithNamePatternPaginationResponse(paginationRequest, "", friend);
    }

    private ResponseEntity<ResponseToFront> getUsersWithNamePatternPaginationResponse(PaginationUserRequestDTO paginationRequest, String partOfUserName, Boolean friend) {
        if (friend == null) {
            return getUsersWithNamePatternPaginationResponse(paginationRequest, partOfUserName);
        }
        if (friend) {
            return getFriendsWithNamePatternPaginationResponse(paginationRequest, partOfUserName);
        } else {
            return getNotFriendsWithNamePatternPaginationResponse(paginationRequest, partOfUserName);
        }
    }

    private ResponseEntity<ResponseToFront> getUsersWithNamePatternPaginationResponse(PaginationUserRequestDTO paginationRequest, String partOfUsersName) {

        return createUsersPaginationResponse(paginationRequest, () -> userListService.getPaginationData(paginationRequest, partOfUsersName));
    }

    private ResponseEntity<ResponseToFront> getNotFriendsWithNamePatternPaginationResponse(PaginationUserRequestDTO paginationRequest, String partOfUsersName) {

        return createUsersPaginationResponse(paginationRequest, () -> userListService.getNotFriendPaginationData(paginationRequest, partOfUsersName));
    }

    private ResponseEntity<ResponseToFront> getFriendsWithNamePatternPaginationResponse(PaginationUserRequestDTO paginationRequest, String partOfUsersName) {

        return createUsersPaginationResponse(paginationRequest, () -> userListService.getFriendPaginationData(paginationRequest, partOfUsersName));
    }

    private ResponseEntity<ResponseToFront> createUsersPaginationResponse(PaginationUserRequestDTO paginationRequest, Supplier<PaginatedUsersListFromDB> databaseRequest) {


        PaginatedUsersListFromDB paginatedUsersListFromDB = databaseRequest.get();

        List<UserDTO> users =
                paginatedUsersListFromDB.getPageUserList().stream()
                        .map(user -> convertToUserDTO(user, paginationRequest.getRequestedUser()))
                        .toList();

        UsersResponseDTO serverAnswer = UsersResponseDTO.FromUsersAndTotalUsers(users, paginatedUsersListFromDB.getTotalUsers());

        return ResponseEntity.ok(serverAnswer);
    }


    // Конвертация из DTO в модели


    // Конвертация из модели в DTO
    private UserDTO convertToUserDTO(UserModel user, Optional<UserModel> loginUser) {
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        if (loginUser.isPresent()) {
            Set<UserModel> followedUsers = loginUser.get().getFollowedUsers();
            boolean isFollowedThisUser = followedUsers.contains(user);
            userDTO.setFollow(isFollowedThisUser);
        } else {
            userDTO.setFollow(false);
        }

        return userDTO;
    }

    // Обработчики исключений
    @ExceptionHandler
    private ResponseEntity<ResponseToFront> handleNotFoundException(UserNotFoundException exception) {
        List<UserDTO> emptyList = new ArrayList<>();

        UsersResponseDTO usersResponseDTO = UsersResponseDTO.FromUsersAndTotalUsers(emptyList, 0);
        usersResponseDTO.setMessage("Person was not found!");
        usersResponseDTO.setResultCode(ResponseToFront.Code.NOT_FOUND.getCode());

        return ResponseEntity.ok(usersResponseDTO);
    }

    @ExceptionHandler
    private ResponseEntity<ResponseToFront> handleNotValidException(NotValidException exception) {
        System.out.println(exception);
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    private ResponseEntity<ResponseToFront> handleNoLoginUserException(NoLoginUserException exception) {
        return ResponseEntity.ok(ResponseToFront.NotAuthorized());
    }
}
