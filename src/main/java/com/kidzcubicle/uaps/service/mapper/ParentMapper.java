package com.kidzcubicle.uaps.service.mapper;

import com.kidzcubicle.uaps.entity.Parent;
import com.kidzcubicle.uaps.web.request.ParentDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ParentMapper extends EntityMapper<ParentDTO, Parent> {



}
