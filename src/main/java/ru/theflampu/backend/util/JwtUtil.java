package ru.theflampu.backend.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import ru.theflampu.backend.entity.Role;
import ru.theflampu.backend.entity.User;

import java.time.Duration;
import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.lifetime}")
    private Duration lifeTime;

    public String generateToken(User user) {
        Algorithm algorithm = Algorithm.HMAC256(secret);

        Date issuedDate = new Date();
        Date expiredDate = new Date(issuedDate.getTime() + lifeTime.toMillis());

        return JWT.create()
                .withIssuedAt(issuedDate)
                .withExpiresAt(expiredDate)
                .withClaim("firstName", user.getFirstName())
                .withClaim("lastName", user.getLastName())
                .withArrayClaim("roles", user.getRoles().stream().map(Role::getName).toArray(String[]::new))
                .withSubject(user.getEmail())
                .sign(algorithm);
    }

    public String getEmail(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);

        return decodedJWT.getSubject();
    }

    public Set<GrantedAuthority> getRoles(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);

        return Arrays.stream(decodedJWT.getClaim("roles").asArray(String.class))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }
}
