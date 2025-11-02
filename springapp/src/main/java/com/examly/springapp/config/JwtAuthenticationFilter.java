package com.examly.springapp.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/*
 * Custom filter that executes once per request to handle JWT authentication.
 * It extends OncePerRequestFilter to ensure a single execution per request.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    /*
     * Autowire the utility class for JWT operations (creating, validating, and extracting claims)
     */
    private final JwtUtils jwtUtils;

    /*
     * Autowire the custom MyUserDetailsService to load user details using the username extracted from the JWT. 
     */
    private final MyUserDetailsServiceImpl userService;

    public JwtAuthenticationFilter(JwtUtils jwtUtils, MyUserDetailsServiceImpl userService) {
        this.jwtUtils = jwtUtils;
        this.userService = userService;
    }

    /*
     * This method contains the core logic for the filter and is executed for every incoming request.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        /*
         * Extract the "Authorization" header from the request. 
         */
        final String authHeader = request.getHeader("Authorization");

        String email = null;
        String jwt = null;

        /*
         * Check if the header exists and is in the "Bearer [token]" format. 
         */
        if (authHeader != null && authHeader.startsWith("Bearer ")) {

            /*
             *  Extract the JWT token (the part after "Bearer "). 
             */
            jwt = authHeader.substring(7);

            /*
             * Extract the username from the JWT using JwtUtils. 
             */
            email = jwtUtils.extractEmail(jwt);
        }

        /*
         * If a username was extracted and no authentication context exists, proceed with authentication. 
         */
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            /*
             * Load user details from the database using the extracted username. 
             */
            UserDetails userDetails = userService.loadUserByUsername(email);

            /*
             * Validate the JWT token against the loaded user details. 
             */
            if (Boolean.TRUE.equals(jwtUtils.validateToken(jwt, userDetails))) {
                
                /*
                 * If the token is valid, create an authentication object. 
                 */
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());

                /*
                 * Set authentication details from the request (e.g., source IP). 
                 */
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                
                /*
                 * Set the authentication object in the SecurityContext. 
                 * This signals to Spring Security that the user is now authenticated 
                 * for the duration of this request. 
                 */
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        /*
         * Continue the filter chain. 
         * This passes the request to the next filter in the chain, 
         * which could be another custom filter or the Spring Security filter. 
         */
        filterChain.doFilter(request, response);
    }
}
