package com.kidzcubicle.uaps.service.impl;

import com.kidzcubicle.uaps.entity.ChildrenDetails;
import com.kidzcubicle.uaps.repository.ChildrenDetailsRepository;
import com.kidzcubicle.uaps.service.IChildrenDetailsService;
import com.kidzcubicle.uaps.service.mapper.ChildrenDetailsMapper;
import com.kidzcubicle.uaps.web.exception.handler.InvalidIdException;
import com.kidzcubicle.uaps.web.request.ChildrenDetailsDTO;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ChildrenDetails}.
 */
@Slf4j
@Service
@Transactional
public class ChildrenDetailsServiceImpl implements IChildrenDetailsService {

    private final ChildrenDetailsRepository childrenDetailsRepository;
    private final ChildrenDetailsMapper childrenDetailsMapper;

    @Autowired
    public ChildrenDetailsServiceImpl(ChildrenDetailsRepository childrenDetailsRepository, ChildrenDetailsMapper childrenDetailsMapper) {
        this.childrenDetailsRepository = childrenDetailsRepository;
        this.childrenDetailsMapper = childrenDetailsMapper;
    }


    @Override
    public ChildrenDetailsDTO createChildrenDetails(ChildrenDetailsDTO childrenDetailsDTO) {
        log.debug("Request to save ChildrenDetails  : {}", childrenDetailsDTO);
        ChildrenDetails childrenDetails = childrenDetailsMapper.toEntity(childrenDetailsDTO);
        ChildrenDetails childrenDetails1 = childrenDetailsRepository.save(childrenDetails);
        return childrenDetailsMapper.toDto(childrenDetails1);

    }

    @Override
    public Optional<ChildrenDetailsDTO> getChildrenDetailsById(Long id) {
        log.debug("REST request to get ChildrenDetails by id: {}", id);
        return childrenDetailsRepository.findById(id).map(childrenDetailsMapper::toDto);
    }

    @Override
    public ChildrenDetailsDTO partialUpdateChildrenDetails(Long id, ChildrenDetailsDTO childrenDetailsDTO) {
        log.debug("Request to partially update ChildrenDetails : {}", childrenDetailsDTO);

        return childrenDetailsRepository.findById(id).map(existingParent -> {
            childrenDetailsMapper.partialUpdate(existingParent, childrenDetailsDTO);
            ChildrenDetails childrenDetails = childrenDetailsRepository.save(existingParent);
            return childrenDetailsMapper.toDto(childrenDetails);
        }).orElseThrow(InvalidIdException::new);
    }

    @Override
    public List<ChildrenDetailsDTO> getChildByParentId(Long id) {
        log.debug("Request to get child by Prent ID: {}", id);
        List<ChildrenDetails> childrenDetails = childrenDetailsRepository.findByParentId(id);
        return childrenDetails.stream().map(childrenDetailsMapper::toDto).collect(Collectors.toList());
    }
}
