package com.nduyhai.rsexample.repository;

import com.nduyhai.rsexample.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    @Query(value = "SELECT * FROM users ORDER BY id ASC LIMIT :limit", nativeQuery = true)
    List<User> findTopByOrderByIdAsc(int limit);
    @Query(value = "SELECT * FROM users WHERE id > :lastId ORDER BY id ASC LIMIT :limit", nativeQuery = true)
    List<User> findByIdGreaterThanOrderByIdAsc(Long lastId, int limit);
} 