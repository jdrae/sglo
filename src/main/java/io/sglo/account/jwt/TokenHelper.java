package io.sglo.account.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class TokenHelper {

    private final String USER_ID = "user_id";

    private final JwtHandler jwtHandler;
    private final long expiryInSec;

    public String createToken(PrivateClaims privateClaims){
        return jwtHandler.createToken(toMap(privateClaims), expiryInSec);
    }

    public Optional<PrivateClaims> parse(String token){
        return jwtHandler.parse(token).map(this::toClaims);
    }

    private Map<String, Object> toMap(PrivateClaims claims){
        return Map.of(USER_ID, claims.getUserId());
    }

    private PrivateClaims toClaims(Jwt jwt){
        return new PrivateClaims(jwt.getClaim(USER_ID));
    }

    @Getter
    @AllArgsConstructor
    public static class PrivateClaims{
        private String userId;
        // TODO: add roles
    }

}
