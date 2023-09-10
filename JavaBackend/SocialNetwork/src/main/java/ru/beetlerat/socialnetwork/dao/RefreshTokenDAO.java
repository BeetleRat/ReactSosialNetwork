package ru.beetlerat.socialnetwork.dao;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.beetlerat.socialnetwork.models.RefreshTokenModel;
import ru.beetlerat.socialnetwork.models.UserModel;
import ru.beetlerat.socialnetwork.utill.exceptions.token.TokenNotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@Transactional(readOnly = true)
public class RefreshTokenDAO {
    @PersistenceContext
    private EntityManager entityManager;

    public Optional<RefreshTokenModel> findByToken(String token) {
        List<RefreshTokenModel> refreshTokenList = entityManager.createQuery("select rt from RefreshTokenModel as rt where rt.token='" + token + "'").getResultList();
        if (refreshTokenList.isEmpty()) {
            return Optional.empty();
        }

        return Optional.ofNullable(refreshTokenList.get(0));
    }

    @Transactional
    public void deleteByUser(UserModel user) {
        List<RefreshTokenModel> refreshTokenList = entityManager.createQuery("select rt from RefreshTokenModel as rt where rt.user=" + user).getResultList();
        if (refreshTokenList.isEmpty()) {
            throw new TokenNotFoundException();
        } else {
            entityManager.remove(refreshTokenList.get(0));
        }
    }

    @Transactional
    public void save(RefreshTokenModel token) {
        entityManager.persist(token);
    }

    @Transactional
    public void delete(RefreshTokenModel token) {
        RefreshTokenModel deletedToken = entityManager.find(RefreshTokenModel.class, token.getId());
        if (deletedToken == null) {
            throw new TokenNotFoundException();
        } else {
            entityManager.remove(deletedToken);
        }
    }

    public void removeExpiredTokens() {
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        String HQL = "delete from RefreshToken as rt where rt.expiryDate <= " + now;

        entityManager.createNativeQuery(HQL, RefreshTokenModel.class);
    }
}
