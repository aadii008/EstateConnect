package com.examly.springapp.config;

import io.jsonwebtoken.*;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.examly.springapp.model.User;
import com.examly.springapp.repository.UserRepo;
import com.examly.springapp.service.UserService;

import java.util.*;
import java.util.function.Function;

@Component
public class JwtUtils {

    private final UserRepo userRepo;

    /**
     * Constructor for AuthController.
     * All dependencies are injected by Spring.
     */
    public JwtUtils(UserRepo userRepo){
        this.userRepo = userRepo;
    }


    /*
     * A static constant for the secret key used to sign and verify JWTs.
     */
    private static final String SECRET_KEY = "fjfekruwgrug@^^&*&6237";

    /*
     * Extracts the username (subject) from the given JWT token.
     */
    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /*
     * Extracts the expiration date from the given JWT token.
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /*
     * A generic method to extract any claim from the JWT token.
     * It takes a Function that resolves the desired claim from the Claims object.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(extractAllClaims(token));
    }

    /*
     * Extracts all the claims from the JWT token.
     * It uses the Jwts parser and the SECRET_KEY to parse and verify the token's signature.
     */
    public Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    /*
     * Checks if the given JWT token has expired.
     */
    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /*
     * Generates a new JWT token for a given user.
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        User user = userRepo.findByUsername(userDetails.getUsername());
        return createToken(claims, user.getEmail());
    }

    /*
     * Creates the JWT token with the specified claims and subject.
     * It sets the issuer, expiration date, and signs the token with the SECRET_KEY.
     * The token is set to expire in 1 hour.
     */
    public String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
            .setClaims(claims)
            .setSubject(subject)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
            .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
            .compact();
    }

    /*
     * Validates the given JWT token against the user details.
     * It checks if the username from the token matches the user details and if the token is not expired.
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        User user = userRepo.findByUsername(userDetails.getUsername());
        return extractEmail(token).equals(user.getEmail()) && !isTokenExpired(token);
    }
}
