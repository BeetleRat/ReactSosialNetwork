package ru.beetlerat.socialnetwork.security.models;

import ru.beetlerat.socialnetwork.security.types.UserPermissions;

import javax.persistence.*;

@Entity
@Table(name = "security_user_permissions")
public class SecurityUserPermissionModel {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "permission")
    @Enumerated(EnumType.STRING)
    private UserPermissions userPermission;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private SecurityUserModel securityUserModel;

    public SecurityUserPermissionModel() {
    }

    public SecurityUserPermissionModel(UserPermissions userPermission, SecurityUserModel securityUserModel) {
        this.userPermission = userPermission;
        this.securityUserModel = securityUserModel;
    }

    // Геттеры
    public int getId() {
        return id;
    }

    public UserPermissions getUserPermission() {
        return userPermission;
    }

    public SecurityUserModel getSecurityUserModel() {
        return securityUserModel;
    }

    // Сеттеры
    public void setId(int id) {
        this.id = id;
    }

    public void setUserPermission(UserPermissions userPermission) {
        this.userPermission = userPermission;
    }

    public void setSecurityUserModel(SecurityUserModel securityUserModel) {
        this.securityUserModel = securityUserModel;
    }
}