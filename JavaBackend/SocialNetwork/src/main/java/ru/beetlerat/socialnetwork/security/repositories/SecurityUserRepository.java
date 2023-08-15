package ru.beetlerat.socialnetwork.security.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.beetlerat.socialnetwork.security.models.SecurityUserModel;

import java.util.Optional;

@Repository
public interface SecurityUserRepository extends JpaRepository<SecurityUserModel,Integer> {
    // Поиск кользователя по Username
    Optional<SecurityUserModel> findByUsername(String username);
}
