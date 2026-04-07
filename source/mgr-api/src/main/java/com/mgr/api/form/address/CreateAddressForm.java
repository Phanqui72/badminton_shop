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
public class CreateAddressForm {
    @NotEmpty(message = "receiverName is required")
    @ApiModelProperty(name = "receiverName", required = true)
    private String receiverName;

    @NotNull(message = "nationId is required")
    @ApiModelProperty(name = "nationId", required = true)
    private Long nationId;

    @ApiModelProperty(name = "detail")
    private String detail;

    @ApiModelProperty(name = "isDefault")
    private Boolean isDefault = false;

    @NotNull(message = "userId is required")
    @ApiModelProperty(name = "userId", required = true)
    private Long userId;
}
