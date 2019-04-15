package com.bgreen.app.configuration;

import com.bgreen.app.models.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {


        String header = request.getHeader(JwtConfig.HEADER_STRING);

        if (header == null || !header.startsWith(JwtConfig.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        String token = header.replace(JwtConfig.TOKEN_PREFIX, "");

        Claims claims = Jwts.parser()
                .setSigningKey(JwtConfig.SECRET.getBytes())
                .parseClaimsJws(token)
                .getBody();

        User loggedInUser = new User();
        loggedInUser.setId(Long.valueOf(claims.getId()));
        loggedInUser.setUsername(claims.getSubject());

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        claims.getSubject(), loggedInUser, null);

        SecurityContextHolder.getContext().setAuthentication(auth);

        chain.doFilter(request, response);
    }

}
