package com.examly.springapp.service;

import com.examly.springapp.model.User;

public interface UserService {
    User createUser(User user);
    User loginuser(User user);
    User getUserById(Long userId);
    User findByUsername(String username);
    User findByEmail(String email);
}
