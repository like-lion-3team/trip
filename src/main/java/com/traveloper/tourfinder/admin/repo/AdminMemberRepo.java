package com.traveloper.tourfinder.admin.repo;

import com.traveloper.tourfinder.auth.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AdminMemberRepo extends JpaRepository<Member, Long> {

}
