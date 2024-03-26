package com.traveloper.tourfinder.admin.repo;

import com.traveloper.tourfinder.auth.entity.Member;
import com.traveloper.tourfinder.auth.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface AdminMemberRepo extends JpaRepository<Member, Long> {
    Optional<Member> findMemberByUuid(String uuid);
    Optional<Member> findMemberByEmail(String email);


}
