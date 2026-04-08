package com.mgr.api.dto.nation;

import com.mgr.api.dto.ABasicAdminDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NationDto extends ABasicAdminDto {
    @ApiModelProperty(name = "name")
    private String name;

    @ApiModelProperty(name = "kind")
    private Integer kind;

    @ApiModelProperty(name = "postCode")
    private String postCode;

    @ApiModelProperty(name = "parentId")
    private Long parentId;
}
