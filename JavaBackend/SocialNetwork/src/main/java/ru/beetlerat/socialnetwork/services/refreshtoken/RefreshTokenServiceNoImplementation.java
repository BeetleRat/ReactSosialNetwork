package ru.beetlerat.socialnetwork.services.refreshtoken;

import ru.beetlerat.socialnetwork.models.RefreshToken;
import ru.beetlerat.socialnetwork.models.User;
import ru.beetlerat.socialnetwork.security.types.SecurityUserDetails;

import java.util.Optional;

public class RefreshTokenServiceNoImplementation implements RefreshTokenService{
    @Override
    public RefreshToken createRefreshToken(SecurityUserDetails user) {
        return null;
    }

    @Override
    public Optional<RefreshToken> findToken(String token) {
        return Optional.empty();
    }

    @Override
    public RefreshToken verifyExpiration(RefreshToken token) {
        return null;
    }

    @Override
    public void removeExpiredTokens() {

    }

    @Override
    public void deleteByUser(User user) {

    }
}
