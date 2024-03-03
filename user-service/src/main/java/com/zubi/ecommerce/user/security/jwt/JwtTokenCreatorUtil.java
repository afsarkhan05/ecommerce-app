package com.zubi.ecommerce.user.security.jwt;

import com.zubi.ecommerce.auth.common.filter.AuthTokenFilter;
import com.zubi.ecommerce.auth.common.service.UserDetailsImpl;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import java.security.Key;
import java.util.Date;

@Component
@Slf4j
public class JwtTokenCreatorUtil {

    @Autowired
    private AuthTokenFilter authFilter;
    public void setJwtUtil(AuthTokenFilter authFilter){
        this.authFilter = authFilter;
    }

    public String getJwtFromCookies(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, authFilter.getJwtCookie());
        if (cookie != null) {
            return cookie.getValue();
        } else {
            return null;
        }
    }

    public ResponseCookie generateJwtCookie(UserDetailsImpl userPrincipal) {
        String jwt = generateTokenFromUsername(userPrincipal.getUsername(), null);
        ResponseCookie cookie = ResponseCookie.from(authFilter.getJwtCookie(), jwt).path("/api").maxAge(24 * 60 * 60).httpOnly(true).build();
        return cookie;
    }

    public ResponseCookie getCleanJwtCookie() {
        ResponseCookie cookie = ResponseCookie.from(authFilter.getJwtCookie(), null).path("/api").build();
        return cookie;
    }


    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(authFilter.getJwtSecret()));
    }

    public String generateTokenFromUsername(String username, String userId) {
        return Jwts.builder()
                .setSubject(username)
                .id(userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + authFilter.getJwtExpirationMs()))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }
}
