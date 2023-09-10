package ru.beetlerat.socialnetwork.services.refreshtoken;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.beetlerat.socialnetwork.dao.RefreshTokenDAO;
import ru.beetlerat.socialnetwork.models.RefreshTokenModel;
import ru.beetlerat.socialnetwork.models.UserModel;
import ru.beetlerat.socialnetwork.security.JWT.JwtUtils;
import ru.beetlerat.socialnetwork.security.types.SecurityUserDetails;
import ru.beetlerat.socialnetwork.utill.exceptions.token.TokenRefreshedException;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Service
public class RefreshTokenServiceImplementation implements RefreshTokenService {
    @Value("${application.jwt.refreshTokenLifetimeInDays}")
    private final int refreshTokenLifetimeInDays = 0;
    private final RefreshTokenDAO refreshTokenDAO;
    private final JwtUtils jwtUtils;

    @Autowired
    public RefreshTokenServiceImplementation(RefreshTokenDAO refreshTokenDAO, JwtUtils jwtUtils) {
        this.refreshTokenDAO = refreshTokenDAO;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public RefreshTokenModel createRefreshToken(SecurityUserDetails user) {
        String jwtRefresh = jwtUtils.generateRefreshToken(user);

        RefreshTokenModel newRefreshToken = new RefreshTokenModel();
        newRefreshToken.setToken(jwtRefresh);
        newRefreshToken.setUser(user.getSecurityUser().getUser());

        LocalDateTime expiryDate = LocalDateTime.now().plus(Duration.ofDays(refreshTokenLifetimeInDays));
        newRefreshToken.setExpiryDate(Timestamp.valueOf(expiryDate));

        refreshTokenDAO.save(newRefreshToken);

        return newRefreshToken;
    }

    @Override
    public Optional<RefreshTokenModel> findToken(String token) {

        return refreshTokenDAO.findByToken(token);
    }

    @Override
    public RefreshTokenModel verifyExpiration(RefreshTokenModel token) {
        if (token.getExpiryDate().after(new Date())) {
            refreshTokenDAO.delete(token);
            throw new TokenRefreshedException(token.getToken(), "Refresh token was expired. Please make a new signin request");
        }

        return token;
    }

    @Override
    public void removeExpiredTokens() {
        refreshTokenDAO.removeExpiredTokens();
    }

    @Override
    public void deleteByUser(UserModel user) {
        refreshTokenDAO.deleteByUser(user);
    }
}
