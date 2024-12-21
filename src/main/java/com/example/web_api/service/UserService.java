package com.example.web_api.service;

import com.example.web_api.entities.User;
import com.example.web_api.repos.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    public User saveUser(User user) {
        if (!user.getPassword().startsWith("$2a$")) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        return userRepository.save(user);
    }
    public User updateUser(Long id, User updatedUser) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    existingUser.setUsername(updatedUser.getUsername());
                    if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
                        existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
                    }
                    existingUser.setRoles(updatedUser.getRoles());
                    return userRepository.save(existingUser);
                })
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
