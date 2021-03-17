package com.example.securingweb.security.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class JsonWebTokenService {

    private final static Integer duration = 604800; //7 days
    private final static String jwtSecret = "javamaster";

    public JsonWebToken generateToken(String username) {
        String token = Jwts.
                builder().
                setSubject(username).
                setExpiration(Date.from(LocalDate.now().plusDays(duration).atStartOfDay(ZoneId.systemDefault()).toInstant())).
                signWith(SignatureAlgorithm.HS512, jwtSecret).
                compact();
        return new JsonWebToken(token, duration);
    }

    public String validateTokenAndGetUsername(String javaWebToken) {
        Jws<Claims> claimsJws;
        try {
            claimsJws = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(javaWebToken);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Claims body = claimsJws.getBody();
        Date now = new Date();
        Date expiration = body.getExpiration();

        long timeDiff = now.getTime() - expiration.getTime();
        if (timeDiff > 0) {
            throw new RuntimeException("token has expired, re-login");
        } else {
            return body.getSubject();
        }
    }

}
