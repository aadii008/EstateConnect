package com.examly.springapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.examly.springapp.model.User;
import com.examly.springapp.repository.UserRepo;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private BCryptPasswordEncoder encoder;

    @Override
    public User createUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    @Override
    public User getUserById(Long userId) {
        return userRepo.findById(userId).orElse(null);
    }

    @Override
    public User loginuser(User user) {
        return userRepo.findByUsername(user.getUsername());
    }
    
    @Override
    public User findByUsername(String username){
        return userRepo.findByUsername(username);
    }

    @Override
    public User findByEmail(String email){
        return userRepo.findByEmail(email);
    }

}
