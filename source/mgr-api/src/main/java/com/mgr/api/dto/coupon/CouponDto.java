package com.mgr.api.dto.coupon;

import com.mgr.api.dto.ABasicAdminDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CouponDto extends ABasicAdminDto {
    @ApiModelProperty(name = "code")
    private String code;

    @ApiModelProperty(name = "discountValue")
    private Double discountValue;

    @ApiModelProperty(name = "discountType")
    private Integer discountType;

    @ApiModelProperty(name = "expiryDate")
    private String expiryDate;

    @ApiModelProperty(name = "minOrderValue")
    private Double minOrderValue;
}
