package com.traveloper.tourfinder.api.KTO.dto.search;

import lombok.Data;

import java.util.List;

@Data
public class KTOResponseItemsDto {
    List<KTOItemDto> item;
}
