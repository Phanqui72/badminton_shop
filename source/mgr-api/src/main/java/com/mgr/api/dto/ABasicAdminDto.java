package com.mgr.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ABasicAdminDto {
    @ApiModelProperty(name = "id")
    private Long id;
    @ApiModelProperty(name = "status")
    private Integer status;
    @ApiModelProperty(name = "modifiedDate")
    private String modifiedDate;
    @ApiModelProperty(name = "createdDate")
    private String createdDate;
}
