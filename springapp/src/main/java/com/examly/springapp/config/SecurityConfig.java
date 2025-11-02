package com.examly.springapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Main security configuration class for the application.
 * It uses `@Configuration` and `@EnableWebSecurity` to enable Spring Security
 * and define how security is handled, including authentication and authorization.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    public static final String PROPERTY_API_ENDPOINT = "/api/properties/{propertyId}";
    public static final String PROPERTY_INQUIRY_API_ENDPOINT =  "/api/inquiries/{inquiryId}";
    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_USER = "USER";

// Usage
    /**
     * Configures the security filter chain that processes all HTTP requests.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   JwtAuthenticationFilter jwtFilter,
                                                   JwtAuthenticationEntryPoint unauthorizedHandler) throws Exception {
        http
            /*
             * Disables Cross-Site Request Forgery (CSRF) protection.
             * This is common for stateless APIs (which use JWTs) because the
             * tokens are typically sent in headers, not cookies.
             */
            .csrf(csrf -> csrf.disable())
            /*
             * Configures authorization for incoming HTTP requests.
             */
            .authorizeHttpRequests(auth -> auth
                /*
                 * Allows unauthenticated access to the login and register endpoints.
                 */
                .requestMatchers("/api/login", "/api/register", "/api/properties", "/api/feedback").permitAll()
                /*
                 * Allows EndPoints based on userRole
                 */
                .requestMatchers(HttpMethod.POST, "/api/properties").hasAuthority(ROLE_ADMIN)
                .requestMatchers(HttpMethod.GET, PROPERTY_API_ENDPOINT).hasAnyAuthority(ROLE_ADMIN, ROLE_USER)
                .requestMatchers(HttpMethod.PUT, PROPERTY_API_ENDPOINT).hasAuthority(ROLE_ADMIN)
                .requestMatchers(HttpMethod.DELETE, PROPERTY_API_ENDPOINT).hasAuthority(ROLE_ADMIN)
                .requestMatchers(HttpMethod.POST, "/api/inquiries").hasAuthority(ROLE_USER)
                .requestMatchers(HttpMethod.GET, PROPERTY_INQUIRY_API_ENDPOINT).hasAuthority(ROLE_ADMIN)
                .requestMatchers(HttpMethod.GET, "/api/inquiries/user/{userId}").hasAuthority(ROLE_USER)
                .requestMatchers(HttpMethod.GET, "/api/inquiries").hasAuthority(ROLE_ADMIN)
                .requestMatchers(HttpMethod.PUT, PROPERTY_INQUIRY_API_ENDPOINT).hasAuthority(ROLE_ADMIN)
                .requestMatchers(HttpMethod.DELETE, PROPERTY_INQUIRY_API_ENDPOINT).hasAnyAuthority(ROLE_ADMIN, ROLE_USER)
                //.requestMatchers(HttpMethod.POST, "/api/feedback").hasAuthority(ROLE_ADMIN)
                .requestMatchers(HttpMethod.GET, "/api/feedback/{feedbackId}").hasAnyAuthority(ROLE_ADMIN, ROLE_USER)
                //.requestMatchers(HttpMethod.GET, "/api/feedback").hasAuthority(ROLE_ADMIN)
                .requestMatchers(HttpMethod.GET, "/api/feedback/user/{userId}").hasAuthority(ROLE_USER)
                .requestMatchers(HttpMethod.DELETE, "/api/feedback/{feedbackId}").hasAuthority(ROLE_USER)
                .requestMatchers(HttpMethod.GET, "/api/user/{userId}").hasAnyAuthority(ROLE_ADMIN, ROLE_USER)
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                /*
                 * Requires authentication for any other request.
                 */
                .anyRequest().authenticated()
            )
            /*
             * Configures exception handling.
             * Sets the entry point for handling unauthorized authentication failures.
             */
            .exceptionHandling(ex -> ex.authenticationEntryPoint(unauthorizedHandler))
            /*
             * Configures session management to be stateless.
             * This means that Spring Security will not create or use HTTP sessions
             * to store the security context, relying completely on the JWT token.
             */
            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        /*
         * Adds the custom JWT filter before Spring's standard authentication filter.
         * This ensures that JWT-based authentication is processed first.
         */
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /*
     * Defines the AuthenticationManager bean.
     * It is used by Spring Security to perform authentication.
     */
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http,
                                                       MyUserDetailsServiceImpl userService) throws Exception {
        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        /*
         * Configures the AuthenticationManager to use the custom UserDetailsService
         * and the specified password encoder.
         */
        builder.userDetailsService(userService).passwordEncoder(passwordEncoder());
        return builder.build();
    }

    /*
     * Creates and provides the PasswordEncoder bean.
     * BCrypt is a strong, adaptive password-hashing algorithm that
     * is highly recommended for securely storing passwords.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
