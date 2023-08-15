package ru.beetlerat.socialnetwork.security.types;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public enum UserRoles {
    // Роли.
    // Переменные ролей хранят в себе HashSet прав(разрешений) этой роли. Для удобства.
    ADMIN(new HashSet<UserPermissions>(Arrays.asList(UserPermissions.GET_PERMIT, UserPermissions.POST_PERMIT, UserPermissions.PATCH_PERMIT, UserPermissions.DELETE_PERMIT))),
    USER(new HashSet<UserPermissions>(Arrays.asList(UserPermissions.GET_PERMIT, UserPermissions.POST_PERMIT)));

    // Константа - Set прав(разрешений) роли
    private final Set<UserPermissions> permissions;

    // Консструктор роли.
    // При создании роли(создавали выше) упаковываем в нее Set прав(разрешений) этой роли
    UserRoles(Set<UserPermissions> permissions) {
        this.permissions = permissions;
    }

    // Геттер. Получить права(разрешения) данной роли.
    public Set<UserPermissions> getPermissions() {
        return permissions;
    }

    // Геттер. Получить полный список прав(разрешений) данной роли. В формате Spring Security.
    public Set<SimpleGrantedAuthority> getGrantedAuthority(){
        // Создаем Set прав(разрешений) в формате Spring Security из прав даннрй роли
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(
                        (permission)->{
                            return new SimpleGrantedAuthority(permission.getPermission());
                        }
                )
                .collect(Collectors.toSet());
        // Добавляем еще одно право(разрешение) - это право Роли. Такое право начинается с ROLE_
        permissions.add(new SimpleGrantedAuthority("ROLE_"+this.name()));
        // Возвращаем полный список прав(разрешений) данной роли. В формате Spring Security.
        return permissions;
    }
}
