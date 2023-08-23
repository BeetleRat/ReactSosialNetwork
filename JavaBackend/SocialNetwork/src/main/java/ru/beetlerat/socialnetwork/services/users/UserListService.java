package ru.beetlerat.socialnetwork.services.users;

import ru.beetlerat.socialnetwork.models.User;

import java.util.List;


public interface UserListService {
    long getUsersCount();

    List<User> getList();

    List<User> getPaginationData(int page, int count);

    void setPageSize(int pageSize);

    int getDefaultPageSize();
}
