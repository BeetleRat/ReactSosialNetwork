package ru.beetlerat.socialnetwork.security.models;

import ru.beetlerat.socialnetwork.models.User;
import ru.beetlerat.socialnetwork.security.types.UserPermissions;
import ru.beetlerat.socialnetwork.security.types.UserRoles;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "security_users")
public class SecurityUserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "username", unique = true)
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRoles userRole;
    @OneToOne(mappedBy = "securitySettings", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private User user;

    @OneToMany(mappedBy = "securityUserModel", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private List<SecurityUserPermissionModel> userPermissions;

    public SecurityUserModel() {
    }

    // Геттеры
    public int getId() {
        return id;
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
    public List<SecurityUserPermissionModel> getUserPermissions() {
        return userPermissions;
    }
    // Проверка есть ли у пользователя данное разрешение
    public boolean hasPermissions(UserPermissions permission) {
        for (SecurityUserPermissionModel permissionModel : userPermissions) {
            if (permissionModel.getUserPermission().equals(permission)) {
                return true;
            }
        }
        return false;
    }

    public User getUser() {
        return user;
    }

    // Сеттеры
    public void setId(int id) {
        this.id = id;
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
    public void setUserPermissions(List<SecurityUserPermissionModel> userPermissions) {
        this.userPermissions = userPermissions;
    }
    // Добавление разрешений пользователю
    public void addUserPermissions(UserPermissions... permissions) {
        if (this.userPermissions == null) {
            this.userPermissions = new ArrayList<>();
            for (UserPermissions onePermission : permissions) {
                this.userPermissions.add(new SecurityUserPermissionModel(onePermission, this));
            }
        } else {
            for (UserPermissions onePermission : permissions) {
                if (!hasPermissions(onePermission)) {
                    this.userPermissions.add(new SecurityUserPermissionModel(onePermission, this));
                }
            }
        }
    }
    // Удаление у пользователя разрешений
    public void removeUserPermissions(UserPermissions... permissions) {
        for (UserPermissions onePermission : permissions) {
            for (int i = 0; i < userPermissions.size(); i++) {
                if (onePermission.equals(userPermissions.get(i).getUserPermission())) {
                    userPermissions.remove(i);
                    break;
                }
            }
        }
    }
    public void setUser(User user) {
        this.user = user;
    }

    // Перегрузка методов equals и hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SecurityUserModel that = (SecurityUserModel) o;

        if (id != that.id) return false;
        if (!Objects.equals(username, that.username)) return false;
        if (!Objects.equals(password, that.password)) return false;
        return userRole == that.userRole;
    }
    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (userRole != null ? userRole.hashCode() : 0);
        return result;
    }
}
