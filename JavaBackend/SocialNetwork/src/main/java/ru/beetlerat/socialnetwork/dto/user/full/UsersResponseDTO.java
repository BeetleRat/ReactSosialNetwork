package ru.beetlerat.socialnetwork.dto.user.full;

import ru.beetlerat.socialnetwork.dto.ResponseToFront;

import java.util.LinkedList;
import java.util.List;

public class UsersResponseDTO extends ResponseToFront {
    private List<UserDTO> users;
    private long totalUsers;

    public UsersResponseDTO() {
        users = new LinkedList<UserDTO>();
        totalUsers = 0;
    }

    private UsersResponseDTO(List<UserDTO> users, long totalUsers) {
        super("", Code.AUTHORIZED_AND_COMPLETED.getCode());
        this.users = users;
        this.totalUsers = totalUsers;
    }

    public static UsersResponseDTO FromUsersAndTotalUsers(List<UserDTO> users, long totalUsers) {
        return new UsersResponseDTO(users, totalUsers);
    }

    public void setUsers(List<UserDTO> users) {
        this.users = users;
    }

    public void setTotalUsers(long totalUsers) {
        this.totalUsers = totalUsers;
    }

    public List<UserDTO> getUsers() {
        return users;
    }

    public long getTotalUsers() {
        return totalUsers;
    }
}
