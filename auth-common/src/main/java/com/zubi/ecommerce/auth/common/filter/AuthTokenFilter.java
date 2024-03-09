package com.zubi.ecommerce.auth.common.filter;

import com.zubi.ecommerce.auth.common.AuthConstant;
import com.zubi.ecommerce.auth.common.service.UserDetailsImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.Key;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@Data
public class AuthTokenFilter extends OncePerRequestFilter {

    @Value("${ecommerce.app.jwtSecret}")
    private String jwtSecret;

    @Value("${ecommerce.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    @Value("${ecommerce.app.jwtCookieName}")
    private String jwtCookie;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request){
        String path = request.getServletPath();
        return isSwaggerRequest(path);
    }

    private boolean isSwaggerRequest(String path) {
        return path.startsWith("/swagger-ui/") || path.startsWith("/v3/api-docs") || path.contains("csrf")
                || path.contains("/actuator/") || path.contains("swagger") || path.endsWith("/swagger-ui/index.html");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        log.info("Inside Authfilter");
        try {
            String jwt = parseJwt(request);
            if (jwt != null) {
                if (validateJwtToken(jwt)) {
                    log.info("Token verified");
                    Map<String, String> claimMap = getIdentityFromJwtToken(jwt);

                    UserDetails userDetails = UserDetailsImpl.
                            builder()
                            .username(claimMap.get(AuthConstant.USERNAME))
                            .build();


                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(userDetails,
                                    null,
                                    userDetails.getAuthorities());

                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    filterChain.doFilter(request, response);

                /*UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails,
                                null,
                                userDetails.getAuthorities());*/

                    //authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    log.error("Token expired or invalid");
                    // Handle unauthenticated requests
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }
            }else {
                log.info("No token provided, authentication will handle.");
                filterChain.doFilter(request, response);
            }

        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
        }


    }

    private String parseJwt(HttpServletRequest request) {
        String jwt = getJwtFromHeader(request);
        return jwt;
    }

    public String getJwtFromHeader(HttpServletRequest request) {
        String token = request.getHeader(AuthConstant.AUTHORIZATION);
        if (! StringUtils.isEmpty(token)) {
            return token;
        } else {
            return null;
        }
    }


    public Map<String, String> getIdentityFromJwtToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(key()).build()
                .parseClaimsJws(token).getBody();

        Map<String, String> claimMap = new HashMap<>();
        claimMap.put(AuthConstant.USERNAME, claims.getSubject());
        claimMap.put(AuthConstant.ID, claims.getId());

        return claimMap;
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(key()).build().parse(authToken);
            return true;
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        } catch (Exception e){
            log.error("Invalid JWT token");
        }

        return false;
    }
}
