package ru.beetlerat.socialnetwork.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.beetlerat.socialnetwork.dto.dbrequest.PaginationUserRequestDTO;
import ru.beetlerat.socialnetwork.dto.users.PaginatedUsersListFromDB;
import ru.beetlerat.socialnetwork.models.UserModel;
import ru.beetlerat.socialnetwork.security.models.SecurityUserModel;
import ru.beetlerat.socialnetwork.security.types.SecurityUserDetails;
import ru.beetlerat.socialnetwork.security.types.UserRoles;
import ru.beetlerat.socialnetwork.utill.exceptions.NotValidException;
import ru.beetlerat.socialnetwork.utill.exceptions.user.NoLoginUserException;
import ru.beetlerat.socialnetwork.utill.exceptions.user.UserAlreadyCreatedException;
import ru.beetlerat.socialnetwork.utill.exceptions.user.UserNotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

@Component
@Transactional(readOnly = true)
public class UserDAO {
    private final int DEFAULT_PAGE_SIZE = 5;
    @PersistenceContext
    private EntityManager entityManager;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private int pageSize;
    private int maxCount;

    @Autowired
    public UserDAO(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        pageSize = 5;
        maxCount = 100;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Long getTotalUsersCount() {
        String HQL = "select count (distinct u.userID) from UserModel as u";
        Long answer = (Long) entityManager.createQuery(HQL).getSingleResult();

        return answer;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getDefaultPageSize() {
        return DEFAULT_PAGE_SIZE;
    }

    public void setPageSizeAsDefault() {
        pageSize = DEFAULT_PAGE_SIZE;
    }

    private List<UserModel> findAll() {
        String HQL = "select u from UserModel as u";
        if (isUserLogin()) {
            HQL += " where u.id != " + getCurrentLoginUser().getUserID();
        }

        return entityManager.createQuery(HQL, UserModel.class).getResultList();
    }

    private List<UserModel> findUsersWhoseNameContains(String partOfUsersName) {
        String HQL = "select u from UserModel as u where u.securitySettings.username like '%" + partOfUsersName + "%'";
        if (isUserLogin()) {
            HQL += " and u.id != " + getCurrentLoginUser().getUserID();
        }

        return entityManager.createQuery(HQL, UserModel.class).getResultList();
    }

    public PaginatedUsersListFromDB getFriendPaginationData(PaginationUserRequestDTO paginationUserRequestDTO) {

        return getFriendPaginationData(paginationUserRequestDTO, "");
    }

    public PaginatedUsersListFromDB getFriendPaginationData(PaginationUserRequestDTO paginationUserRequestDTO, String partOfUsersName) {
        if (paginationUserRequestDTO.getRequestedUser().isEmpty()) {
            throw new NotValidException("Не указан пользователь друзей которого необходимо искать.");
        }

        UserModel requestedUser = paginationUserRequestDTO.getRequestedUser().get();

        List<UserModel> friends = requestedUser.getFollowedUsers().stream().filter(user -> user.getSecuritySettings().getUsername().contains(partOfUsersName)).toList();

        List<UserModel> pageUsersList = getPageSublist(paginationUserRequestDTO, friends);

        return PaginatedUsersListFromDB.FromUserListAndTotalUsers(pageUsersList, friends.size());
    }

    public PaginatedUsersListFromDB getNotFriendPaginationData(PaginationUserRequestDTO paginationUserRequestDTO) {

        return getNotFriendPaginationData(paginationUserRequestDTO, "");
    }

    public PaginatedUsersListFromDB getNotFriendPaginationData(PaginationUserRequestDTO paginationUserRequestDTO, String partOfUsersName) {
        if (paginationUserRequestDTO.getRequestedUser().isEmpty()) {
            throw new NotValidException("Не указан пользователь не друзей которого необходимо искать.");
        }

        UserModel requestedUser = paginationUserRequestDTO.getRequestedUser().get();

        return getPaginationData(
                paginationUserRequestDTO, partOfUsersName,
                (users -> users.stream().filter(user -> !requestedUser.getFollowedUsers().contains(user)).toList())
        );
    }

    public PaginatedUsersListFromDB getPaginationData(PaginationUserRequestDTO paginationUserRequestDTO) {

        return getPaginationData(paginationUserRequestDTO, "");
    }

    public PaginatedUsersListFromDB getPaginationData(PaginationUserRequestDTO paginationUserRequestDTO, String partOfUsersName) {

        return getPaginationData(paginationUserRequestDTO, partOfUsersName, (users -> users));
    }

    private PaginatedUsersListFromDB getPaginationData(PaginationUserRequestDTO paginationUserRequestDTO, String partOfUsersName, Function<List<UserModel>, List<UserModel>> filterFunction) {
        List<UserModel> userList;
        if (partOfUsersName.isEmpty()) {
            userList = findAll();
        } else {
            userList = findUsersWhoseNameContains(partOfUsersName);
        }

        userList = filterFunction.apply(userList);

        List<UserModel> pageUsersList = getPageSublist(paginationUserRequestDTO, userList);

        return PaginatedUsersListFromDB.FromUserListAndTotalUsers(pageUsersList, userList.size());
    }

    private List<UserModel> getPageSublist(PaginationUserRequestDTO paginationUserRequestDTO, List<UserModel> users) {
        int startIndex = paginationUserRequestDTO.getPage() * pageSize;
        int endIndex = startIndex + Math.max(1, Math.min(maxCount, paginationUserRequestDTO.getCount()));

        int maxIndex = users.size();

        if (startIndex >= maxIndex) {
            throw new UserNotFoundException();
        }

        return users.subList(startIndex, Math.min(endIndex, maxIndex));
    }

    public Set<UserModel> getUsersByIds(Set<Integer> ids) {
        String HQL = "select u.userID from UserModel as u where ";
        for (Integer id : ids) {
            HQL += "u.userID = " + id + " or ";
        }
        HQL = HQL.substring(0, HQL.length() - 4);
        List<UserModel> users = entityManager.createQuery(HQL, UserModel.class).getResultList();

        if (users == null) {
            return null;
        }

        return new HashSet<>(users);
    }

    public Set<Integer> getIdsFollowedUsers(UserModel user) {
        Set<UserModel> followedUsers = user.getFollowedUsers();
        Set<Integer> ids = new HashSet<>();
        for (UserModel followedUser : followedUsers) {
            ids.add(followedUser.getUserID());
        }

        return ids;
    }

    public Set<Integer> getIdsUsersWhoFollowedMe(UserModel user) {
        Set<UserModel> followedUsers = user.getUsersWhoFollowedMe();
        Set<Integer> ids = new HashSet<>();
        for (UserModel followedUser : followedUsers) {
            ids.add(followedUser.getUserID());
        }

        return ids;
    }

    public UserModel getByID(int id) {
        UserModel user = entityManager.find(UserModel.class, id);
        if (user == null) {
            throw new UserNotFoundException();
        }

        return user;
    }

    public UserModel getByUsername(String username) {
        String HQL = "select u from UserModel as u where u.securitySettings.username = '" + username + "'";
        List<UserModel> users = entityManager.createQuery(HQL).getResultList();
        if (users.isEmpty()) {
            throw new UserNotFoundException();
        }

        return users.get(0);
    }

    @Transactional
    public void subscribeLoginUserToUserByID(int userID) {
        UserModel loginUser = getCurrentLoginUser();
        UserModel followedUser = getByID(userID);

        loginUser.getFollowedUsers().add(followedUser);

        entityManager.merge(loginUser);
    }

    @Transactional
    public void unsubscribeLoginUserToUserByID(int userID) {
        UserModel loginUser = getCurrentLoginUser();
        UserModel unfollowedUser = getByID(userID);

        loginUser.removeFollowedUsers(unfollowedUser);
        entityManager.merge(loginUser);
    }

    @Transactional
    public void subscribeUserToUserByID(int userID, int followedUserID) {
        UserModel loginUser = getByID(userID);
        UserModel followedUser = getByID(followedUserID);

        loginUser.getFollowedUsers().add(followedUser);

        entityManager.merge(loginUser);
    }

    @Transactional
    public void unsubscribeUserToUserByID(int userID, int unfollowedUserID) {
        UserModel loginUser = getByID(userID);
        UserModel unfollowedUser = getByID(unfollowedUserID);

        loginUser.removeFollowedUsers(unfollowedUser);

        entityManager.merge(loginUser);
    }

    public UserModel getCurrentLoginUser() {
        SecurityUserDetails currentUser = SecurityUserDetails.CurrentLoginUser();
        if (currentUser == null) {
            throw new NoLoginUserException();
        }

        return currentUser.getSecurityUser().getUser();
    }

    public boolean isUserLogin() {
        return SecurityUserDetails.CurrentLoginUser() != null;
    }

    public void logoutUser() {
        SecurityUserDetails.Logout();
    }

    @Transactional
    public void save(UserModel newUser, String username, String password, UserRoles role) {
        String HQL = "select u.id from SecurityUserModel as u where u.username = '" + username + "'";
        List<Integer> existsUsersID = entityManager.createQuery(HQL, Integer.class).getResultList();
        if (existsUsersID.size() > 0) {
            throw new UserAlreadyCreatedException();
        }

        SecurityUserModel securityUserModel = new SecurityUserModel();
        securityUserModel.setUser(newUser);
        securityUserModel.setUsername(username);
        securityUserModel.setPassword(passwordEncoder.encode(password));
        securityUserModel.setUserRole(role);

        newUser.setSecuritySettings(securityUserModel);

        entityManager.persist(securityUserModel);
        entityManager.persist(newUser);
    }

    @Transactional
    public void delete(int id) {
        UserModel deletedUser = entityManager.find(UserModel.class, id);
        if (deletedUser == null) {
            // Если не получилось найти по данному id
            throw new UserNotFoundException();
        } else {
            entityManager.remove(deletedUser);
        }
    }

    @Transactional
    public void update(int id, UserModel updatedUser, String username, String password, UserRoles role) {
        UserModel oldUser = entityManager.find(UserModel.class, id);
        if (oldUser == null) {
            // Если не получилось найти по данному id
            throw new UserNotFoundException();
        } else {
            updatedUser.setUserID(id);

            SecurityUserModel securityUserModel = updatedUser.getSecuritySettings();
            securityUserModel.setUsername(username);
            securityUserModel.setPassword(password);
            securityUserModel.setUserRole(role);

            oldUser.setAllFromAnotherUser(updatedUser);

            entityManager.merge(oldUser);
        }
    }

    @Transactional
    public void update(int id, UserModel updatedUser, String username, String password) {
        UserModel oldUser = entityManager.find(UserModel.class, id);
        if (oldUser == null) {
            // Если не получилось найти по данному id
            throw new UserNotFoundException();
        } else {
            update(id, updatedUser, username, password, oldUser.getSecuritySettings().getUserRole());
        }
    }

    @Transactional
    public void update(int id, UserModel updatedUser, String username) {
        UserModel oldUser = entityManager.find(UserModel.class, id);
        if (oldUser == null) {
            // Если не получилось найти по данному id
            throw new UserNotFoundException();
        } else {
            update(id, updatedUser, username, oldUser.getSecuritySettings().getPassword());
        }
    }

    @Transactional
    public void update(int id, UserModel updatedUser) {
        UserModel oldUser = entityManager.find(UserModel.class, id);
        if (oldUser == null) {
            // Если не получилось найти по данному id
            throw new UserNotFoundException();
        } else {
            update(id, updatedUser, oldUser.getSecuritySettings().getUsername());
        }
    }

    public void setAuthentication(UsernamePasswordAuthenticationToken token) throws BadCredentialsException {

        Authentication authentication =
                authenticationManager.authenticate(token);

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
