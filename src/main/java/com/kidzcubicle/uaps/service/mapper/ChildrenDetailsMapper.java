package com.kidzcubicle.uaps.service.mapper;

import com.kidzcubicle.uaps.entity.ChildrenDetails;
import com.kidzcubicle.uaps.web.request.ChildrenDetailsDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChildrenDetailsMapper extends EntityMapper<ChildrenDetailsDTO, ChildrenDetails> {

    @Mapping(target = "parent.id", source = "parentId")
    ChildrenDetails toEntity(ChildrenDetailsDTO childrenDetailsDTO);

    @Mapping(target = "parentId", source = "parent.id")
    ChildrenDetailsDTO toDto(ChildrenDetails childrenDetails);
}