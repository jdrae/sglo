package io.sglo.account.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.*;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;

public class JwtHandler {

    @Autowired
    JwtEncoder jwtEncoder;
    @Autowired
    JwtDecoder jwtDecoder;

    private String type = "Bearer ";

    public String createToken(Map<String, Object> privateClaims, Long expiryInSec){

        Instant now = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiryInSec))
                .claims(claim -> claim.putAll(privateClaims))
                .build();

        return type + jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public Optional<Jwt> parse(String token){
        try {
            return Optional.of(jwtDecoder.decode(untype(token)));
        } catch (Exception e){
            return Optional.empty();
        }
    }

    //== helper methods ==//
    private String untype(String token){
        return token.substring(type.length());
    }
}
