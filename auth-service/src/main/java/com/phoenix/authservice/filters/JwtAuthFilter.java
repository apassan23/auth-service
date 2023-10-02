package com.phoenix.authservice.filters;

import com.phoenix.authservice.constants.RequestConstants;
import com.phoenix.authservice.utils.TokenUtils;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    private final Logger LOGGER = LoggerFactory.getLogger(JwtAuthFilter.class);

    private final TokenUtils tokenUtils;
    private final UserDetailsService userDetailsService;

    public JwtAuthFilter(TokenUtils tokenUtils, UserDetailsService userDetailsService) {
        this.tokenUtils = tokenUtils;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(RequestConstants.AUTHORIZATION_HEADER);
        String username = null;
        String authToken = null;

        if (Objects.nonNull(header) && header.startsWith(RequestConstants.TOKEN_PREFIX)) {
            try {
                authToken = header.replace(RequestConstants.TOKEN_PREFIX, "");
                username = tokenUtils.getUsernameFromToken(authToken);
                // extract username
            } catch (IllegalArgumentException e) {
                // unexpected error when extracting username
                LOGGER.error("An error occurred while fetching Username from Token", e);
            } catch (ExpiredJwtException e) {
                // token expired
                LOGGER.warn("The token has expired", e);
            } catch (Exception e) {
                // username or password not valid
                LOGGER.error("Authentication Failed. Username or Password not valid.", e);
            }
        } else {
            LOGGER.warn("Couldn't find bearer string, header will be ignored");
        }

        if (Objects.nonNull(username) && Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {
            // populate auth token in security context
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (tokenUtils.validateToken(authToken, userDetails)) {
                UsernamePasswordAuthenticationToken token = tokenUtils.authToken(authToken, SecurityContextHolder.getContext().getAuthentication(), userDetails);
                token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                LOGGER.info("Authenticated user {}, setting security context", username);
                SecurityContextHolder.getContext().setAuthentication(token);
            }
        }

        filterChain.doFilter(request, response);
    }
}
