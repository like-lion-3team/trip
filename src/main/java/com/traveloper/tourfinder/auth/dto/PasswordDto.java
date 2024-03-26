package com.traveloper.tourfinder.auth.dto;

import com.traveloper.tourfinder.auth.entity.Member;
import lombok.*;

@Getter
@Builder
@Setter
@AllArgsConstructor
public class PasswordDto {
    private String currentPassword;
    private String newPassword;
    private String confirmPassword;

    public static PasswordDto fromEntity(Member entity) {
        return PasswordDto.builder()
                .currentPassword(entity.getPassword())
                .newPassword(entity.getPassword())
                .confirmPassword(entity.getPassword())
                .build();
    }
}
