package com.traveloper.tourfinder.oauth2.entity;

import com.traveloper.tourfinder.common.BaseEntity;
import jakarta.persistence.*;

import lombok.Getter;
import java.util.List;
@Getter
@Entity
public class SocialProvider extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    // ex) KAKAO, NAVER, GOOGLE
    private String socialProviderName;

    @OneToMany(mappedBy = "socialProvider")
    private List<SocialProviderMember> socialProviderMembers;
}
