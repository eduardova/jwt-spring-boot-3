package dev.eduardova.jwt.components;

import dev.eduardova.jwt.dtos.auth.AuthenticationToken;
import dev.eduardova.jwt.exceptions.GeneralInputErrorException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.Key;
import java.util.Collection;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Component
public class JwtUtils {

    private static final String AUTHORITIES = "authorities";

    private final Long ttlAccessToken;
    private final Long ttlRefreshToken;
    private final Key key;

    @Autowired
    public JwtUtils(
        @Value("${configs.security.secret-key}") String secretKey,
        @Value("${configs.security.ttl-access-token:60}") Long ttlAccessToken,
        @Value("${configs.security.ttl-refresh-token:240}") Long ttlRefreshToken
    ) {
        this.ttlAccessToken = ttlAccessToken;
        this.ttlRefreshToken = ttlRefreshToken;
        key = new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS256.getJcaName());
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Collection<? extends GrantedAuthority> extractAuthorities(String token) {
        var authorities = extractClaim(token, tkn -> tkn.get(AUTHORITIES, List.class));
        return authorities.stream()
            .map(authority -> new SimpleGrantedAuthority(((Map) (authority)).get("authority").toString()))
            .toList();
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) throws GeneralInputErrorException {
        return Jwts.parserBuilder().setSigningKey(key).build()
            .parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public AuthenticationToken generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(AUTHORITIES, userDetails.getAuthorities());
        return createToken(claims, userDetails.getUsername());
    }

    public AuthenticationToken refreshToken(String refreshToken) {
        var username = extractUsername(refreshToken);
        if (username != null) {
            Map<String, Object> claims = new HashMap<>();
            claims.put(AUTHORITIES, extractAuthorities(refreshToken));
            return createToken(claims, username);
        }
        throw new GeneralInputErrorException("Cannot generate token with refresh token");
    }

    private AuthenticationToken createToken(Map<String, Object> claims, String subject) {
        var accessToken = generateToken(claims, subject, ttlAccessToken);
        var refreshToken = generateToken(claims, subject, ttlRefreshToken);
        var response = new AuthenticationToken();
        response.setAccessToken(accessToken);
        response.setRefreshToken(refreshToken);
        response.setEmail(subject);
        return response;
    }

    private String generateToken(Map<String, Object> claims, String subject, Long minutes) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * minutes))
            .signWith(key, SignatureAlgorithm.HS256).compact();
    }

    public Boolean validateAcessToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
