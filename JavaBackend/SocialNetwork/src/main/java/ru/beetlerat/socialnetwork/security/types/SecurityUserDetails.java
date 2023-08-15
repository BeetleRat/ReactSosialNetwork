package ru.beetlerat.socialnetwork.security.types;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import ru.beetlerat.socialnetwork.security.models.SecurityUserModel;

import java.util.Collection;
import java.util.Set;

// Пользователь формата Spring Security
// Spring Security работает с пользователями реализующими интерфейс UserDetails
public class SecurityUserDetails implements UserDetails {
    // Метод получения пользователя залогиненного в данный момент
    public static SecurityUserDetails CurrentLoginUser() {
        boolean isAuthenticated = SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
        Object userDetails = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!isAuthenticated
        ||!(userDetails instanceof SecurityUserDetails)){
            return null;
        }

        return (SecurityUserDetails) userDetails;
    }

    public static void Logout(){
        boolean isAuthenticated = SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
        if(isAuthenticated){
            SecurityContextHolder.clearContext();
        }
    }

    private SecurityUserModel securityUser;
    // Права(разрешения) пользователя
    private Set<? extends GrantedAuthority> grantedAuthorities;
    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialsNonExpired;
    private boolean isEnabled;

    // Конструктор
    public SecurityUserDetails(SecurityUserModel securityUser) {
        this.securityUser = securityUser;
        // Получаем роль и права пользователя из его модели
        this.grantedAuthorities = this.securityUser.getUserRole().getGrantedAuthority();
        this.isAccountNonExpired = true;
        this.isAccountNonLocked = true;
        this.isCredentialsNonExpired = true;
        this.isEnabled = true;
    }

    // Геттеры(по совместительству реализация интерфейса)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return this.securityUser.getPassword();
    }

    @Override
    public String getUsername() {
        return this.securityUser.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    public SecurityUserModel getSecurityUser() {
        return securityUser;
    }
}
