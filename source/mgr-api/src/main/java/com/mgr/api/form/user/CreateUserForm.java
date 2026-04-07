package com.mgr.api.form.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CreateUserForm {
    @NotNull(message = "gender is required")
    @ApiModelProperty(name = "gender", required = true)
    private Integer gender;

    @ApiModelProperty(name = "dateOfBirth")
    private String dateOfBirth;

    @ApiModelProperty(name = "addresses")
    private List<com.mgr.api.form.address.CreateAddressForm> addresses;
}
