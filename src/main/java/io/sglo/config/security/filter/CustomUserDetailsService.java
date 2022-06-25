package io.sglo.config.security.filter;

import io.sglo.account.jwt.TokenHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final TokenHelper accessTokenHelper;

    @Override
    public CustomUserDetails loadUserByUsername(String token) throws UsernameNotFoundException {
        return accessTokenHelper.parse(token)
                .map(this::convert)
                .orElse(null);
    }

    private CustomUserDetails convert(TokenHelper.PrivateClaims privateClaims){
        return new CustomUserDetails(
                privateClaims.getUserId(),
                // TODO: privateClaims.getRoleTypes().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet())
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }
}