package ru.beetlerat.socialnetwork.dto.dbrequest;

import ru.beetlerat.socialnetwork.models.UserModel;

import java.util.Optional;

public class PaginationUserRequestDTO extends PaginationRequestDTO {
    private Optional<UserModel> requestedUser;

    public PaginationUserRequestDTO() {
        requestedUser = Optional.empty();
    }

    private PaginationUserRequestDTO(Optional<UserModel> requestedUser) {
        this.requestedUser = requestedUser;
    }

    public static PaginationUserRequestDTO FromRequestedUser(Optional<UserModel> requestedUser) {
        return new PaginationUserRequestDTO(requestedUser);
    }

    public Optional<UserModel> getRequestedUser() {
        return requestedUser;
    }


    public void setRequestedUser(Optional<UserModel> requestedUser) {
        this.requestedUser = requestedUser;
    }
}
