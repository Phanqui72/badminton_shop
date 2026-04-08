package com.mgr.api.form.coupon;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class UpdateCouponForm {
    @NotNull(message = "id is required")
    @ApiModelProperty(name = "id", required = true)
    private Long id;

    @ApiModelProperty(name = "discountValue")
    private Double discountValue;

    @ApiModelProperty(name = "discountType")
    private Integer discountType;

    @ApiModelProperty(name = "expiryDate")
    private String expiryDate;

    @ApiModelProperty(name = "minOrderValue")
    private Double minOrderValue;

    @ApiModelProperty(name = "status")
    private Integer status;
}
