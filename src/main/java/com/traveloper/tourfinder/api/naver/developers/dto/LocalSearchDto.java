package com.traveloper.tourfinder.api.naver.developers.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class LocalSearchDto {
    private Date lastBuildDate;
    private Integer total;
    private Integer start;
    private Integer display;
    private List<LocalSearchItem> items;
}
