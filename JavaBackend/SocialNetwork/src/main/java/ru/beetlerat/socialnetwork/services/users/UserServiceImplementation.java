package ru.beetlerat.socialnetwork.services.users;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import ru.beetlerat.socialnetwork.dao.UserDAO;
import ru.beetlerat.socialnetwork.dto.dbrequest.PaginationUserRequestDTO;
import ru.beetlerat.socialnetwork.dto.users.PaginatedUsersListFromDB;
import ru.beetlerat.socialnetwork.models.UserModel;
import ru.beetlerat.socialnetwork.security.types.UserRoles;

import java.util.Set;

@Service
public class UserServiceImplementation implements UserListService, UserFollowService, AuthUserService, UsersCRUDService, StatusService {
    private final UserDAO userDAO;

    @Autowired
    public UserServiceImplementation(UserDAO userDAO) {
        this.userDAO = userDAO;
    }


    @Override
    public Long getTotalUsersCount() {
        return userDAO.getTotalUsersCount();
    }

    @Override
    public PaginatedUsersListFromDB getList() {
        PaginationUserRequestDTO paginationUserRequestDTO = new PaginationUserRequestDTO();
        paginationUserRequestDTO.setCount(userDAO.getTotalUsersCount().intValue());
        paginationUserRequestDTO.setPage(0);

        userDAO.setPageSize(userDAO.getDefaultPageSize());

        return userDAO.getPaginationData(paginationUserRequestDTO);
    }

    @Override
    public PaginatedUsersListFromDB getFriendPaginationData(PaginationUserRequestDTO paginationUserRequestDTO) {
        return userDAO.getFriendPaginationData(paginationUserRequestDTO);
    }

    @Override
    public PaginatedUsersListFromDB getFriendPaginationData(PaginationUserRequestDTO paginationUserRequestDTO, String partOfUsersName) {
        return userDAO.getFriendPaginationData(paginationUserRequestDTO, partOfUsersName);
    }

    @Override
    public PaginatedUsersListFromDB getNotFriendPaginationData(PaginationUserRequestDTO paginationUserRequestDTO) {
        return userDAO.getNotFriendPaginationData(paginationUserRequestDTO);
    }

    @Override
    public PaginatedUsersListFromDB getNotFriendPaginationData(PaginationUserRequestDTO paginationUserRequestDTO, String partOfUsersName) {
        return userDAO.getNotFriendPaginationData(paginationUserRequestDTO, partOfUsersName);
    }

    @Override
    public PaginatedUsersListFromDB getPaginationData(PaginationUserRequestDTO paginationUserRequestDTO) {
        return userDAO.getPaginationData(paginationUserRequestDTO);
    }

    @Override
    public PaginatedUsersListFromDB getPaginationData(PaginationUserRequestDTO paginationUserRequestDTO, String partOfUsersName) {
        return userDAO.getPaginationData(paginationUserRequestDTO, partOfUsersName);
    }

    @Override
    public void setPageSize(int pageSize) {
        userDAO.setPageSize(pageSize);
    }

    @Override
    public int getDefaultPageSize() {
        return userDAO.getDefaultPageSize();
    }

    @Override
    public int getPageSize() {
        return userDAO.getPageSize();
    }

    @Override
    public Set<UserModel> getUsersByIds(Set<Integer> ids) {
        return userDAO.getUsersByIds(ids);
    }

    @Override
    public UserModel getByID(int id) {
        return userDAO.getByID(id);
    }

    @Override
    public UserModel getByUsername(String username) {
        return userDAO.getByUsername(username);
    }

    @Override
    public void save(UserModel newUser, String username, String password, UserRoles role) {
        userDAO.save(newUser, username, password, role);
    }

    @Override
    public void update(int id, UserModel updatedUser) {
        userDAO.update(id, updatedUser);
    }

    @Override
    public void delete(int id) {
        userDAO.delete(id);
    }


    @Override
    public Set<Integer> getIdsFollowedUsers(UserModel user) {
        return userDAO.getIdsFollowedUsers(user);
    }

    @Override
    public Set<Integer> getIdsUsersWhoFollowedMe(UserModel user) {
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
    public UserModel getCurrentLoginUser() {
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
        UserModel user = getByID(userId);
        user.setStatus(newStatus);

        userDAO.update(user.getUserID(), user);
    }
}
