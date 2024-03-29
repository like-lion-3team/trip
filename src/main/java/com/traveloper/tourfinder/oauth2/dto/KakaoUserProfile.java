package com.traveloper.tourfinder.oauth2.dto;

import lombok.Getter;

@Getter
public class KakaoUserProfile {
    private Long id;
    private String connectedAt;

    private Properties properties;
    private KakaoAccount kakaoAccount;




    public static class Properties {
        private String nickname;
        private String profileImage;
        private String thumbnailImage;
    }


    @Getter
    public static class KakaoAccount {
        private boolean profileNicknameNeedsAgreement;
        private boolean profileImageNeedsAgreement;
        private Profile profile;

        private boolean hasEmail;
        private boolean emailNeedsAgreement;
        private boolean isEmailValid;
        private boolean isEmailVerified;
        private String email;




        public static class Profile {
            private String nickname;
            private String thumbnailImageUrl;
            private String profileImageUrl;
            private boolean isDefaultImage;
            private boolean isDefaultNickname;
        }
    }
}