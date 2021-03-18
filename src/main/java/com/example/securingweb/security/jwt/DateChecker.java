package com.example.securingweb.security.jwt;

import java.util.Date;

public class DateChecker {

    public static boolean isValid(Date expiration) {
        Date now = new Date();
        long timeDiff = now.getTime() - expiration.getTime();
        return timeDiff <= 0;
    }
}
