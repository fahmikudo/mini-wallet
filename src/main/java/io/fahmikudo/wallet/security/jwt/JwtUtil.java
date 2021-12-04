package io.fahmikudo.wallet.security.jwt;

import io.fahmikudo.wallet.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    public <T> T extractClaim(SecretKey key, String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(key, token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(SecretKey key, String token) {
        Jws<Claims> jws = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
        return jws.getBody();
    }

    public String extractUserID(SecretKey key, String token) {
        return (String) extractClaim(key, token, claims -> claims.get("user_id"));
    }

    public Date extractExpiration(SecretKey key, String token) {
        return extractClaim(key, token, Claims::getExpiration);
    }

    private Boolean isTokenExpired(SecretKey key, String token) {
        return extractExpiration(key, token).before(new Date());
    }

    public String generateToken(User user, SecretKey secretKey, Long exp) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("user_id", user.getId());

        return createToken(claims, secretKey, exp);
    }

    private String createToken(Map<String, Object> claims, SecretKey secretKey, Long exp) {
        return Jwts.builder().setClaims(claims).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + exp))
                .signWith(secretKey, SignatureAlgorithm.HS256).compact();
    }

    public Boolean validateToken(SecretKey key, String token, User user) {
        final String userID = extractUserID(key, token);
        return (userID.equals(user.getId()) && !isTokenExpired(key, token));
    }

}
