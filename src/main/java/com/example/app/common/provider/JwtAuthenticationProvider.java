package com.example.app.common.provider;

import com.example.app.app.auth.domain.vo.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final var authToken = (JwtAuthenticationToken) authentication;
        final var accessToken = authToken.getAccessToken();
        final var claims = jwtTokenProvider.decodeToken(accessToken);
        if (claims == null) {
            return null;
        }

        var email = claims.get("email", String.class);
        var authorities = claims.get("authorities", List.class);
        var authoritiesList = new ArrayList<GrantedAuthority>();
        for (Object authority : authorities) {
            authoritiesList.add(UserRole.valueOf((String) authority));
        }
        var token = new JwtAuthenticationToken(accessToken, email, authoritiesList);
        token.setAuthenticated(true);
        return token;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(JwtAuthenticationToken.class);
    }
}
