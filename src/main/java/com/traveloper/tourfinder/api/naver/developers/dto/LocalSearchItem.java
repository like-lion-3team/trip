package com.traveloper.tourfinder.api.naver.developers.dto;

import lombok.Data;

@Data
public class LocalSearchItem {
    private String title;
    private String link;
    private String category;
    private String description;
    private String telephone;
    private String address;
    private String roadAddress;
    private Integer mapx;
    private Integer mapy;
}
