package ru.beetlerat.socialnetwork.dto.follow;

import javax.validation.constraints.Min;

public class FollowDTO {
    @Min(value = 1, message = "User ID should be bigger then 0")
    private int userID;
    @Min(value = 1, message = "Followed User ID should be bigger then 0")
    private int followedUserID;
    private boolean isFollow;

    public FollowDTO() {

    }

    public FollowDTO(int userID, int followedUserID, boolean isFollow) {
        this.userID = userID;
        this.followedUserID = followedUserID;
        this.isFollow = isFollow;
    }

    public int getUserID() {
        return userID;
    }

    public int getFollowedUserID() {
        return followedUserID;
    }

    public boolean isFollow() {
        return isFollow;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setFollowedUserID(int followedUserID) {
        this.followedUserID = followedUserID;
    }

    public void setFollow(boolean follow) {
        isFollow = follow;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FollowDTO followDTO = (FollowDTO) o;

        if (userID != followDTO.userID) return false;
        if (followedUserID != followDTO.followedUserID) return false;
        return isFollow == followDTO.isFollow;
    }

    @Override
    public int hashCode() {
        int result = userID;
        result = 31 * result + followedUserID;
        result = 31 * result + (isFollow ? 1 : 0);
        return result;
    }
}
