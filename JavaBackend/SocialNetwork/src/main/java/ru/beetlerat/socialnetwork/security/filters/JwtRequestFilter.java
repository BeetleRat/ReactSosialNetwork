package ru.beetlerat.socialnetwork.security.filters;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.beetlerat.socialnetwork.security.JWT.JwtUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtils;
    @Value("${application.jwt.authHeader}")
    private String authorizationHeaderName = "";
    @Value("${application.jwt.tokenPrefix}")
    private String authorizationPrefix = "";

    private SecurityContext securityContext;

    @Autowired
    public JwtRequestFilter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        securityContext = SecurityContextHolder.getContext();

        scanJWT(request);

        // Передаем запрос следующему фильтру в цепочке
        filterChain.doFilter(request, response);
    }

    private void scanJWT(HttpServletRequest request) {
        String authorization =
                request.getHeader(authorizationHeaderName);

        if (authorization != null
                && authorization.startsWith(authorizationPrefix)) {
            encodeHeader(authorization);
        }
    }

    private void encodeHeader(String authorizationHeaderWithPrefix) {
        String jwt =
                authorizationHeaderWithPrefix.substring(authorizationPrefix.length());
        String username = getUsernameFromToken(jwt);

        if (!username.equals("")
                && securityContext.getAuthentication() == null) {

            addUserAndPermissionsToSecurityContext(
                    username,
                    jwtUtils.getUserPermissionsAsSGA(jwt)
            );
        }
    }

    private void addUserAndPermissionsToSecurityContext(
            String username,
            List<SimpleGrantedAuthority> permissions
    ) {

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(
                        username,
                        null,
                        permissions
                );

        securityContext.setAuthentication(usernamePasswordAuthenticationToken);
    }

    private String getUsernameFromToken(String jwt) {
        String username = "";

        try {
            username = jwtUtils.getUsername(jwt);
        } catch (ExpiredJwtException e) {
            System.out.println("Время жизни токена вышло");
        } catch (SignatureException e) {
            System.out.println("Подпись не корректна");
        }

        return username;
    }
}
