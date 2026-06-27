package com.vaa.investment.tracker.investment_service.security.config;

import com.vaa.investment.tracker.investment_service.security.jwt.JwtService;
import com.vaa.investment.tracker.investment_service.service.CustomUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;
    private static final Logger log =
            LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();

        return path.startsWith("/api/auth/");
    }
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);
        username = jwtService.extractUsername(jwt);
        log.debug("Request URI: {}", request.getRequestURI());
        log.debug("Authorization Header: {}", request.getHeader("Authorization"));
       try {
           if (username != null &&
                   SecurityContextHolder.getContext().getAuthentication() == null) {

               UserDetails userDetails =
                       userDetailsService.loadUserByUsername(username);

               if (jwtService.isTokenValid(jwt, userDetails)) {
                   UsernamePasswordAuthenticationToken authToken =
                           new UsernamePasswordAuthenticationToken(
                                   userDetails,
                                   null,
                                   userDetails.getAuthorities()
                           );

                   authToken.setDetails(
                           new WebAuthenticationDetailsSource().buildDetails(request)
                   );

                   SecurityContextHolder.getContext()
                           .setAuthentication(authToken);
               }
           }
           log.debug("Authentication successful");
           filterChain.doFilter(request, response);
       }catch (ExpiredJwtException e) {
           response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
           response.getWriter().write("Token expired");
           log.error("Token Expired", e);
           return;
       } catch (Exception e) {
           response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
           log.error("JWT authentication failed", e);
           response.getWriter().write("Some other issue");
       }
    }
}

