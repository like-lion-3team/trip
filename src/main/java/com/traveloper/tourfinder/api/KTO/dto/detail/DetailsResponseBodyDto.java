package com.traveloper.tourfinder.api.KTO.dto.detail;

import lombok.Data;

@Data
public class DetailsResponseBodyDto {
    private DetailsResponseItemsDto items;
    private Integer numOfRows;
    private Integer pageNo;
    private Integer totalCount;
}
