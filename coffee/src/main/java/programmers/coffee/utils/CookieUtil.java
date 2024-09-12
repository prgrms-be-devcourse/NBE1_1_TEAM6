package programmers.coffee.utils;

import jakarta.servlet.http.Cookie;

public class CookieUtil {

    public static final String COOKIE_KEY="cartCookie";

    public static Cookie getCartCookie(Cookie[] requestCookies){
        for(Cookie cookie:requestCookies){
            if(cookie.getName().equals(COOKIE_KEY)){
                return cookie;
            }
        }
        return null;
    }
}
