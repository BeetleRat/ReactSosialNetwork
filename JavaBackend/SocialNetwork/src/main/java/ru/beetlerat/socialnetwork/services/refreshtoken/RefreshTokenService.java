package ru.beetlerat.socialnetwork.services.refreshtoken;

import ru.beetlerat.socialnetwork.models.RefreshToken;
import ru.beetlerat.socialnetwork.models.User;
import ru.beetlerat.socialnetwork.security.types.SecurityUserDetails;

import java.util.Optional;

public interface RefreshTokenService {
    RefreshToken createRefreshToken(SecurityUserDetails user);

    Optional<RefreshToken> findToken(String token);

    RefreshToken verifyExpiration(RefreshToken token);

    void removeExpiredTokens();

    void deleteByUser(User user);
}
