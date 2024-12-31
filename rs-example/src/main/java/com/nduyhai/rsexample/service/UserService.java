package com.nduyhai.rsexample.service;

import com.nduyhai.rsexample.entity.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers();
    Optional<User> getUserById(Long id);
    User createUser(User user);
    User updateUser(Long id, User user);
    void deleteUser(Long id);
    List<User> getAllUsersKeySet(Long lastId, int limit);
} 