package com.traveloper.tourfinder.admin.dto;

import com.traveloper.tourfinder.auth.dto.MemberDto;
import com.traveloper.tourfinder.auth.entity.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Builder
public class PagingMembersDto {
    private List<MemberDto> members;

    @Schema(name = "총 몇페이지인지")
    private Integer totalPages;

    @Schema(name = "한페이지에 몇개의 데이터를 넣을 것인지")
    private Integer pageSize;

    @Schema(name = "현재 몇페이지를 조회했는지")
    private Integer pageNumber;

}
