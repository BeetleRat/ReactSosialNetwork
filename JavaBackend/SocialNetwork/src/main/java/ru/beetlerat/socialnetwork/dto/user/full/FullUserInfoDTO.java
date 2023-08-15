package ru.beetlerat.socialnetwork.dto.user.full;

import ru.beetlerat.socialnetwork.security.types.UserRoles;

import java.util.Set;

public class FullUserInfoDTO extends UserDTO {
    private String username;
    private String password;
    private UserRoles userRole;
    private Set<Integer> followedUsersIds;
    private Set<Integer> usersWhoFollowedMeID;

    public FullUserInfoDTO() {
        this.contacts=new Contacts();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public UserRoles getUserRole() {
        return userRole;
    }

    public Set<Integer> getFollowedUsersIds() {
        return followedUsersIds;
    }

    public Set<Integer> getUsersWhoFollowedMeID() {
        return usersWhoFollowedMeID;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserRole(UserRoles userRole) {
        this.userRole = userRole;
    }

    public void setFollowedUsersIds(Set<Integer> followedUsersIds) {
        this.followedUsersIds = followedUsersIds;
    }

    public void setUsersWhoFollowedMeID(Set<Integer> usersWhoFollowedMeID) {
        this.usersWhoFollowedMeID = usersWhoFollowedMeID;
    }
}
