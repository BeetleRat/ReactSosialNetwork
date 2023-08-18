package ru.beetlerat.socialnetwork.services.users;

import ru.beetlerat.socialnetwork.models.User;

import java.util.Set;

public interface UserFollowService {

    Set<Integer> getIdsFollowedUsers(User user);

    Set<Integer> getIdsUsersWhoFollowedMe(User user);

    void subscribeLoginUserToUserByID(int userID);


    void unsubscribeLoginUserToUserByID(int userID);

    void subscribeUserToUserByID(int userID, int followedUserID);

    void unsubscribeUserToUserByID(int userID, int unfollowedUserID);
}
