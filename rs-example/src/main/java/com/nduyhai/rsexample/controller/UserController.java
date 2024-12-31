package com.nduyhai.rsexample.controller;

import com.nduyhai.rsexample.dto.UserDTO;
import com.nduyhai.rsexample.dto.UserCreateDTO;
import com.nduyhai.rsexample.dto.UserUpdateDTO;
import com.nduyhai.rsexample.entity.User;
import com.nduyhai.rsexample.mapper.UserMapper;
import com.nduyhai.rsexample.service.UserService;
import com.nduyhai.rsexample.dto.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    public ResponseEntity<PageResponse<List<UserDTO>>> getAllUsers(
            @RequestParam(required = false) Long lastId,
            @RequestParam(defaultValue = "10") int size) {
        
        List<User> users = userService.getAllUsersKeySet(lastId, size + 1);
        boolean hasNext = users.size() > size;
        
        // Remove the extra item we fetched to check for next page
        if (hasNext) {
            users = users.subList(0, size);
        }
        
        List<UserDTO> userDTOs = users.stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
                
        Long nextCursor = hasNext && !users.isEmpty() ? 
                users.get(users.size() - 1).getId() : null;
                
        return ResponseEntity.ok(new PageResponse<>(
                userDTOs,
                hasNext,
                nextCursor
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(userMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserCreateDTO createDTO) {
        User user = userMapper.toEntity(createDTO);
        User savedUser = userService.createUser(user);
        return ResponseEntity.ok(userMapper.toDTO(savedUser));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserUpdateDTO updateDTO) {
        User user = userMapper.toEntity(updateDTO);
        User updatedUser = userService.updateUser(id, user);
        return ResponseEntity.ok(userMapper.toDTO(updatedUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }
} 