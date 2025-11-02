package com.examly.springapp.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.config.JwtUtils;
import com.examly.springapp.config.MyUserDetailsServiceImpl;
import com.examly.springapp.model.LoginDTO;
import com.examly.springapp.model.User;
import com.examly.springapp.service.UserService;

import jakarta.validation.Valid;

/**
 * Controller class for handling authentication-related API endpoints.
 * This includes login, user registration, and retrieving user information.
 */
@RestController
public class AuthController {
 
    /*
     * The AuthenticationManager is used to authenticate a user.
     */
    private final AuthenticationManager authenticationManager;

    private static final String ERROR_KEY = "error";


    /*
     * The JwtUtils utility class is used for JWT token operations.
     */
    private final JwtUtils jwtUtil;
 
    /*
     * The UserService provides business logic for user-related operations.
     */
    private final  UserService userService;

    /*
     * The MyUserDetailsServiceImpl provides user details for Spring Security.
     */
    private final MyUserDetailsServiceImpl myUserDetailsServiceImpl;

    /**
     * Constructor for AuthController.
     * All dependencies are injected by Spring.
     */
    public AuthController(AuthenticationManager authenticationManager, JwtUtils jwtUtil, UserService userService,
            MyUserDetailsServiceImpl myUserDetailsServiceImpl) {

        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.myUserDetailsServiceImpl = myUserDetailsServiceImpl;
    }

    /**
     * Handles user login requests.
     * Authenticates the user and returns a JWT token upon successful authentication.
     *
     * @param loginDTO The DTO containing the user's login credentials.
     * @return A ResponseEntity containing the JWT token and user details, or an error.
     */
    @PostMapping("/api/login")
    public ResponseEntity<Map<String, Object>> login(@Valid @RequestBody LoginDTO loginDTO) {
        try {
            // Check if user exists
            User user = userService.findByEmail(loginDTO.getEmail());
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(ERROR_KEY, "User not found"));
            }

            // Authenticate credentials
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword())
            );

            // Load user details and generate JWT
            final UserDetails userDetails = myUserDetailsServiceImpl.loadUserByUsername(loginDTO.getEmail());
            final String jwt = jwtUtil.generateToken(userDetails);

            Map<String, Object> response = new HashMap<>();
            response.put("jwtToken", jwt);
            response.put("username", userDetails.getUsername());
            response.put("userId", myUserDetailsServiceImpl.getUserIdByUsername(userDetails.getUsername()));
            response.put("role", userDetails.getAuthorities().toArray()[0].toString());

            return ResponseEntity.ok(response);

        } catch (org.springframework.security.authentication.BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(ERROR_KEY, "Invalid email or password"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(ERROR_KEY, "Login failed"));
        }
    }

    /**
     * Registers a new user.
     *
     * @param user The user object to be created.
     * @return The created user object.
     */
    
    @PostMapping("/api/register")
    public User signup(@Valid @RequestBody User user) {
        return userService.createUser(user);
    }

    /** 
     * Retrieves a user by their ID.
     *
     * @param userId The ID of the user to retrieve.
     * @return The user object.
     */
    @GetMapping("/api/user/{userId}")
    public User getUserById(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }
}
