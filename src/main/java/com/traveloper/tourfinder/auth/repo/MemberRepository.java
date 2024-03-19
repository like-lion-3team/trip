package com.traveloper.tourfinder.auth.repo;

import com.traveloper.tourfinder.auth.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository
    extends JpaRepository<Member, Long> {

    Optional<Member> findMemberByUuid(String uuid);
}
