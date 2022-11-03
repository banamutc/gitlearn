package com.example.springfile.repository;

import com.example.springfile.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
    // data layer
}
