package com.mgr.api.controller;

import com.mgr.api.constant.MgrConstant;
import com.mgr.api.dto.ApiMessageDto;
import com.mgr.api.dto.ErrorCode;
import com.mgr.api.dto.ResponseListDto;
import com.mgr.api.dto.seller.SellerDto;
import com.mgr.api.exception.BadRequestException;
import com.mgr.api.exception.NotFoundException;
import com.mgr.api.form.seller.CreateSellerForm;
import com.mgr.api.form.seller.UpdateSellerForm;
import com.mgr.api.mapper.SellerMapper;
import com.mgr.api.model.Account;
import com.mgr.api.model.Address;
import com.mgr.api.model.Seller;
import com.mgr.api.model.criteria.SellerCriteria;
import com.mgr.api.repository.AccountRepository;
import com.mgr.api.repository.address.AddressRepository;
import com.mgr.api.repository.seller.SellerRepository;
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
@RequestMapping("/v1/seller")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class SellerController extends ABasicController {

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private SellerMapper sellerMapper;

    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('SEL_C')")
    @Transactional
    public ApiMessageDto<String> register(@Valid @RequestBody CreateSellerForm createSellerForm, BindingResult bindingResult) {
        Long accountId = getCurrentUser();
        
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new NotFoundException("Account not found", ErrorCode.ACCOUNT_ERROR_NOT_FOUND));

        if (sellerRepository.findById(accountId).isPresent()) {
            throw new BadRequestException("Seller profile already exists", ErrorCode.USER_ERROR_EXISTED);
        }

        Seller seller = sellerMapper.fromCreateFormToEntity(createSellerForm);
        seller.setAccount(account);
        seller.setStatus(MgrConstant.STATUS_PENDING); // Initial status
        
        if (createSellerForm.getAddressId() != null) {
            Address address = addressRepository.findById(createSellerForm.getAddressId()).orElse(null);
            if (address != null) {
                seller.setAddress(address);
            }
        }

        sellerRepository.save(seller);
        return makeSuccessResponse(null, "Seller registered successfully");
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('SEL_U')")
    @Transactional
    public ApiMessageDto<String> update(@Valid @RequestBody UpdateSellerForm updateSellerForm, BindingResult bindingResult) {
        Long sellerId = getCurrentUser();
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new NotFoundException("Seller profile not found", ErrorCode.USER_ERROR_NOT_FOUND));

        sellerMapper.mappingUpdateFormToEntity(updateSellerForm, seller);
        
        if (updateSellerForm.getAddressId() != null) {
            Address address = addressRepository.findById(updateSellerForm.getAddressId()).orElse(null);
            if (address != null) {
                seller.setAddress(address);
            }
        }
//a
        sellerRepository.save(seller);
        return makeSuccessResponse(null, "Seller updated successfully");
    }

    @GetMapping(value = "/profile", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<SellerDto> profile() {
        Long sellerId = getCurrentUser();
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new NotFoundException("Seller not found", ErrorCode.USER_ERROR_NOT_FOUND));
        return makeSuccessResponse(sellerMapper.fromEntityToDto(seller), "Get Seller profile success");
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('SEL_L')")
    public ApiMessageDto<ResponseListDto<SellerDto>> list(SellerCriteria sellerCriteria, Pageable pageable) {
        Page<Seller> page = sellerRepository.findAll(sellerCriteria.getSpecification(), pageable);
        ResponseListDto<SellerDto> listDto = new ResponseListDto(
                sellerMapper.fromEntityListToDtoList(page.getContent()),
                page.getTotalElements(),
                page.getTotalPages()
        );
        return makeSuccessResponse(listDto, "Get list seller success");
    }
}
