package com.traveloper.tourfinder.auth.repo;

import com.traveloper.tourfinder.auth.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository
    extends JpaRepository<Role, Long> {
    Optional<Role> findRoleByName(String name);
}
