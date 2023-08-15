package ru.beetlerat.socialnetwork.dto.user.full;

import java.util.LinkedList;
import java.util.List;

public class UsersResponseDTO {
    private List<UserDTO> users;
    private long totalUsers;
    private String errors;

    public UsersResponseDTO(){
        users = new LinkedList<UserDTO>();
        totalUsers=0;
    }
    private UsersResponseDTO(List<UserDTO> users, long totalUsers, String errors) {
        this.users = users;
        this.totalUsers = totalUsers;
        this.errors = errors;
    }

    public static UsersResponseDTO FromUsersTotalUsersAndErrors(List<UserDTO> users, long totalUsers, String errors){
        return new UsersResponseDTO( users, totalUsers, errors);
    }

    public void setUsers(List<UserDTO> users) {
        this.users = users;
    }

    public void setTotalUsers(long totalUsers) {
        this.totalUsers = totalUsers;
    }

    public void setErrors(String errors) {
        this.errors = errors;
    }

    public List<UserDTO> getUsers() {
        return users;
    }

    public long getTotalUsers() {
        return totalUsers;
    }

    public String getErrors() {
        return errors;
    }
}
