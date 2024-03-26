package com.traveloper.tourfinder.admin.service;

import com.traveloper.tourfinder.admin.repo.AdminMemberRepo;
import com.traveloper.tourfinder.auth.entity.Member;
import com.traveloper.tourfinder.auth.entity.Role;
import com.traveloper.tourfinder.auth.repo.MemberRepository;
import com.traveloper.tourfinder.auth.repo.RoleRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AdminMemberService {
    private final AdminMemberRepo adminMemberRepo;
    private final RoleRepository roleRepository;

    public Page<Member> findMembers(Pageable pageable) {
        return adminMemberRepo.findAll(pageable);
        // 모든 멤버 조회
    }

    @Transactional
    public void blockMember(String uuid) {
        String blockRoleName = "BLOCK_USER";
        Role role = roleRepository.findRoleByName(blockRoleName)
                .orElseThrow(() -> new AccessDeniedException("Role을 찾을 수 없습니다."));

        updateMemberRole(uuid, role);
    }

    public void updateMemberRole(String uuid, Role role) {
        Member member = adminMemberRepo.findMemberByUuid(uuid).orElseThrow(() -> new AccessDeniedException("유저를 찾을 수 없습니다."));

        Member updatedMember = Member.builder()
                .id(member.getId())
                .uuid(member.getUuid())
                .memberName(member.getMemberName())
                .password(member.getPassword())
                .nickname(member.getNickname())
                .email(member.getEmail())
                .role(role)
                .build();

        adminMemberRepo.save(updatedMember);
    }
}
