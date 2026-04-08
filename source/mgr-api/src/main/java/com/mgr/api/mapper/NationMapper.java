package com.mgr.api.mapper;

import com.mgr.api.dto.nation.NationDto;
import com.mgr.api.form.nation.CreateNationForm;
import com.mgr.api.form.nation.UpdateNationForm;
import com.mgr.api.model.Nation;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface NationMapper {

    @Mapping(source = "name", target = "name")
    @Mapping(source = "kind", target = "kind")
    @Mapping(source = "postCode", target = "postCode")
    @Mapping(source = "parentId", target = "parent", qualifiedByName = "fromParentId")
    Nation fromCreateFormToEntity(CreateNationForm form);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "kind", target = "kind")
    @Mapping(source = "postCode", target = "postCode")
    @Mapping(source = "parentId", target = "parent", qualifiedByName = "fromParentId")
    void mappingUpdateFormToEntity(UpdateNationForm form, @MappingTarget Nation nation);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "kind", target = "kind")
    @Mapping(source = "postCode", target = "postCode")
    @Mapping(source = "parent.id", target = "parentId")
    @Mapping(source = "status", target = "status")
    @Mapping(target = "createdDate", expression = "java(com.mgr.api.utils.DateUtils.formatDate(nation.getCreatedDate(), com.mgr.api.constant.MgrConstant.DATE_TIME_FORMAT))")
    @Mapping(target = "modifiedDate", expression = "java(com.mgr.api.utils.DateUtils.formatDate(nation.getModifiedDate(), com.mgr.api.constant.MgrConstant.DATE_TIME_FORMAT))")
    NationDto fromEntityToDto(Nation nation);

    @IterableMapping(elementTargetType = NationDto.class)
    List<NationDto> fromEntityListToDtoList(List<Nation> list);

    @Named("fromParentId")
    default Nation fromParentId(Long parentId) {
        if (parentId == null) {
            return null;
        }
        Nation parent = new Nation();
        parent.setId(parentId);
        return parent;
    }
}

