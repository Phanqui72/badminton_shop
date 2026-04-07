package com.mgr.api.mapper;

import com.mgr.api.dto.user.UserDto;
import com.mgr.api.form.user.CreateUserForm;
import com.mgr.api.form.user.UpdateUserForm;
import com.mgr.api.model.User;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {AccountMapper.class, AddressMapper.class})
public interface UserMapper {

    @Mapping(source = "gender", target = "gender")
    @Mapping(target = "dateOfBirth", expression = "java(com.mgr.api.utils.DateUtils.convertStringToLocalDate(form.getDateOfBirth(), com.mgr.api.constant.MgrConstant.DATE_FORMAT))")
    User fromCreateFormToEntity(CreateUserForm form);

    @Mapping(source = "gender", target = "gender")
    @Mapping(target = "dateOfBirth", expression = "java(com.mgr.api.utils.DateUtils.convertStringToLocalDate(form.getDateOfBirth(), com.mgr.api.constant.MgrConstant.DATE_FORMAT))")
    void mappingUpdateFormToEntity(UpdateUserForm form, @MappingTarget User user);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "gender", target = "gender")
    @Mapping(source = "account", target = "account")
    @Mapping(source = "addresses", target = "addresses")
    @Mapping(target = "dateOfBirth", expression = "java(com.mgr.api.utils.DateUtils.formatDate(user.getDateOfBirth(), com.mgr.api.constant.MgrConstant.DATE_FORMAT))")
    @Mapping(target = "createdDate", expression = "java(com.mgr.api.utils.DateUtils.formatDate(user.getCreatedDate(), com.mgr.api.constant.MgrConstant.DATE_TIME_FORMAT))")
    @Mapping(target = "modifiedDate", expression = "java(com.mgr.api.utils.DateUtils.formatDate(user.getModifiedDate(), com.mgr.api.constant.MgrConstant.DATE_TIME_FORMAT))")
    UserDto fromEntityToDto(User user);

    @IterableMapping(elementTargetType = UserDto.class)
    List<UserDto> fromEntityListToDtoList(List<User> list);
}
