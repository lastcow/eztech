package me.chen.eztech.service;

import me.chen.eztech.model.User;
import me.chen.eztech.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User create(User user){
        return userRepository.save(user);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    /**
     * Get user by username
     * @param username
     * @return
     */
    public Optional<User> getUserByUsername(String username){
        return userRepository.getUserByUsername(username);
    }

    public Optional<User> getUserById(String id){
        return userRepository.findById(id);
    }
}
