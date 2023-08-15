package ru.beetlerat.socialnetwork.security.repositories;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.beetlerat.socialnetwork.security.models.SecurityUserModel;
import ru.beetlerat.socialnetwork.security.service.SecurityUserService;

@Service
public class SecurityUserServiceRepo implements SecurityUserService {
    private final SecurityUserRepository securityUserRepository;

    @Autowired
    public SecurityUserServiceRepo(SecurityUserRepository securityUserRepository) {
        this.securityUserRepository = securityUserRepository;
    }

    @Transactional
    @Override
    public void save(SecurityUserModel user){
        securityUserRepository.save(user);
    }
}
