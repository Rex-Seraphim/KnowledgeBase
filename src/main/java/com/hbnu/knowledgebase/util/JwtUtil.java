package com.hbnu.knowledgebase.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private static final String SECRET_KEY = "MANWHATCANISAYMANWHATCANISAYMANWHATCANISAYMANWHATCANISAYMANWHATCANISAYMANWHATCANISAYMANWHATCANISAYMANWHATCANISAYMANWHATCANISAYMANWHATCANISAY";
    private static final long EXPIRATION = 86400000; // 24小时

    public static String sign(String id, String username) {
        return Jwts.builder()
                .setSubject(username)
                .claim("id", id)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    public static String parseUsername(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}






