package com.example.securingweb.security.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class JsonWebTokenService {

    private final static Integer WEEK_DURATION = 604800; //7 days
    private final static String jwtSecret = "javamaster";


    public JsonWebToken generateToken(String username) {
        String token = Jwts.
                builder().
                setSubject(username).
                setExpiration(Date.from(LocalDate.now().plusDays(WEEK_DURATION).atStartOfDay(ZoneId.systemDefault()).toInstant())).
                signWith(SignatureAlgorithm.HS512, jwtSecret).
                compact();
        return new JsonWebToken(token, WEEK_DURATION);
    }

    public String validateTokenAndGetUsername(String javaWebToken) {
        Jws<Claims> claimsJws;
        try {
            claimsJws = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(javaWebToken);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Claims body = claimsJws.getBody();
        if (DateChecker.isValid(body.getExpiration())) {
            return body.getSubject();
        } else {
            throw new RuntimeException("token has expired, re-login");
        }
    }

}
