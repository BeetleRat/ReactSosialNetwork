package ru.beetlerat.socialnetwork.dao;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.beetlerat.socialnetwork.models.RefreshToken;
import ru.beetlerat.socialnetwork.models.User;
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

    public Optional<RefreshToken> findByToken(String token) {
        List<RefreshToken> refreshTokenList = entityManager.createQuery("select rt from RefreshToken as rt where rt.token='" + token + "'").getResultList();
        if (refreshTokenList.isEmpty()) {
            return Optional.empty();
        }

        return Optional.ofNullable(refreshTokenList.get(0));
    }

    @Transactional
    public void deleteByUser(User user) {
        List<RefreshToken> refreshTokenList = entityManager.createQuery("select rt from RefreshToken as rt where rt.user=" + user).getResultList();
        if (refreshTokenList.isEmpty()) {
            throw new TokenNotFoundException();
        } else {
            entityManager.remove(refreshTokenList.get(0));
        }
    }

    @Transactional
    public void save(RefreshToken token) {
        entityManager.persist(token);
    }

    @Transactional
    public void delete(RefreshToken token) {
        RefreshToken deletedToken = entityManager.find(RefreshToken.class, token.getId());
        if (deletedToken == null) {
            throw new TokenNotFoundException();
        } else {
            entityManager.remove(deletedToken);
        }
    }

    public void removeExpiredTokens() {
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        String HQL = "delete from RefreshToken as rt where rt.expiryDate <= " + now;

        entityManager.createNativeQuery(HQL, RefreshToken.class);
    }
}
