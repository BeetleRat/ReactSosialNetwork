package ru.beetlerat.socialnetwork.services.refreshtoken;

import ru.beetlerat.socialnetwork.models.RefreshTokenModel;
import ru.beetlerat.socialnetwork.models.UserModel;
import ru.beetlerat.socialnetwork.security.types.SecurityUserDetails;

import java.util.Optional;

public interface RefreshTokenService {
    RefreshTokenModel createRefreshToken(SecurityUserDetails user);

    Optional<RefreshTokenModel> findToken(String token);

    RefreshTokenModel verifyExpiration(RefreshTokenModel token);

    void removeExpiredTokens();

    void deleteByUser(UserModel user);
}
