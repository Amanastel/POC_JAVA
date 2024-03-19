package com.masai.scheduler.repository;

import com.masai.scheduler.payload.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
