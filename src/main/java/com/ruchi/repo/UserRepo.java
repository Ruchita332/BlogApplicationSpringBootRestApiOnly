package com.ruchi.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ruchi.entity.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long>{

	Optional<User> findByEmail(String email);
	Optional<User> findByOtp(String otp);
}
