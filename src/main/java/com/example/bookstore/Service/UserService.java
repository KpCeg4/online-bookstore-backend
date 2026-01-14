package com.example.bookstore.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.bookstore.Config.JwtUtil;
import com.example.bookstore.Model.User;
import com.example.bookstore.Repo.UserRepo;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public User register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("USER");
        try {
            return userRepo.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("EMAIL_EXISTS");
        }
    }

    public User loginAndGetUser(String email, String password) {
        User user = userRepo.findByEmail(email);

        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }

        return null;
    }

    public String generateToken(User user) {
        return jwtUtil.generateToken(user.getEmail(), user.getRole());
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public User updateUserRole(Long id, String role) {
        if (!role.equals("USER") && !role.equals("ADMIN")) {
            throw new RuntimeException("Invalid role");
        }

        User user = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setRole(role);
        return userRepo.save(user);
    }
}
