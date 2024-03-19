package com.traveloper.tourfinder.auth.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String uuid;
    private String memberName;
    @Setter
    private String password;
    @Setter
    private String nickname;
    private String email;

    @ManyToOne
    private Role role;
}