package com.examly.springapp.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.examly.springapp.model.User;

import java.util.Collection;
import java.util.Collections;

/**
 * A custom implementation of the `UserDetails` interface used by Spring Security.
 * This class wraps the application's `User` entity to provide user details.
 */
public class UserPrinciple implements UserDetails {

    /*
     * Reference to the underlying user entity.
     */
    private final User user;

    /**
     * Constructs a new UserPrinciple.
     * @param user The user entity.
     */
    public UserPrinciple(User user) {
        this.user = user;
    }

    /**
     * Returns the authorities (roles) granted to the user.
     * @return A collection of GrantedAuthority objects.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        /*
         * Wraps the user's role into a SimpleGrantedAuthority.
         */
        return Collections.singletonList(new SimpleGrantedAuthority(user.getUserRole()));
    }

    /**
     * Returns the password used to authenticate the user.
     * @return The user's encoded password.
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * Returns the username used to authenticate the user.
     * @return The username.
     */
    @Override
    public String getUsername() {
        return user.getUsername();
    }

    /**
     * Indicates whether the user's account has expired (always returns true in this implementation).
     * @return true
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is locked or unlocked (always returns true in this implementation).
     * @return true
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indicates whether the user's credentials have expired (always returns true in this implementation).
     * @return true
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is enabled or disabled (always returns true in this implementation).
     * @return true
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
