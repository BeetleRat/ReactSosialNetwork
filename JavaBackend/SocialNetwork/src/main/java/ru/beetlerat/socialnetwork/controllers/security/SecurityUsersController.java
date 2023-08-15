package ru.beetlerat.socialnetwork.controllers.security;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.beetlerat.socialnetwork.dto.user.authorized.ResponseToFront;
import ru.beetlerat.socialnetwork.dto.user.full.FullUserInfoDTO;
import ru.beetlerat.socialnetwork.models.User;
import ru.beetlerat.socialnetwork.security.types.UserRoles;
import ru.beetlerat.socialnetwork.services.users.UserFollowService;
import ru.beetlerat.socialnetwork.services.users.UserListService;
import ru.beetlerat.socialnetwork.services.users.UsersCRUDService;
import ru.beetlerat.socialnetwork.utill.exceptions.user.UserAlreadyCreatedException;
import ru.beetlerat.socialnetwork.utill.exceptions.user.UserErrorResponse;
import ru.beetlerat.socialnetwork.utill.exceptions.user.UserNotFoundException;
import ru.beetlerat.socialnetwork.utill.exceptions.NotValidException;

@Controller
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/security/users")
public class SecurityUsersController {

    private final UserListService userListService;
    private final UsersCRUDService usersCRUDService;
    private final UserFollowService userFollowService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SecurityUsersController(UserListService userListService, UsersCRUDService usersCRUDService, UserFollowService userFollowService, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userListService = userListService;
        this.usersCRUDService = usersCRUDService;
        this.userFollowService = userFollowService;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/create")
    public String ShowHTML(Model model) {
        // Упаковка в модель списка БД с ключем Books
        model.addAttribute("Users", userListService.getList());
        model.addAttribute("User", new FullUserInfoDTO());
        return "security/create";
    }

    @GetMapping("/show")
    public String ShowList(Model model) {
        model.addAttribute("Users", userListService.getList());
        model.addAttribute("OpenPasswords",
                userListService.getList().stream().map((user) -> {
                    return passwordEncoder.matches("rootroot", user.getSecuritySettings().getPassword());
                }).toList()
        );

        return "security/show";
    }

    @PostMapping("/create")
    public String CreateUser(@ModelAttribute("User") FullUserInfoDTO user) {

        usersCRUDService.save(convertToUser(user), user.getUsername(), user.getPassword(), UserRoles.USER);
        return "redirect:/security/users/create";
    }

    @GetMapping("/login")
    public String login() {
        return "security/login";
    }

    // Конвертация из DTO в модели
    private User convertToUser(FullUserInfoDTO fullUserInfoDTO) {
        // Автоматически конвертируем поля совпадающие по названию геттеров/сеттеров
        User user = modelMapper.map(fullUserInfoDTO, User.class);

        // Вручную конвертируем дополнительные поля
        if (fullUserInfoDTO.getFollowedUsersIds() != null) {
            user.setFollowedUsers(usersCRUDService.getUsersByIds(fullUserInfoDTO.getFollowedUsersIds()));
        }
        if (fullUserInfoDTO.getUsersWhoFollowedMeID() != null) {
            user.setUsersWhoFollowedMe(usersCRUDService.getUsersByIds(fullUserInfoDTO.getUsersWhoFollowedMeID()));
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

    // Конвертация из модели в DTO
    private FullUserInfoDTO convertToUserDTO(User user) {
        // Автоматически конвертируем поля совпадающие по названию геттеров/сеттеров
        FullUserInfoDTO fullUserInfoDTO = modelMapper.map(user, FullUserInfoDTO.class);

        // Вручную конвертируем дополнительные поля
        fullUserInfoDTO.setUserRole(user.getSecuritySettings().getUserRole());
        fullUserInfoDTO.setPassword(user.getSecuritySettings().getPassword());
        fullUserInfoDTO.setUsername(user.getSecuritySettings().getUsername());
        fullUserInfoDTO.setFollowedUsersIds(userFollowService.getIdsFollowedUsers(user));
        fullUserInfoDTO.setUsersWhoFollowedMeID(userFollowService.getIdsUsersWhoFollowedMe(user));

        return fullUserInfoDTO;
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
}
