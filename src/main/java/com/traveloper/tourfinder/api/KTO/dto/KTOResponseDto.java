package com.traveloper.tourfinder.api.KTO.dto;

import lombok.Data;

@Data
public class KTOResponseDto {
    private KTOResponseHeaderDto header;
    private KTOResponseBodyDto body;
}
