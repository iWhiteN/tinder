package com.tinder.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Optional;
import java.util.OptionalInt;

public class CookieReader {
    public static OptionalInt readCookie(HttpServletRequest request, String key) {
        if (request.getCookies() == null) {
            return OptionalInt.of(-1);
        }
        return OptionalInt.of(Arrays.stream(request.getCookies())
                .filter(c -> key.equals(c.getName()))
                .map(Cookie::getValue)
                .mapToInt(Integer::parseInt)
                .findAny()
                .orElse(-1));
    }
}
