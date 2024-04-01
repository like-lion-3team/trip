package com.traveloper.tourfinder.oauth2.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class NaverUserProfile {

    @JsonProperty("resultcode")
    private String resultCode;

    @JsonProperty("message")
    private String message;

    @JsonProperty("response")
    private Response response;

    @Getter
    public static class Response {

        @JsonProperty("id")
        private String id;

        @JsonProperty("profile_image")
        private String profileImage;

        @JsonProperty("email")
        private String email;

        @JsonProperty("name")
        private String name;

    }
}
