package com.mgr.api.mapper;

import com.mgr.api.dto.seller.SellerDto;
import com.mgr.api.form.seller.CreateSellerForm;
import com.mgr.api.form.seller.UpdateSellerForm;
import com.mgr.api.model.Seller;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {AccountMapper.class, AddressMapper.class})
public interface SellerMapper {

    @Mapping(source = "shopName", target = "shopName")
    @Mapping(source = "shopDescription", target = "shopDescription")
    Seller fromCreateFormToEntity(CreateSellerForm form);

    @Mapping(source = "shopName", target = "shopName")
    @Mapping(source = "shopDescription", target = "shopDescription")
    void mappingUpdateFormToEntity(UpdateSellerForm form, @MappingTarget Seller seller);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "shopName", target = "shopName")
    @Mapping(source = "shopDescription", target = "shopDescription")
    @Mapping(source = "account", target = "account")
    @Mapping(source = "address", target = "address")
    @Mapping(target = "createdDate", expression = "java(com.mgr.api.utils.DateUtils.formatDate(seller.getCreatedDate(), com.mgr.api.constant.MgrConstant.DATE_TIME_FORMAT))")
    @Mapping(target = "modifiedDate", expression = "java(com.mgr.api.utils.DateUtils.formatDate(seller.getModifiedDate(), com.mgr.api.constant.MgrConstant.DATE_TIME_FORMAT))")
    SellerDto fromEntityToDto(Seller seller);

    @IterableMapping(elementTargetType = SellerDto.class)
    List<SellerDto> fromEntityListToDtoList(List<Seller> list);
}
