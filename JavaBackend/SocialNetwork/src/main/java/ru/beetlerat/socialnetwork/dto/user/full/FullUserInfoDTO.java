package ru.beetlerat.socialnetwork.dto.user.full;

import ru.beetlerat.socialnetwork.security.types.UserRoles;

import javax.validation.constraints.NotNull;
import java.util.Set;

public class FullUserInfoDTO extends UserDTO {
    @NotNull(message = "Username should not be empty")
    private String username;
    @NotNull(message = "Password should not be empty")
    private String password;
    private UserRoles userRole;
    private Set<Integer> followedUsersIds;
    private Set<Integer> usersWhoFollowedMeID;

    public FullUserInfoDTO() {
        this.contacts = new Contacts();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        FullUserInfoDTO that = (FullUserInfoDTO) o;

        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (userRole != that.userRole) return false;
        if (followedUsersIds != null ? !followedUsersIds.equals(that.followedUsersIds) : that.followedUsersIds != null)
            return false;
        return usersWhoFollowedMeID != null ? usersWhoFollowedMeID.equals(that.usersWhoFollowedMeID) : that.usersWhoFollowedMeID == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (userRole != null ? userRole.hashCode() : 0);
        result = 31 * result + (followedUsersIds != null ? followedUsersIds.hashCode() : 0);
        result = 31 * result + (usersWhoFollowedMeID != null ? usersWhoFollowedMeID.hashCode() : 0);
        return result;
    }
}
