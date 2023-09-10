package ru.beetlerat.socialnetwork.dto.users;

import ru.beetlerat.socialnetwork.models.UserModel;

import java.util.ArrayList;
import java.util.List;

public class PaginatedUsersListFromDB {
    private List<UserModel> pageUserList;
    private int totalUsers;

    public PaginatedUsersListFromDB() {
        pageUserList = new ArrayList<>();
    }

    private PaginatedUsersListFromDB(List<UserModel> pageUserList, int totalUsers) {
        this.pageUserList = pageUserList;
        this.totalUsers = totalUsers;
    }

    public static PaginatedUsersListFromDB FromUserListAndTotalUsers(List<UserModel> pageUserList, int totalUsers) {
        return new PaginatedUsersListFromDB(pageUserList, totalUsers);
    }

    public List<UserModel> getPageUserList() {
        return pageUserList;
    }

    public int getTotalUsers() {
        return totalUsers;
    }

    public void setPageUserList(List<UserModel> pageUserList) {
        this.pageUserList = pageUserList;
    }

    public void setTotalUsers(int totalUsers) {
        this.totalUsers = totalUsers;
    }
}
