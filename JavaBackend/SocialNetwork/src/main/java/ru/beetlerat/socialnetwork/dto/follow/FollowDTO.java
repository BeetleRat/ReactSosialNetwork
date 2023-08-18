package ru.beetlerat.socialnetwork.dto.follow;

public class FollowDTO {
    private int userID;
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
}
