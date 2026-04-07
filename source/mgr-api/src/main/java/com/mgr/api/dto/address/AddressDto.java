package com.mgr.api.dto.address;

import com.mgr.api.dto.ABasicAdminDto;
import com.mgr.api.dto.nation.NationDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddressDto extends ABasicAdminDto {
    @ApiModelProperty(name = "receiverName")
    private String receiverName;

    @ApiModelProperty(name = "nation")
    private NationDto nation;

    @ApiModelProperty(name = "detail")
    private String detail;

    @ApiModelProperty(name = "isDefault")
    private Boolean isDefault;

    @ApiModelProperty(name = "userId")
    private Long userId;
}
