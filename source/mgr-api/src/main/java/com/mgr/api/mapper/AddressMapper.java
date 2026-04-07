package com.mgr.api.mapper;

import com.mgr.api.dto.address.AddressDto;
import com.mgr.api.form.address.CreateAddressForm;
import com.mgr.api.form.address.UpdateAddressForm;
import com.mgr.api.model.Address;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AddressMapper {

    @Mapping(source = "receiverName", target = "receiverName")
    @Mapping(source = "detail", target = "detail")
    @Mapping(source = "isDefault", target = "isDefault")
    Address fromCreateFormToEntity(CreateAddressForm form);

    @Mapping(source = "receiverName", target = "receiverName")
    @Mapping(source = "detail", target = "detail")
    @Mapping(source = "isDefault", target = "isDefault")
    void mappingUpdateFormToEntity(UpdateAddressForm form, @MappingTarget Address address);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "receiverName", target = "receiverName")
    @Mapping(source = "detail", target = "detail")
    @Mapping(source = "isDefault", target = "isDefault")
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "createdDate", expression = "java(com.mgr.api.utils.DateUtils.formatDate(address.getCreatedDate(), com.mgr.api.constant.MgrConstant.DATE_TIME_FORMAT))")
    @Mapping(target = "modifiedDate", expression = "java(com.mgr.api.utils.DateUtils.formatDate(address.getModifiedDate(), com.mgr.api.constant.MgrConstant.DATE_TIME_FORMAT))")
    AddressDto fromEntityToDto(Address address);

    @IterableMapping(elementTargetType = AddressDto.class)
    List<AddressDto> fromEntityListToDtoList(List<Address> list);
}
