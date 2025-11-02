package com.examly.springapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.examly.springapp.model.User;
import com.examly.springapp.repository.UserRepo;

/**
 * Custom implementation of Spring Security's UserDetailsService to load user-specific data.
 * This class is responsible for retrieving user information (including roles and permissions)
 * from the database for authentication purposes.
 */
@Service
public class MyUserDetailsServiceImpl implements UserDetailsService {

    /*
     * The repository for accessing User entities from the database.
     * Declared as 'final' to enforce immutability, which is a best practice for dependencies.
     */
    private final UserRepo userRepo;

    /**
     * Constructor for MyUserDetailsServiceImpl.
     * The dependencies (UserRepo and PasswordEncoder) are injected by Spring through this constructor.
     * This is the recommended practice for dependency injection.
     */
    @Autowired
    public MyUserDetailsServiceImpl(UserRepo userRepo, PasswordEncoder encoder) {
        this.userRepo = userRepo;
    }
    
    /**
     * Locates the user based on the username. In a production environment,
     * the incoming username might be a unique identifier like an email address.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        /*
         * Attempts to find the user in the database using the provided username.
         */
        User user = userRepo.findByEmail(email);

        /*
         * Throws an exception if no user is found, as required by the UserDetailsService contract.
         */
        if (user == null) {
            throw new UsernameNotFoundException("User not found from exception");
        }

        /*
         * Returns a custom UserDetails implementation, UserPrinciple,
         * which wraps the User entity and provides the necessary
         * details for Spring Security.
         */
        return new UserPrinciple(user);
    }

    public Long getUserIdByUsername(String username){

        /*
         * Attempts to find the user in the database using the provided username.
         */
        User user = userRepo.findByUsername(username);

        /*
         * Throws an exception if no user is found, as required by the UserDetailsService contract.
         */
        if (user == null) {
            throw new UsernameNotFoundException("User not found from exception");
        }

        /*
         * Returns a user id
         */
        return user.getUserId();
    }
}
