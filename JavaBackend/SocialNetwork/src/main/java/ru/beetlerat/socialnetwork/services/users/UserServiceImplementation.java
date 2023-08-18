package ru.beetlerat.socialnetwork.services.users;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import ru.beetlerat.socialnetwork.dao.UserDAO;
import ru.beetlerat.socialnetwork.models.User;
import ru.beetlerat.socialnetwork.security.types.UserRoles;

import java.util.List;
import java.util.Set;

@Service
public class UserServiceImplementation implements UserListService, UserFollowService, AuthUserService, UsersCRUDService, StatusService {
    private final UserDAO userDAO;

    @Autowired
    public UserServiceImplementation(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public long getUsersCount() {
        return this.userDAO.getUsersCount();
    }

    @Override
    public List<User> getList() {
        return userDAO.findAll();
    }

    @Override
    public List<User> getPaginationData(int page, int count) {
        return userDAO.getPaginationData(page, count);
    }

    @Override
    public void setPageSize(int pageSize) {
        userDAO.setPageSize(pageSize);
    }

    @Override
    public Set<User> getUsersByIds(Set<Integer> ids) {
        return userDAO.getUsersByIds(ids);
    }

    @Override
    public User getByID(int id) {
        return userDAO.getByID(id);
    }

    @Override
    public User getByUsername(String username) {
        return userDAO.getByUsername(username);
    }

    @Override
    public void save(User newUser, String username, String password, UserRoles role) {
        userDAO.save(newUser, username, password, role);
    }

    @Override
    public void update(int id, User updatedUser) {
        userDAO.update(id, updatedUser);
    }

    @Override
    public void delete(int id) {
        userDAO.delete(id);
    }


    @Override
    public Set<Integer> getIdsFollowedUsers(User user) {
        return userDAO.getIdsFollowedUsers(user);
    }

    @Override
    public Set<Integer> getIdsUsersWhoFollowedMe(User user) {
        return userDAO.getIdsUsersWhoFollowedMe(user);
    }

    @Override
    public void subscribeLoginUserToUserByID(int userID) {
        userDAO.subscribeLoginUserToUserByID(userID);
    }


    @Override
    public void unsubscribeLoginUserToUserByID(int userID) {
        userDAO.unsubscribeLoginUserToUserByID(userID);
    }

    @Override
    public void subscribeUserToUserByID(int userID, int followedUserID) {
        userDAO.subscribeUserToUserByID(userID, followedUserID);
    }

    @Override
    public void unsubscribeUserToUserByID(int userID, int unfollowedUserID) {
        userDAO.unsubscribeUserToUserByID(userID, unfollowedUserID);
    }

    @Override
    public User getCurrentLoginUser() {
        return userDAO.getCurrentLoginUser();
    }

    @Override
    public void setAuthentication(UsernamePasswordAuthenticationToken token) throws BadCredentialsException {
        userDAO.setAuthentication(token);
    }

    @Override
    public boolean isUserLogin() {
        return userDAO.isUserLogin();
    }

    @Override
    public void logout() {
        userDAO.logoutUser();
    }

    @Override
    public String getStatus(int userId) {
        return getByID(userId).getStatus();
    }

    @Override
    public void updateStatus(int userId, String newStatus) {
        User user = getByID(userId);
        user.setStatus(newStatus);

        userDAO.update(user.getUserID(), user);
    }
}
