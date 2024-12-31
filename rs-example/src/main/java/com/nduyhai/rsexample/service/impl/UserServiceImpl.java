package com.nduyhai.rsexample.service.impl;

import com.nduyhai.rsexample.entity.User;
import com.nduyhai.rsexample.repository.UserRepository;
import com.nduyhai.rsexample.service.UserService;
import com.nduyhai.rsexample.exception.UserNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long id, User userDetails) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        
        user.setUsername(userDetails.getUsername());
        user.setEmail(userDetails.getEmail());
        user.setPassword(userDetails.getPassword());
        
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> getAllUsersKeySet(Long lastId, int limit) {
        if (lastId == null) {
            return userRepository.findTopByOrderByIdAsc(limit);
        }
        return userRepository.findByIdGreaterThanOrderByIdAsc(lastId, limit);
    }
} 