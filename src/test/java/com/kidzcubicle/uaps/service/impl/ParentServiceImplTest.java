package com.kidzcubicle.uaps.service.impl;

import com.kidzcubicle.uaps.entity.ChildrenDetails;
import com.kidzcubicle.uaps.entity.Parent;
import com.kidzcubicle.uaps.repository.ParentRepository;
import com.kidzcubicle.uaps.service.mapper.ParentMapper;
import com.kidzcubicle.uaps.web.request.ChildrenDetailsDTO;
import com.kidzcubicle.uaps.web.request.ParentDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ParentServiceImplTest {
    @InjectMocks
    private ParentServiceImpl parentService;
    @Mock
    private ParentRepository parentRepository;
    @Mock
    private ParentMapper parentMapper;

    @Test
    void createParent() {
        ParentDTO parentDTO = new ParentDTO();
        Parent parent = new Parent();
        when(parentMapper.toEntity(any(ParentDTO.class))).thenReturn(parent);
        when(parentRepository.save(any(Parent.class))).thenReturn(parent);
        when(parentMapper.toDto(any(Parent.class))).thenReturn(parentDTO);
        ParentDTO saved = parentService.createParent(parentDTO);
        assertNotNull(saved);
        verify(parentRepository).save(parent);

    }

    @Test
    void getParentById() {
        Long parentId = 1L;
        ParentDTO parentDTO = new ParentDTO();
        Parent parent = new Parent();
        when(parentRepository.findById(parentId)).thenReturn(java.util.Optional.of(parent));
        when(parentMapper.toDto(parent)).thenReturn(parentDTO);
        Optional<ParentDTO> result = parentService.getParentById(parentId);
        assertNotNull(result);
        verify(parentRepository).findById(parentId);
        verify(parentMapper).toDto(parent);

    }

    @Test
    void partialUpdateParent() {
        Long detailsId = 1L;
        Parent parent = new Parent();
        ParentDTO parentDTO = new ParentDTO();
        when(parentRepository.findById(detailsId)).thenReturn(Optional.of(parent));
        when(parentMapper.toDto(any(Parent.class))).thenReturn(parentDTO);
        when(parentRepository.save(any(Parent.class))).thenReturn(new Parent());
        ParentDTO parentDTO1 = parentService.partialUpdateParent(detailsId, parentDTO);
        assertNotNull(parentDTO1);
        verify(parentRepository).findById(detailsId);
        verify(parentMapper).partialUpdate(eq(parent), eq(parentDTO));
        verify(parentRepository).save(any(Parent.class));
    }
}