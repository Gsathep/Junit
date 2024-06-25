package com.kidzcubicle.uaps.service.impl;

import com.kidzcubicle.uaps.entity.ChildrenDetails;
import com.kidzcubicle.uaps.repository.ChildrenDetailsRepository;
import com.kidzcubicle.uaps.service.mapper.ChildrenDetailsMapper;
import com.kidzcubicle.uaps.web.exception.handler.InvalidIdException;
import com.kidzcubicle.uaps.web.request.ChildrenDetailsDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

@ExtendWith(MockitoExtension.class)
class ChildrenDetailsServiceImplTest {

    @Mock
    private ChildrenDetailsRepository childrenDetailsRepository;
    @InjectMocks
    private ChildrenDetailsServiceImpl childrenDetailsService;
    @Mock
    private ChildrenDetailsMapper childrenDetailsMapper;

    @BeforeEach
    void setUp() {
        this.childrenDetailsService = new ChildrenDetailsServiceImpl(this.childrenDetailsRepository, this.childrenDetailsMapper);
    }

    @Test
    void createChildrenDetails() {
        ChildrenDetailsDTO childrenDetailsDTO = new ChildrenDetailsDTO();
        ChildrenDetails childrenDetails = new ChildrenDetails();
        when(childrenDetailsMapper.toEntity(any(ChildrenDetailsDTO.class))).thenReturn(childrenDetails);
        when(childrenDetailsRepository.save(any(ChildrenDetails.class))).thenReturn(childrenDetails);
        when(childrenDetailsMapper.toDto(any(ChildrenDetails.class))).thenReturn(childrenDetailsDTO);
        ChildrenDetailsDTO saved = childrenDetailsService.createChildrenDetails(childrenDetailsDTO);
        assertNotNull(saved);
        verify(childrenDetailsRepository).save(childrenDetails);
    }

    @Test
    void getChildrenDetailsById() {
        Long Id = 1L;
        ChildrenDetails childrenDetails = new ChildrenDetails();
        ChildrenDetailsDTO childrenDetailsDTO = new ChildrenDetailsDTO();
        when(childrenDetailsRepository.findById(Id)).thenReturn(java.util.Optional.of(childrenDetails));
        when(childrenDetailsMapper.toDto(childrenDetails)).thenReturn(childrenDetailsDTO);
        Optional<ChildrenDetailsDTO> retrievedDetails = childrenDetailsService.getChildrenDetailsById(Id);
        assertNotNull(retrievedDetails);
        verify(childrenDetailsRepository).findById(Id);
        verify(childrenDetailsMapper).toDto(childrenDetails);
    }

    @Test
    void partialUpdateChildrenDetails() {
        Long detailsId = 1L;
        ChildrenDetails childrenDetails = new ChildrenDetails();
        ChildrenDetailsDTO childrenDetailsDTO = new ChildrenDetailsDTO();
        when(childrenDetailsRepository.findById(detailsId)).thenReturn(Optional.of(childrenDetails));
        when(childrenDetailsMapper.toDto(any(ChildrenDetails.class))).thenReturn(childrenDetailsDTO);
        when(childrenDetailsRepository.save(any(ChildrenDetails.class))).thenReturn(new ChildrenDetails());
        ChildrenDetailsDTO updatedDetails = childrenDetailsService.partialUpdateChildrenDetails(detailsId, childrenDetailsDTO);
        assertNotNull(updatedDetails);
        verify(childrenDetailsRepository).findById(detailsId);
        verify(childrenDetailsMapper).partialUpdate(eq(childrenDetails), eq(childrenDetailsDTO));
        verify(childrenDetailsRepository).save(any(ChildrenDetails.class));
    }

    @Test
    void getChildByParentId() {
        Long parentId = 1L;
        List<ChildrenDetails> childrenDetails = new ArrayList<>();
        when(childrenDetailsRepository.findByParentId(parentId)).thenReturn(childrenDetails);
        List<ChildrenDetailsDTO> childrenDetailsDTOS = childrenDetailsService.getChildByParentId(parentId);
        assertNotNull(childrenDetailsDTOS);
        verify(childrenDetailsRepository).findByParentId(parentId);
    }
}