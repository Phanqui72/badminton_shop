package com.mgr.api.form.coupon;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class CreateCouponForm {
    @NotEmpty(message = "code is required")
    @ApiModelProperty(name = "code", required = true)
    private String code;

    @NotNull(message = "discountValue is required")
    @ApiModelProperty(name = "discountValue", required = true)
    private Double discountValue;

    @NotNull(message = "discountType is required")
    @ApiModelProperty(name = "discountType", required = true)
    private Integer discountType; // 1: Percent, 2: Fixed amount

    @ApiModelProperty(name = "expiryDate")
    private String expiryDate;

    @ApiModelProperty(name = "minOrderValue")
    private Double minOrderValue;
}
