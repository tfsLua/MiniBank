package com.squad21.pitang.User.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.squad21.pitang.User.User;
import com.squad21.pitang.User.Repository.IUserRepository;

@Service
    public class UserService {
    @Autowired
        private IUserRepository userRepository;

        public List<User> getAllUsers() {
            return userRepository.findAll();
        }
    }
