package ru.beetlerat.socialnetwork.services.users;

import ru.beetlerat.socialnetwork.models.User;

import java.util.List;
import java.util.Set;


public interface UserListService {
    long getUsersCount();

    List<User> getList();

    List<User> getPaginationData(int page, int count);
}
