package com.mgr.api.dto.user;

import com.mgr.api.dto.ABasicAdminDto;
import com.mgr.api.dto.account.AccountDto;
import com.mgr.api.dto.address.AddressDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserDto extends ABasicAdminDto {

    @ApiModelProperty(name = "gender")
    private Integer gender;

    @ApiModelProperty(name = "dateOfBirth")
    private String dateOfBirth;

    @ApiModelProperty(name = "account")
    private AccountDto account;

    @ApiModelProperty(name = "addresses")
    private List<AddressDto> addresses;
}
