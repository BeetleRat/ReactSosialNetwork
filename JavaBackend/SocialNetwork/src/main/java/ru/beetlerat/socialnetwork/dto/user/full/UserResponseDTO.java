package ru.beetlerat.socialnetwork.dto.user.full;

import ru.beetlerat.socialnetwork.dto.ResponseToFront;

public class UserResponseDTO extends ResponseToFront {
    private UserDTO user;

    public UserResponseDTO() {

    }

    private UserResponseDTO(UserDTO user) {
        super("", Code.AUTHORIZED_AND_COMPLETED.getCode());
        this.user = user;
    }

    public static UserResponseDTO FromUserDTO(UserDTO user) {
        return new UserResponseDTO(user);
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

}
