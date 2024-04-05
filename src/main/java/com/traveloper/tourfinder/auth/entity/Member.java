package com.traveloper.tourfinder.auth.entity;

import com.traveloper.tourfinder.common.BaseEntity;
import com.traveloper.tourfinder.oauth2.entity.SocialProviderMember;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String uuid;
    private String memberName;

    private String password;
    @Setter
    private String nickname;
    private String email;
    @Setter
    private String profile;

    @ManyToOne
    private Role role;

    @OneToMany(mappedBy = "member")
    private List<SocialProviderMember> socialProviderMembers;

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }
}