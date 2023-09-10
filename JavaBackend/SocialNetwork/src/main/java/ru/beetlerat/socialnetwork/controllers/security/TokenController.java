package ru.beetlerat.socialnetwork.controllers.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.beetlerat.socialnetwork.dto.user.authorized.AuthorizationRefreshTokenDTO;
import ru.beetlerat.socialnetwork.dto.user.authorized.AuthorizationUserInfoDTO;
import ru.beetlerat.socialnetwork.dto.user.authorized.JWTokenDTOResponse;
import ru.beetlerat.socialnetwork.dto.ResponseToFront;
import ru.beetlerat.socialnetwork.models.RefreshTokenModel;
import ru.beetlerat.socialnetwork.models.UserModel;
import ru.beetlerat.socialnetwork.security.JWT.JwtUtils;
import ru.beetlerat.socialnetwork.security.types.SecurityUserDetails;
import ru.beetlerat.socialnetwork.services.refreshtoken.RefreshTokenService;
import ru.beetlerat.socialnetwork.services.users.AuthUserService;
import ru.beetlerat.socialnetwork.services.users.FindUserService;
import ru.beetlerat.socialnetwork.utill.exceptions.NotValidException;
import ru.beetlerat.socialnetwork.utill.exceptions.token.TokenNotFoundException;
import ru.beetlerat.socialnetwork.utill.exceptions.token.TokenRefreshedException;

import javax.validation.Valid;

@RestController
@RequestMapping("/security/token")
public class TokenController {
    private final FindUserService findUserService;
    private final AuthUserService authService;
    private final RefreshTokenService refreshTokenService;
    private final JwtUtils jwtUtils;

    @Autowired
    public TokenController(FindUserService findUserService, AuthUserService authService, RefreshTokenService refreshTokenService, JwtUtils jwtUtils) {
        this.findUserService = findUserService;
        this.authService = authService;
        this.refreshTokenService = refreshTokenService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping
    public ResponseEntity<? extends ResponseToFront> createJWTToken(@RequestBody @Valid AuthorizationUserInfoDTO authRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()
                || authRequest.equals(new AuthorizationUserInfoDTO())) {
            throw new NotValidException("Authorization user info is not correct");
        }

        try {
            authService.setAuthentication(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getUsername(),
                            authRequest.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            return ResponseEntity.ok(
                    ResponseToFront.FromMessageAndResultCode(
                            ResponseToFront.Message.NOT_FOUND.getMessage(),
                            ResponseToFront.Code.NOT_FOUND.getCode()
                    )
            );
        }

        UserModel user = findUserService.getByUsername(authRequest.getUsername());
        SecurityUserDetails securityUserDetails = new SecurityUserDetails(user.getSecuritySettings());

        String jwtAccess = jwtUtils.generateAccessToken(securityUserDetails);
        String jwtRefresh = "";


        return ResponseEntity.ok(JWTokenDTOResponse.FromAccessAndRefreshToken(jwtAccess, jwtRefresh));
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<? extends ResponseToFront> refreshToken(@RequestBody @Valid AuthorizationRefreshTokenDTO request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()
                || request.equals(new AuthorizationRefreshTokenDTO())) {
            throw new NotValidException("Authorization user info is not correct");
        }

        String requestRefreshToken = request.getRefreshToken();

        RefreshTokenModel token = refreshTokenService.findToken(requestRefreshToken).orElseThrow(TokenNotFoundException::new);

        SecurityUserDetails securityUserDetails = new SecurityUserDetails(token.getUser().getSecuritySettings());

        String jwtAccess = jwtUtils.generateAccessToken(securityUserDetails);
        String jwtRefresh = getJWTRefresh(token, securityUserDetails);

        return ResponseEntity.ok(JWTokenDTOResponse.FromAccessAndRefreshToken(jwtAccess, jwtRefresh));
    }

    private String getJWTRefresh(RefreshTokenModel token, SecurityUserDetails securityUserDetails) {
        String jwtRefresh;
        try {
            jwtRefresh = refreshTokenService.verifyExpiration(token).getToken();
        } catch (TokenRefreshedException e) {
            jwtRefresh = refreshTokenService.createRefreshToken(securityUserDetails).getToken();
        }

        return jwtRefresh;
    }
}
