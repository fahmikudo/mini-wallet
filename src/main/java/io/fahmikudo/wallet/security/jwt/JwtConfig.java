package io.fahmikudo.wallet.security.jwt;

import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import javax.crypto.SecretKey;

@Getter
@Setter
@Configuration
public class JwtConfig {

    @Value("${mini.wallet.secret.access-token}")
    private String accessTokenSecretKey;

    @Value("${mini.wallet.secret.refresh-token}")
    private String refreshTokenSecretKey;

    @Value("${mini.wallet.secret.prefix}")
    private String tokenPrefix;

    @Value("${mini.wallet.secret.exp-refresh-token}")
    private Long refreshTokenExpiration;

    @Value("${mini.wallet.secret.exp-access-token}")
    private Long accessTokenExpiration;

    public String getAuthorizationHeader() {
        return HttpHeaders.AUTHORIZATION;
    }

    public SecretKey accessKey() {
        return Keys.hmacShaKeyFor(this.getAccessTokenSecretKey().getBytes());
    }

    public SecretKey refreshKey() {
        return Keys.hmacShaKeyFor(this.getRefreshTokenSecretKey().getBytes());
    }

}
