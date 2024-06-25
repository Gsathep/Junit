package com.kidzcubicle.uaps.service.impl;

import com.kidzcubicle.uaps.entity.ChildrenDetails;
import com.kidzcubicle.uaps.entity.Parent;
import com.kidzcubicle.uaps.repository.ParentRepository;
import com.kidzcubicle.uaps.service.IParentService;
import com.kidzcubicle.uaps.service.mapper.ParentMapper;
import com.kidzcubicle.uaps.web.exception.handler.InvalidIdException;
import com.kidzcubicle.uaps.web.request.ParentDTO;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Parent}.
 */
@Slf4j
@Service
@Transactional
public class ParentServiceImpl implements IParentService {

    private final ParentRepository parentRepository;
    private final ParentMapper parentMapper;

    public ParentServiceImpl(ParentRepository parentRepository, ParentMapper parentMapper) {
        this.parentRepository = parentRepository;
        this.parentMapper = parentMapper;
    }

    @Override
    public ParentDTO createParent(ParentDTO parentDTO) {
        log.debug("Request to save parent  : {}", parentDTO);
        Parent parent = parentMapper.toEntity(parentDTO);
        if (parentDTO.getCreatedBy() == null) {
            parent.setCreatedBy("system");
        }
        Parent Parent = parentRepository.save(parent);
        return parentMapper.toDto(Parent);
    }

    @Override
    public Optional<ParentDTO> getParentById(Long id) {
        log.debug("REST request to get parent by id: {}", id);
        return parentRepository.findById(id).map(parentMapper::toDto);

    }

    @Override
    public ParentDTO partialUpdateParent(Long id, ParentDTO parentDTO) throws InvalidIdException {
        log.debug("Request to partially update parent : {}", parentDTO);

        return parentRepository.findById(id).map(existingParent -> {
            parentMapper.partialUpdate(existingParent, parentDTO);
            Parent updatedParent = parentRepository.save(existingParent);
            return parentMapper.toDto(updatedParent);
        }).orElseThrow(InvalidIdException::new);
    }

}
