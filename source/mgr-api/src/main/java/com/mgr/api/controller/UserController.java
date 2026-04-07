package com.mgr.api.controller;

import com.mgr.api.constant.MgrConstant;
import com.mgr.api.dto.ApiMessageDto;
import com.mgr.api.dto.ErrorCode;
import com.mgr.api.dto.ResponseListDto;
import com.mgr.api.dto.user.UserDto;
import com.mgr.api.exception.BadRequestException;
import com.mgr.api.exception.NotFoundException;
import com.mgr.api.form.address.CreateAddressForm;
import com.mgr.api.form.address.UpdateAddressForm;
import com.mgr.api.form.user.CreateUserForm;
import com.mgr.api.form.user.UpdateUserForm;
import com.mgr.api.mapper.AddressMapper;
import com.mgr.api.mapper.UserMapper;
import com.mgr.api.model.Account;
import com.mgr.api.model.Address;
import com.mgr.api.model.Nation;
import com.mgr.api.model.User;
import com.mgr.api.model.criteria.UserCriteria;
import com.mgr.api.repository.AccountRepository;
import com.mgr.api.repository.address.AddressRepository;
import com.mgr.api.repository.nation.NationRepository;
import com.mgr.api.repository.user.UserRepository;
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
@RequestMapping("/v1/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class UserController extends ABasicController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private NationRepository nationRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AddressMapper addressMapper;

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('USR_C')")
    @Transactional
    public ApiMessageDto<String> create(@Valid @RequestBody CreateUserForm createUserForm, BindingResult bindingResult) {
        Long accountId = getCurrentUser(); // Get from token
        
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new NotFoundException("Account not found", ErrorCode.ACCOUNT_ERROR_NOT_FOUND));

        User existingUser = userRepository.findById(accountId).orElse(null);
        if (existingUser != null) {
            throw new BadRequestException("User profile already exists for this account", ErrorCode.USER_ERROR_EXISTED);
        }

        User user = userMapper.fromCreateFormToEntity(createUserForm);
        user.setAccount(account);
        userRepository.save(user);

        // Handle addresses
        if (createUserForm.getAddresses() != null && !createUserForm.getAddresses().isEmpty()) {
            for (CreateAddressForm addressForm : createUserForm.getAddresses()) {
                Address address = addressMapper.fromCreateFormToEntity(addressForm);
                Nation nation = nationRepository.findById(addressForm.getNationId()).orElse(null);
                if (nation != null) {
                    address.setNation(nation);
                    address.setUser(user);
                    addressRepository.save(address);
                }
            }
        }

        return makeSuccessResponse(null, "Create User success");
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('USR_U')")
    @Transactional
    public ApiMessageDto<String> update(@Valid @RequestBody UpdateUserForm updateUserForm, BindingResult bindingResult) {
        Long userId = getCurrentUser(); // Identifier from token
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found", ErrorCode.USER_ERROR_NOT_FOUND));

        userMapper.mappingUpdateFormToEntity(updateUserForm, user);
        if (updateUserForm.getStatus() != null) {
            user.setStatus(updateUserForm.getStatus());
        }
        userRepository.save(user);

        // Bulk address updates
        if (updateUserForm.getAddresses() != null) {
            for (UpdateAddressForm addressForm : updateUserForm.getAddresses()) {
                Address address = addressRepository.findById(addressForm.getId()).orElse(null);
                if (address != null && address.getUser().getId().equals(user.getId())) {
                    addressMapper.mappingUpdateFormToEntity(addressForm, address);
                    if (addressForm.getNationId() != null) {
                        Nation nation = nationRepository.findById(addressForm.getNationId()).orElse(null);
                        address.setNation(nation);
                    }
                    if (Boolean.TRUE.equals(addressForm.getIsDefault())) {
                        addressRepository.findAllByUser(user).forEach(a -> {
                            if (!a.getId().equals(address.getId())) {
                                a.setIsDefault(false);
                                addressRepository.save(a);
                            }
                        });
                    }
                    addressRepository.save(address);
                }
            }
        }

        return makeSuccessResponse(null, "Update User success");
    }

    @GetMapping(value = "/profile", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('USR_V')")
    public ApiMessageDto<UserDto> profile() {
        Long userId = getCurrentUser();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found", ErrorCode.USER_ERROR_NOT_FOUND));
        
        return makeSuccessResponse(userMapper.fromEntityToDto(user), "Get Profile success");
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('USR_L')")
    public ApiMessageDto<ResponseListDto<UserDto>> list(UserCriteria userCriteria, Pageable pageable) {
        Page<User> page = userRepository.findAll(userCriteria.getSpecification(), pageable);
        ResponseListDto<UserDto> responseListDto = new ResponseListDto(
                userMapper.fromEntityListToDtoList(page.getContent()),
                page.getTotalElements(),
                page.getTotalPages()
        );
        return makeSuccessResponse(responseListDto, "Get list user success");
    }

    @DeleteMapping(value = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('USR_D')")
    @Transactional
    public ApiMessageDto<String> delete() {
        Long userId = getCurrentUser();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found", ErrorCode.USER_ERROR_NOT_FOUND));
        
        user.setStatus(MgrConstant.STATUS_DELETE);
        userRepository.save(user);
        
        return makeSuccessResponse(null, "Delete User success");
    }
}
