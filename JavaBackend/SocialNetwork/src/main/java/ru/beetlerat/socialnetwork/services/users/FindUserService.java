package ru.beetlerat.socialnetwork.services.users;

import ru.beetlerat.socialnetwork.models.UserModel;

import java.util.Set;

public interface FindUserService {
    // В DTO подписки будут представлены в виде списка ID
    // потому нам нужны методы получения списка пользователей по списку ID
    // и наоборот, получения списка id пользователей подписанных на данного
    // и на которых подписан данный пользователь
    Set<UserModel> getUsersByIds(Set<Integer> ids);

    UserModel getByID(int id);

    UserModel getByUsername(String username);
}
