package com.mgr.api.form.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class RegisterForm {
    @NotEmpty(message = "Email is required")
    @Email(message = "Email is invalid")
    @ApiModelProperty(name = "email", required = true)
    private String email;

    @NotEmpty(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    @ApiModelProperty(name = "password", required = true)
    private String password;

    @NotEmpty(message = "Full name is required")
    @ApiModelProperty(name = "fullName", required = true)
    private String fullName;

    @ApiModelProperty(name = "phone")
    private String phone;

    @NotNull(message = "Gender is required")
    @ApiModelProperty(name = "gender", required = true)
    private Integer gender; // 1: Male, 2: Female, 3: Other
}
