package com.phoenix.blog.util;

import com.phoenix.blog.model.JwtPair;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.experimental.UtilityClass;

import java.time.Duration;

@UtilityClass
public final class CookieUtil {

    public static void send(JwtPair pair,
                            HttpServletResponse res) {

        Cookie access = build("ACCESS", pair.accessToken(), Duration.ofMinutes(15));
        Cookie refresh = build("REFRESH", pair.refreshToken(), Duration.ofDays(7));

        res.addCookie(access);
        res.addCookie(refresh);
    }

    private static Cookie build(String name, String value, Duration maxAge) {
        Cookie c = new Cookie(name, value);
        c.setHttpOnly(true);
        c.setSecure(true);
        c.setPath("/");
        c.setMaxAge((int) maxAge.toSeconds());
        // Spring Boot 3.x embeds Servlet 6; for SameSite you need a response header
        c.setDomain("localhost");
        return c;
    }
}
