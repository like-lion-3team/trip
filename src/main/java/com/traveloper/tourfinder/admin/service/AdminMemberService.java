package com.traveloper.tourfinder.admin.service;

import com.traveloper.tourfinder.admin.repo.AdminMemberRepo;
import com.traveloper.tourfinder.auth.entity.Member;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdminMemberService {
    private final AdminMemberRepo adminMemberRepo;

    public Page<Member> findMembers(Pageable pageable){
        return adminMemberRepo.findAll(pageable);
        // 모든 멤버 조회
    }
}
