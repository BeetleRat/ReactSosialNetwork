package ru.beetlerat.socialnetwork.services.users;

import ru.beetlerat.socialnetwork.models.UserModel;
import ru.beetlerat.socialnetwork.security.types.UserRoles;

public interface UsersCRUDService extends FindUserService {

    void save(UserModel newUser, String username, String password, UserRoles role);

    void update(int id, UserModel updatedUser);

    void delete(int id);
}
