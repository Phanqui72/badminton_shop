package com.mgr.api.form.nation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class CreateNationForm {
    @NotEmpty(message = "name is required")
    @ApiModelProperty(name = "name", required = true)
    private String name;

    @NotNull(message = "kind is required")
    @ApiModelProperty(name = "kind", required = true)
    private Integer kind;

    @ApiModelProperty(name = "postCode")
    private String postCode;

    @ApiModelProperty(name = "parentId")
    private Long parentId;
}
