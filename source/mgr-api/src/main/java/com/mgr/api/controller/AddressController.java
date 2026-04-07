package com.mgr.api.controller;

import com.mgr.api.constant.MgrConstant;
import com.mgr.api.dto.ApiMessageDto;
import com.mgr.api.dto.ErrorCode;
import com.mgr.api.dto.address.AddressDto;
import com.mgr.api.exception.NotFoundException;
import com.mgr.api.form.address.CreateAddressForm;
import com.mgr.api.form.address.UpdateAddressForm;
import com.mgr.api.mapper.AddressMapper;
import com.mgr.api.model.Address;
import com.mgr.api.model.Nation;
import com.mgr.api.model.User;
import com.mgr.api.repository.address.AddressRepository;
import com.mgr.api.repository.nation.NationRepository;
import com.mgr.api.repository.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/address")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class AddressController extends ABasicController {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NationRepository nationRepository;

    @Autowired
    private AddressMapper addressMapper;

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADDR_C')")
    @Transactional
    public ApiMessageDto<String> create(@Valid @RequestBody CreateAddressForm createAddressForm, BindingResult bindingResult) {
        Long userId = getCurrentUser();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User profile not found", ErrorCode.USER_ERROR_NOT_FOUND));

        Address address = addressMapper.fromCreateFormToEntity(createAddressForm);
        address.setUser(user);
        
        Nation nation = nationRepository.findById(createAddressForm.getNationId())
                .orElseThrow(() -> new NotFoundException("Nation not found", ErrorCode.PERMISSION_ERROR_NOT_FOUND));
        address.setNation(nation);

        if (Boolean.TRUE.equals(createAddressForm.getIsDefault())) {
            addressRepository.findAllByUser(user).forEach(a -> {
                a.setIsDefault(false);
                addressRepository.save(a);
            });
        }

        addressRepository.save(address);
        return makeSuccessResponse(null, "Create Address success");
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADDR_U')")
    @Transactional
    public ApiMessageDto<String> update(@Valid @RequestBody UpdateAddressForm updateAddressForm, BindingResult bindingResult) {
        Long userId = getCurrentUser();
        Address address = addressRepository.findById(updateAddressForm.getId())
                .orElseThrow(() -> new NotFoundException("Address not found", ErrorCode.USER_ERROR_NOT_FOUND));

        if (!address.getUser().getId().equals(userId)) {
            throw new NotFoundException("Address not found", ErrorCode.USER_ERROR_NOT_FOUND);
        }

        addressMapper.mappingUpdateFormToEntity(updateAddressForm, address);
        
        if (updateAddressForm.getNationId() != null) {
            Nation nation = nationRepository.findById(updateAddressForm.getNationId()).orElse(null);
            address.setNation(nation);
        }

        if (Boolean.TRUE.equals(updateAddressForm.getIsDefault())) {
            addressRepository.findAllByUser(address.getUser()).forEach(a -> {
                if (!a.getId().equals(address.getId())) {
                    a.setIsDefault(false);
                    addressRepository.save(a);
                }
            });
        }

        addressRepository.save(address);
        return makeSuccessResponse(null, "Update Address success");
    }

    @GetMapping(value = "/my-addresses", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<List<AddressDto>> listMyAddresses() {
        Long userId = getCurrentUser();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found", ErrorCode.USER_ERROR_NOT_FOUND));
        
        List<Address> addresses = addressRepository.findAllByUser(user);
        return makeSuccessResponse(addressMapper.fromEntityListToDtoList(addresses), "Get list address success");
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADDR_D')")
    @Transactional
    public ApiMessageDto<String> delete(@PathVariable("id") Long id) {
        Long userId = getCurrentUser();
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Address not found", ErrorCode.USER_ERROR_NOT_FOUND));

        if (!address.getUser().getId().equals(userId)) {
            throw new NotFoundException("Address not found", ErrorCode.USER_ERROR_NOT_FOUND);
        }

        addressRepository.delete(address);
        return makeSuccessResponse(null, "Delete Address success");
    }
}
