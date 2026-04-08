package com.mgr.api.form.address;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class UpdateAddressForm {
    @NotNull(message = "id is required")
    @ApiModelProperty(name = "id", required = true)
    private Long id;

    @NotEmpty(message = "receiverName is required")
    @ApiModelProperty(name = "receiverName", required = true)
    private String receiverName;

    @NotNull(message = "nationId is required")
    @ApiModelProperty(name = "nationId", required = true)
    private Long nationId;

    @ApiModelProperty(name = "detail")
    private String detail;

    @ApiModelProperty(name = "isDefault")
    private Boolean isDefault;

    @ApiModelProperty(name = "status")
    private Integer status;
}
