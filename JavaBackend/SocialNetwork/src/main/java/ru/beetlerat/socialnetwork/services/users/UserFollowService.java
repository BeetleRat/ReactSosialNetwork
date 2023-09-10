package ru.beetlerat.socialnetwork.services.users;

import ru.beetlerat.socialnetwork.models.UserModel;

import java.util.Set;

public interface UserFollowService {

    Set<Integer> getIdsFollowedUsers(UserModel user);

    Set<Integer> getIdsUsersWhoFollowedMe(UserModel user);

    void subscribeLoginUserToUserByID(int userID);


    void unsubscribeLoginUserToUserByID(int userID);

    void subscribeUserToUserByID(int userID, int followedUserID);

    void unsubscribeUserToUserByID(int userID, int unfollowedUserID);
}
