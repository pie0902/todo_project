package com.tryagain.tryagain.jwt;

import com.tryagain.tryagain.domain.User;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
    private Key key;

    public JwtUtil(@Value("${jwt.secret.key}") String secret) {
        byte[] byteSecretKey = Decoders.BASE64.decode(secret);
        key = Keys.hmacShaKeyFor(byteSecretKey);
    }

    public String getUsername(String token) {

        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().get("username", String.class);
    }

    public Boolean isExpired(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getExpiration().before(new Date());
    }

    //코드 중복 줄이기
    //사용자 정보 Claims에 설정

    private Claims setClaims(User user) {
        Claims claims = Jwts.claims();
        Map<String,Object> userInfo = Map.of(
            "id",user.getId(),
            "username",user.getUsername(),
            "email", user.getEmail()
        );
        claims.putAll(userInfo);
        return claims;
    }

    //jwt생성
    public String createJwt(User user) {
        Claims claims = setClaims(user);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 30000000))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
    public User getUserFromToken(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        User user = new User();
        user.setId(claims.get("id", Long.class));
        user.setUsername(claims.get("username", String.class));
        user.setEmail(claims.get("email", String.class));
        return user;
    }
}
//로그인할때 json으로 받아오기 (RequestBody)
