package com.traveloper.tourfinder.auth.repo;

import com.traveloper.tourfinder.auth.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository
    extends JpaRepository<Role, Long> {
}
