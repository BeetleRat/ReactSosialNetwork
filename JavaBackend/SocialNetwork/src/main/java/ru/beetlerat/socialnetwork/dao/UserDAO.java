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
import ru.beetlerat.socialnetwork.models.User;
import ru.beetlerat.socialnetwork.security.models.SecurityUserModel;
import ru.beetlerat.socialnetwork.security.types.SecurityUserDetails;
import ru.beetlerat.socialnetwork.security.types.UserRoles;
import ru.beetlerat.socialnetwork.utill.exceptions.user.NoLoginUserException;
import ru.beetlerat.socialnetwork.utill.exceptions.user.UserAlreadyCreatedException;
import ru.beetlerat.socialnetwork.utill.exceptions.user.UserNotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@Transactional(readOnly = true)
public class UserDAO {
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

    public long getUsersCount() {
        List<Long> answer = entityManager.createQuery("select count (distinct u.userID) from User as u").getResultList();
        return answer.get(0);
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageSize() {
        return pageSize;
    }

    public List<User> findAll() {
        String HQL = "select u from User as u";
        if (isUserLogin()) {
            HQL += " where u.id != " + getCurrentLoginUser().getUserID();
        }
        return entityManager.createQuery(HQL, User.class).getResultList();
    }

    public List<User> getPaginationData(int page, int count) {
        int startIndex = page * pageSize;
        int endIndex = startIndex + Math.max(1, Math.min(maxCount, count));

        List<User> userList = findAll();
        int maxIndex = userList.size();

        if (startIndex >= maxIndex) {
            throw new UserNotFoundException();
        }

        return userList.subList(startIndex, Math.min(endIndex, maxIndex));
    }

    public Set<User> getUsersByIds(Set<Integer> ids) {
        String HQL = "select u.userID from User as u where ";
        for (Integer id : ids) {
            HQL += "u.userID = " + id + " or ";
        }
        HQL = HQL.substring(0, HQL.length() - 4);
        List<User> users = entityManager.createQuery(HQL, User.class).getResultList();

        if (users == null) {
            return null;
        }

        return new HashSet<>(users);
    }

    public Set<Integer> getIdsFollowedUsers(User user) {
        Set<User> followedUsers = user.getFollowedUsers();
        Set<Integer> ids = new HashSet<>();
        for (User followedUser : followedUsers) {
            ids.add(followedUser.getUserID());
        }

        return ids;
    }

    public Set<Integer> getIdsUsersWhoFollowedMe(User user) {
        Set<User> followedUsers = user.getUsersWhoFollowedMe();
        Set<Integer> ids = new HashSet<>();
        for (User followedUser : followedUsers) {
            ids.add(followedUser.getUserID());
        }

        return ids;
    }

    public User getByID(int id) {
        User user = entityManager.find(User.class, id);
        if (user == null) {
            throw new UserNotFoundException();
        }

        return user;
    }

    public User getByUsername(String username) {
        String HQL = "select u from User as u where u.securitySettings.username = '" + username + "'";
        List<User> users = entityManager.createQuery(HQL).getResultList();
        if (users.isEmpty()) {
            throw new UserNotFoundException();
        }

        return users.get(0);
    }

    @Transactional
    public void subscribeLoginUserToUserByID(int userID) {
        User loginUser = getCurrentLoginUser();
        User followedUser = getByID(userID);

        loginUser.getFollowedUsers().add(followedUser);

        entityManager.merge(loginUser);
    }

    @Transactional
    public void unsubscribeLoginUserToUserByID(int userID) {
        User loginUser = getCurrentLoginUser();
        User unfollowedUser = getByID(userID);

        loginUser.removeFollowedUsers(unfollowedUser);
        entityManager.merge(loginUser);
    }

    @Transactional
    public void subscribeUserToUserByID(int userID, int followedUserID) {
        User loginUser = getByID(userID);
        User followedUser = getByID(followedUserID);

        loginUser.getFollowedUsers().add(followedUser);

        entityManager.merge(loginUser);
    }

    @Transactional
    public void unsubscribeUserToUserByID(int userID, int unfollowedUserID) {
        User loginUser = getByID(userID);
        User unfollowedUser = getByID(unfollowedUserID);

        loginUser.removeFollowedUsers(unfollowedUser);

        entityManager.merge(loginUser);
    }

    public User getCurrentLoginUser() {
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
    public void save(User newUser, String username, String password, UserRoles role) {
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
        User deletedUser = entityManager.find(User.class, id);
        if (deletedUser == null) {
            // Если не получилось найти по данному id
            throw new UserNotFoundException();
        } else {
            entityManager.remove(deletedUser);
        }
    }

    @Transactional
    public void update(int id, User updatedUser, String username, String password, UserRoles role) {
        User oldUser = entityManager.find(User.class, id);
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
    public void update(int id, User updatedUser, String username, String password) {
        User oldUser = entityManager.find(User.class, id);
        if (oldUser == null) {
            // Если не получилось найти по данному id
            throw new UserNotFoundException();
        } else {
            update(id, updatedUser, username, password, oldUser.getSecuritySettings().getUserRole());
        }
    }

    @Transactional
    public void update(int id, User updatedUser, String username) {
        User oldUser = entityManager.find(User.class, id);
        if (oldUser == null) {
            // Если не получилось найти по данному id
            throw new UserNotFoundException();
        } else {
            update(id, updatedUser, username, oldUser.getSecuritySettings().getPassword());
        }
    }

    @Transactional
    public void update(int id, User updatedUser) {
        User oldUser = entityManager.find(User.class, id);
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
