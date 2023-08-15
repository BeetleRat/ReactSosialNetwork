package ru.beetlerat.socialnetwork.security.JWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Класс взаимодействия с переменными в application.properties
// Данный класс реализует взаимодействие со свойствами начинающимеся на application.jwt
@ConfigurationProperties(prefix = "application.jwt")
public class JwtUtils {
    private final static String PERMISSIONS = "permissions";
    // Свойство application.jwt.secretKey
    private String secretKey;
    // Свойство application.jwt.tokenPrefix
    private String tokenPrefix;
    // Свойство application.jwt.accessTokenLifetimeInMinutes
    private Integer accessTokenLifetimeInMinutes;
    // Свойство application.jwt.refreshTokenLifetimeInDays
    private Integer refreshTokenLifetimeInDays;
    // Свойство application.jwt.authHeader
    private String authHeader;

    // Обязательно должен быть конструктор по умолчанию
    public JwtUtils() {
    }

    public String generateAccessToken(UserDetails userDetails) {
        Duration accessTokenLifetime = Duration.of(accessTokenLifetimeInMinutes, ChronoUnit.MINUTES);

        return generateTokenWithLifetime(userDetails, accessTokenLifetime);
    }

    public String generateRefreshToken(UserDetails userDetails) {
        Duration refreshTokenLifetime = Duration.of(refreshTokenLifetimeInDays, ChronoUnit.DAYS);

        return generateTokenWithLifetime(userDetails, refreshTokenLifetime);
    }

    private String generateTokenWithLifetime(UserDetails userDetails, Duration lifeTime) {
        Map<String, Object> claims = getClaimsFromUser(userDetails);

        Date creationDate = new Date();
        Date expiredDate = new Date(creationDate.getTime() + lifeTime.toMillis());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(creationDate)
                .setExpiration(expiredDate)
                .signWith(getRealSecretKey())
                .compact();
    }

    private Map<String, Object> getClaimsFromUser(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();

        // Получаем список разрешений пользователя
        List<String> permissionsList = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList();

        claims.put(PERMISSIONS, permissionsList);

        return claims;
    }

    // Получить секретный ключ для генерации токена основываясь на secretKey
    public SecretKey getRealSecretKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String getUsername(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    public List<String> getUserPermissionsAsString(String token) {
        return getAllClaimsFromToken(token).get(PERMISSIONS, List.class);
    }

    public List<SimpleGrantedAuthority> getUserPermissionsAsSGA(
            String token) {
        return getUserPermissionsAsString(token).stream()
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getRealSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Геттеры
    public String getAuthHeader() {
        return authHeader;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public String getTokenPrefix() {
        return tokenPrefix;
    }

    public Integer getAccessTokenLifetimeInMinutes() {
        return accessTokenLifetimeInMinutes;
    }

    public Integer getRefreshTokenLifetimeInDays() {
        return refreshTokenLifetimeInDays;
    }

    // Сеттеры
    public void setAuthHeader(String authHeader) {
        this.authHeader = authHeader;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public void setTokenPrefix(String tokenPrefix) {
        this.tokenPrefix = tokenPrefix;
    }

    public void setAccessTokenLifetimeInMinutes(Integer accessTokenLifetimeInMinutes) {
        this.accessTokenLifetimeInMinutes = accessTokenLifetimeInMinutes;
    }

    public void setRefreshTokenLifetimeInDays(Integer refreshTokenLifetimeInDays) {
        this.refreshTokenLifetimeInDays = refreshTokenLifetimeInDays;
    }
}
