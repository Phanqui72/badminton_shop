package com.mgr.api.mapper;

import com.mgr.api.dto.coupon.CouponDto;
import com.mgr.api.form.coupon.CreateCouponForm;
import com.mgr.api.form.coupon.UpdateCouponForm;
import com.mgr.api.model.Coupon;
import org.mapstruct.*;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CouponMapper {

    @Mapping(source = "code", target = "code")
    @Mapping(source = "discountValue", target = "discountValue")
    @Mapping(source = "discountType", target = "discountType")
    @Mapping(source = "minOrderValue", target = "minOrderValue")
    @Mapping(target = "expiryDate", expression = "java(com.mgr.api.utils.DateUtils.convertStringToLocalDateTime(form.getExpiryDate(), com.mgr.api.constant.MgrConstant.DATE_TIME_FORMAT))")
    Coupon fromCreateFormToEntity(CreateCouponForm form);

    @Mapping(source = "discountValue", target = "discountValue")
    @Mapping(source = "discountType", target = "discountType")
    @Mapping(source = "minOrderValue", target = "minOrderValue")
    @Mapping(target = "expiryDate", expression = "java(com.mgr.api.utils.DateUtils.convertStringToLocalDateTime(form.getExpiryDate(), com.mgr.api.constant.MgrConstant.DATE_TIME_FORMAT))")
    void mappingUpdateFormToEntity(UpdateCouponForm form, @MappingTarget Coupon coupon);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "code", target = "code")
    @Mapping(source = "discountValue", target = "discountValue")
    @Mapping(source = "discountType", target = "discountType")
    @Mapping(source = "minOrderValue", target = "minOrderValue")
    @Mapping(source = "status", target = "status")
    @Mapping(target = "expiryDate", expression = "java(com.mgr.api.utils.DateUtils.formatDate(coupon.getExpiryDate(), com.mgr.api.constant.MgrConstant.DATE_TIME_FORMAT))")
    @Mapping(target = "createdDate", expression = "java(com.mgr.api.utils.DateUtils.formatDate(coupon.getCreatedDate(), com.mgr.api.constant.MgrConstant.DATE_TIME_FORMAT))")
    @Mapping(target = "modifiedDate", expression = "java(com.mgr.api.utils.DateUtils.formatDate(coupon.getModifiedDate(), com.mgr.api.constant.MgrConstant.DATE_TIME_FORMAT))")
    CouponDto fromEntityToDto(Coupon coupon);

    @IterableMapping(elementTargetType = CouponDto.class)
    List<CouponDto> fromEntityListToDtoList(List<Coupon> list);
}
