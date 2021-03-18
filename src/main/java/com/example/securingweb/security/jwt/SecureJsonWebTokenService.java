package com.example.securingweb.security.jwt;

import com.example.securingweb.entites.MetaTokenInf;
import com.example.securingweb.services.MetaTokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class SecureJsonWebTokenService {

    private final static Integer WEEK_DURATION = 604800; //7 days
    private final static String jwtSecret = "secure secret you cant break through";
    private final MetaTokenService service;

    public SecureJsonWebTokenService(MetaTokenService service) {
        this.service = service;
    }


    public JsonWebToken generateOncePerRequestToken() {
        MetaTokenInf metaTokenInf = service.generateTokenInf();
        String token = Jwts.
                builder().
                setId(String.valueOf(metaTokenInf.getId())).
                setSubject(metaTokenInf.getValue()).
                setExpiration(Date.from(LocalDate.now().plusDays(WEEK_DURATION).atStartOfDay(ZoneId.systemDefault()).toInstant())).
                signWith(SignatureAlgorithm.HS512, jwtSecret).
                compact();
        return new JsonWebToken(token, WEEK_DURATION);
    }

    public boolean validateToken(String javaWebToken) {
        Jws<Claims> claimsJws;
        try {
            claimsJws = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(javaWebToken);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Claims body = claimsJws.getBody();
        Long id = Long.valueOf(body.getId());
        return service.validateTokenIsPresentAndThenDelete(id, body.getSubject()) && DateChecker.isValid(body.getExpiration());

    }


}
