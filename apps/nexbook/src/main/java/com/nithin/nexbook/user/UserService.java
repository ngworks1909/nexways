package com.nithin.nexbook.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    public User getUserById(String userId) {
        return userRepository.findById(userId).orElse(null);
    }
}
