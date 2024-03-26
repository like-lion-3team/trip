package com.traveloper.tourfinder.admin;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.traveloper.tourfinder.admin.repo.AdminMemberRepo;
import com.traveloper.tourfinder.admin.service.AdminMemberService;
import com.traveloper.tourfinder.auth.entity.Member;
import com.traveloper.tourfinder.auth.entity.Role;
import com.traveloper.tourfinder.auth.repo.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.access.AccessDeniedException;

import javax.swing.text.html.Option;
import java.util.Optional;


public class AdminMemberServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private AdminMemberRepo adminMemberRepo;

    @InjectMocks
    private AdminMemberService adminMemberService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }



    @DisplayName("Role 조회 테스트")
    @Test
    void findRoleTest(){
        Role blockRole = Role.builder()
                .name("BLOCK_USER")
                .build();

        when(roleRepository.findRoleByName("BLOCK_USER")).thenReturn(Optional.of(blockRole));
        when(roleRepository.findRoleByName("NOT_EXIST_ROLE_123123")).thenReturn(Optional.empty());
    }

    @DisplayName("Uuid로 멤버 조회 테스트")
    @Test
    void findMemberTest(){

    }

    @DisplayName("멤버 차단 테스트")
    @Test
    void blockMemberTest(){
        // given
        String testUuid = "f5583164-825a-4c59-99e8-3e4d31a0cc1a";

        findRoleTest();
        when(adminMemberRepo.findMemberByUuid(testUuid)).thenReturn(Optional.of(new Member()));

        // when
        adminMemberService.blockMember(testUuid);

        // then
        verify(adminMemberRepo, times(1)).save(any(Member.class));
    }


    @DisplayName("멤버 차단 해제 테스트")
    @Test
    void unBlockMemberTest(){
        String testUuid = "f5583164-825a-4c59-99e8-3e4d31a0cc1a";
        findRoleTest();
        when(adminMemberRepo.findMemberByUuid(testUuid)).thenReturn(Optional.of(new Member()));
        adminMemberService.unBlockMember(testUuid);
        verify(adminMemberRepo, times(1)).save(any(Member.class));
    }
}
