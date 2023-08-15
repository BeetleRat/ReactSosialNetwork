package ru.beetlerat.socialnetwork.services.users;

import ru.beetlerat.socialnetwork.models.User;
import ru.beetlerat.socialnetwork.security.types.UserRoles;

public interface UsersCRUDService extends FindUserService {

    void save(User newUser, String username, String password, UserRoles role);

    void update(int id, User updatedUser);

    void delete(int id);
}
