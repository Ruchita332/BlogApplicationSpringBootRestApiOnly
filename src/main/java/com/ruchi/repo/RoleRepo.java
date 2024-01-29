package com.ruchi.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ruchi.entity.Role;
import java.util.List;


public interface RoleRepo extends JpaRepository<Role, Long> {
	Optional<Role> findByRoleName(String roleName);
}
