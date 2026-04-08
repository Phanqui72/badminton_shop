package com.mgr.api.controller;

import com.mgr.api.constant.MgrConstant;
import com.mgr.api.dto.ApiMessageDto;
import com.mgr.api.dto.ErrorCode;
import com.mgr.api.dto.ResponseListDto;
import com.mgr.api.dto.coupon.CouponDto;
import com.mgr.api.exception.NotFoundException;
import com.mgr.api.form.coupon.CreateCouponForm;
import com.mgr.api.form.coupon.UpdateCouponForm;
import com.mgr.api.mapper.CouponMapper;
import com.mgr.api.model.Coupon;
import com.mgr.api.model.criteria.CouponCriteria;
import com.mgr.api.repository.CouponRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/coupon")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class CouponController extends ABasicController {

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private CouponMapper couponMapper;

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('CPN_C')")
    @Transactional
    public ApiMessageDto<String> create(@Valid @RequestBody CreateCouponForm createCouponForm, BindingResult bindingResult) {
        Coupon coupon = couponMapper.fromCreateFormToEntity(createCouponForm);
        coupon.setStatus(MgrConstant.STATUS_ACTIVE);
        couponRepository.save(coupon);
        return makeSuccessResponse(null, "Create Coupon success");
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('CPN_U')")
    @Transactional
    public ApiMessageDto<String> update(@Valid @RequestBody UpdateCouponForm updateCouponForm, BindingResult bindingResult) {
        Coupon coupon = couponRepository.findById(updateCouponForm.getId())
                .orElseThrow(() -> new NotFoundException("Coupon not found", ErrorCode.PRODUCT_ERROR_NOT_FOUND));

        couponMapper.mappingUpdateFormToEntity(updateCouponForm, coupon);
        if (updateCouponForm.getStatus() != null) {
            coupon.setStatus(updateCouponForm.getStatus());
        }
        
        couponRepository.save(coupon);
        return makeSuccessResponse(null, "Update Coupon success");
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('CPN_L')")
    public ApiMessageDto<ResponseListDto<CouponDto>> list(CouponCriteria couponCriteria, Pageable pageable) {
        Page<Coupon> page = couponRepository.findAll(couponCriteria.getSpecification(), pageable);
        ResponseListDto<CouponDto> listDto = new ResponseListDto(
                couponMapper.fromEntityListToDtoList(page.getContent()),
                page.getTotalElements(),
                page.getTotalPages()
        );
        return makeSuccessResponse(listDto, "Get list coupon success");
    }

    @GetMapping(value = "/validate", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<CouponDto> validate(@RequestParam("code") String code) {
        Coupon coupon = couponRepository.findByCode(code)
                .orElseThrow(() -> new NotFoundException("Coupon not found", ErrorCode.PRODUCT_ERROR_NOT_FOUND));
        
        return makeSuccessResponse(couponMapper.fromEntityToDto(coupon), "Coupon is valid");
    }
}
