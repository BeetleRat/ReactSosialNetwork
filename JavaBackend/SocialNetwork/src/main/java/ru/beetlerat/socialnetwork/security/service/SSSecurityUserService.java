package ru.beetlerat.socialnetwork.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.beetlerat.socialnetwork.security.models.SecurityUserModel;
import ru.beetlerat.socialnetwork.security.repositories.SecurityUserRepository;
import ru.beetlerat.socialnetwork.security.types.SecurityUserDetails;

import java.util.Optional;

// Сервис взаимодействия Spring Security с хранимыми пользователями
@Service
public class SSSecurityUserService implements UserDetailsService {

    private final SecurityUserRepository securityUserRepository;

    @Autowired
    public SSSecurityUserService(SecurityUserRepository securityUserRepository) {
        this.securityUserRepository = securityUserRepository;
    }

    // Метод получения пользователя по username
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<SecurityUserModel> securityUserModel = securityUserRepository.findByUsername(username);
        if (securityUserModel.isEmpty()) {
            throw new UsernameNotFoundException(String.format("User %s is not found", username));
        }

        return new SecurityUserDetails(securityUserModel.get());
    }
}
