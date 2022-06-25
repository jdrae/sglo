package io.sglo.config.security;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import io.sglo.account.jwt.JwtHandler;
import io.sglo.account.jwt.TokenHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Configuration
public class TokenConfig {

    // TODO: separate access key and refresh key
    @Value("${jwt.public.key}")
    RSAPublicKey key;

    @Value("${jwt.private.key}")
    RSAPrivateKey priv;

    @Bean
    public TokenHelper accessTokenHelper(@Value("${jwt.access.expiry}") long expiryInSec){
        JwtHandler jwtHandler = new JwtHandler(jwtEncoder(key, priv), jwtDecoder(key));
        return new TokenHelper(jwtHandler, expiryInSec);
    }

    @Bean
    public TokenHelper refreshTokenHelper(@Value("${jwt.refresh.expiry}") long expiryInSec){
        JwtHandler jwtHandler = new JwtHandler(jwtEncoder(key, priv), jwtDecoder(key));
        return new TokenHelper(jwtHandler, expiryInSec);
    }


    JwtEncoder jwtEncoder(RSAPublicKey key, RSAPrivateKey priv) {
        JWK jwk = new RSAKey.Builder(key).privateKey(priv).build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }

    JwtDecoder jwtDecoder(RSAPublicKey key) {
        return NimbusJwtDecoder.withPublicKey(key).build();
    }

}
