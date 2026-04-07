package com.mgr.api.controller;

import com.mgr.api.constant.MgrConstant;
import com.mgr.api.dto.ApiMessageDto;
import com.mgr.api.dto.ErrorCode;
import com.mgr.api.dto.ResponseListDto;
import com.mgr.api.dto.nation.NationDto;
import com.mgr.api.exception.NotFoundException;
import com.mgr.api.form.nation.CreateNationForm;
import com.mgr.api.form.nation.UpdateNationForm;
import com.mgr.api.mapper.NationMapper;
import com.mgr.api.model.Nation;
import com.mgr.api.model.criteria.NationCriteria;
import com.mgr.api.repository.nation.NationRepository;
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
@RequestMapping("/v1/nation")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class NationController extends ABasicController {

    @Autowired
    private NationRepository nationRepository;

    @Autowired
    private NationMapper nationMapper;

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('NAT_C')")
    @Transactional
    public ApiMessageDto<String> create(@Valid @RequestBody CreateNationForm createNationForm, BindingResult bindingResult) {
        Nation nation = nationMapper.fromCreateFormToEntity(createNationForm);
        nation.setStatus(MgrConstant.STATUS_ACTIVE);
        nationRepository.save(nation);
        return makeSuccessResponse(null, "Create Nation success");
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('NAT_U')")
    @Transactional
    public ApiMessageDto<String> update(@Valid @RequestBody UpdateNationForm updateNationForm, BindingResult bindingResult) {
        Nation nation = nationRepository.findById(updateNationForm.getId())
                .orElseThrow(() -> new NotFoundException("Nation not found", ErrorCode.PERMISSION_ERROR_NOT_FOUND)); // Reuse permission or add NAT_ERROR

        nationMapper.mappingUpdateFormToEntity(updateNationForm, nation);
        if (updateNationForm.getStatus() != null) {
            nation.setStatus(updateNationForm.getStatus());
        }
        nationRepository.save(nation);
        return makeSuccessResponse(null, "Update Nation success");
    }

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<NationDto> get(@PathVariable("id") Long id) {
        Nation nation = nationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Nation not found", ErrorCode.PERMISSION_ERROR_NOT_FOUND));
        return makeSuccessResponse(nationMapper.fromEntityToDto(nation), "Get Nation success");
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListDto<NationDto>> list(NationCriteria nationCriteria, Pageable pageable) {
        Page<Nation> page = nationRepository.findAll(nationCriteria.getSpecification(), pageable);
        ResponseListDto<NationDto> listDto = new ResponseListDto(
                nationMapper.fromEntityListToDtoList(page.getContent()),
                page.getTotalElements(),
                page.getTotalPages()
        );
        return makeSuccessResponse(listDto, "Get list nation success");
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('NAT_D')")
    @Transactional
    public ApiMessageDto<String> delete(@PathVariable("id") Long id) {
        Nation nation = nationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Nation not found", ErrorCode.PERMISSION_ERROR_NOT_FOUND));
        
        nation.setStatus(MgrConstant.STATUS_DELETE);
        nationRepository.save(nation);
        return makeSuccessResponse(null, "Delete Nation success");
    }
}
