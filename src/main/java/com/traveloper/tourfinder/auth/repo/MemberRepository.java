package com.traveloper.tourfinder.auth.repo;

import com.traveloper.tourfinder.auth.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Long, Member> {
}
