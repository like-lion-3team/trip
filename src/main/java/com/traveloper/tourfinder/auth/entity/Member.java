package com.traveloper.tourfinder.auth.entity;

import com.traveloper.tourfinder.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

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
    private String username;
    @Setter
    private String password;
    @Setter
    private String nickname;
    private String email;

    @ManyToOne
    private Role role;
}