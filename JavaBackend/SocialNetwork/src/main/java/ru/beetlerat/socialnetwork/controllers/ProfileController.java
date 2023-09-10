package ru.beetlerat.socialnetwork.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.beetlerat.socialnetwork.dto.profile.ProfilePhotoURLResponse;
import ru.beetlerat.socialnetwork.dto.profile.ProfileStatusDTO;
import ru.beetlerat.socialnetwork.dto.ResponseToFront;
import ru.beetlerat.socialnetwork.dto.profile.ProfileStatusResponse;
import ru.beetlerat.socialnetwork.dto.user.full.UserDTO;
import ru.beetlerat.socialnetwork.dto.user.full.UserResponseDTO;
import ru.beetlerat.socialnetwork.models.UserModel;
import ru.beetlerat.socialnetwork.services.files.ImageService;
import ru.beetlerat.socialnetwork.services.users.AuthUserService;
import ru.beetlerat.socialnetwork.services.users.StatusService;
import ru.beetlerat.socialnetwork.services.users.UsersCRUDService;
import ru.beetlerat.socialnetwork.utill.exceptions.user.NoLoginUserException;
import ru.beetlerat.socialnetwork.utill.exceptions.user.UserAlreadyCreatedException;
import ru.beetlerat.socialnetwork.utill.exceptions.user.UserNotFoundException;
import ru.beetlerat.socialnetwork.utill.exceptions.NotValidException;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {
    private final UsersCRUDService userCRUDService;
    private final StatusService statusService;
    private final ImageService imageService;
    private final AuthUserService authService;
    private final ModelMapper modelMapper;

    @Autowired
    public ProfileController(UsersCRUDService UserCRUDService, StatusService statusService, ImageService imageService, AuthUserService authService, ModelMapper modelMapper) {
        this.userCRUDService = UserCRUDService;
        this.statusService = statusService;
        this.imageService = imageService;
        this.authService = authService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ResponseToFront> getOneUserFromServerByID(@PathVariable("id") int id) {
        UserModel user = userCRUDService.getByID(id);
        UserDTO userDTO = convertToUserDTO(user);
        UserResponseDTO response = UserResponseDTO.FromUserDTO(userDTO);

        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<ResponseToFront> updateUser(@RequestBody @Valid UserDTO userDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()
                || userDTO.equals(new UserDTO())) {
            throw new NotValidException("Profile data not valid.");
        }

        int id = userDTO.getUserID();

        UserModel newUser = convertToUser(userDTO);

        userCRUDService.update(id, newUser);

        UserResponseDTO response = getUserResponseDTOByID(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/status/{id}")
    public ResponseEntity<ResponseToFront> getUserStatusFromServerByID(@PathVariable("id") int id) {
        String status = statusService.getStatus(id);
        ProfileStatusResponse response = ProfileStatusResponse.FromStatus(status);

        return ResponseEntity.ok(response);
    }

    @PutMapping(path = "/status/{id}")
    public ResponseEntity<ResponseToFront> updateUserStatus(@PathVariable("id") int id, @RequestBody @Valid ProfileStatusDTO profileStatus, BindingResult bindingResult) {

        if (bindingResult.hasErrors()
                || profileStatus.equals(new ProfileStatusDTO())) {
            throw new NotValidException("Status not valid.");
        }

        statusService.updateStatus(id, profileStatus.getStatus());

        UserResponseDTO response = getUserResponseDTOByID(id);

        return ResponseEntity.ok(response);
    }

    @PutMapping(path = "/photo")
    public ResponseEntity<ResponseToFront> updateUserPhoto(
            @RequestParam("id") Integer userID,
            @RequestParam("file") MultipartFile photo) {

        if (photo == null || photo.isEmpty()) {
            return ResponseEntity.ok(ResponseToFront.FromExceptionMessage("Фото не найдено."));
        }

        UserModel user = userCRUDService.getByID(userID);

        String imgURL = imageService.savePhotoAndGetURL(user, photo);

        if (imgURL.equals("")) {
            return ResponseEntity.ok(ResponseToFront.FromExceptionMessage("Ошибка сохранения изображения."));
        }

        return ResponseEntity.ok(ProfilePhotoURLResponse.FromImgURL(imgURL));
    }

    private UserResponseDTO getUserResponseDTOByID(int userID) {
        UserModel updatedUser = userCRUDService.getByID(userID);
        UserDTO responseUserDTO = convertToUserDTO(updatedUser);

        return UserResponseDTO.FromUserDTO(responseUserDTO);
    }

    // Конвертация из DTO в модели
    private UserModel convertToUser(UserDTO userDTO) {
        UserModel userFromDatabase = userCRUDService.getByID(userDTO.getUserID());
        // Автоматически конвертируем поля совпадающие по названию геттеров/сеттеров
        UserModel user = modelMapper.map(userDTO, UserModel.class);

        user.setFacebook(userDTO.getContacts().getFacebook());
        user.setWebsite(userDTO.getContacts().getWebsite());
        user.setVk(userDTO.getContacts().getVk());
        user.setTwitter(userDTO.getContacts().getTwitter());
        user.setInstagram(userDTO.getContacts().getInstagram());
        user.setYoutube(userDTO.getContacts().getYoutube());
        user.setGithub(userDTO.getContacts().getGithub());
        user.setMainlink(userDTO.getContacts().getMainlink());

        user.setSecuritySettings(userFromDatabase.getSecuritySettings());
        user.setFollowedUsers(userFromDatabase.getFollowedUsers());
        user.setUsersWhoFollowedMe(userFromDatabase.getUsersWhoFollowedMe());
        user.setRefreshTokens(userFromDatabase.getRefreshTokens());

        return user;
    }

    // Конвертация из модели в DTO
    private UserDTO convertToUserDTO(UserModel user) {
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        if (authService.isUserLogin()) {
            Set<UserModel> followedUsers = authService.getCurrentLoginUser().getFollowedUsers();
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

    @ExceptionHandler
    private ResponseEntity<ResponseToFront> handleAlreadyCreatedException(UserAlreadyCreatedException exception) {
        ResponseToFront personErrorResponse = ResponseToFront.FromMessageAndResultCode("Person is already exist:" + exception.getMessage(), ResponseToFront.Code.EXCEPTION.getCode());

        return ResponseEntity.ok(personErrorResponse);
    }
}
