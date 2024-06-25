package com.kidzcubicle.uaps.web.controller;

import com.kidzcubicle.uaps.repository.ChildrenDetailsRepository;
import com.kidzcubicle.uaps.repository.ParentRepository;
import com.kidzcubicle.uaps.service.IChildrenDetailsService;
import com.kidzcubicle.uaps.web.exception.handler.BadRequestAlertException;
import com.kidzcubicle.uaps.web.request.ChildrenDetailsDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ChildrenDetailsControllerTest {

    @Mock
    private IChildrenDetailsService childrenDetailsService;

    @Mock
    private ChildrenDetailsRepository childrenDetailsRepository;
    @Mock
    private ParentRepository parentRepository;

    @InjectMocks
    private ChildrenDetailsController childrenDetailsController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    void testCreateChildrenDetails_Success() throws URISyntaxException {
        ChildrenDetailsDTO childrenDetailsDTO = new ChildrenDetailsDTO();
        childrenDetailsDTO.setParentId(1L);
        when(parentRepository.existsById(anyLong())).thenReturn(true);
        when(childrenDetailsService.createChildrenDetails(any(ChildrenDetailsDTO.class))).thenReturn(childrenDetailsDTO);

        ResponseEntity<ChildrenDetailsDTO> response = childrenDetailsController.createChildrenDetails(childrenDetailsDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("/api/childrenDetails" + childrenDetailsDTO.getId(), response.getHeaders().getLocation().toString());
    }

    @Test
    void createChildrenDetails_shouldThrowBadRequestExceptionWhenIdExists() {
        ChildrenDetailsDTO inputDTO = new ChildrenDetailsDTO();
        inputDTO.setId(1L);
        BadRequestAlertException exception = assertThrows(BadRequestAlertException.class, () -> childrenDetailsController.createChildrenDetails(inputDTO));
        assertEquals("A new ChildrenDetails cannot already have an ID", exception.getMessage());
    }

    @Test
    void getChildrenDetailsById_shouldReturnOkResponseWhenIdExists() {
        Long id = 1L;
        ChildrenDetailsDTO mockDTO = new ChildrenDetailsDTO();
        when(childrenDetailsService.getChildrenDetailsById(id)).thenReturn(Optional.of(mockDTO));
        when(childrenDetailsRepository.existsById(id)).thenReturn(true);

        ResponseEntity<ChildrenDetailsDTO> response = childrenDetailsController.getChildrenDetailsById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }


    @Test
    void getChildrenDetailsById_shouldThrowBadRequestWhenIdIsNull() {
        assertThrows(BadRequestAlertException.class, () -> childrenDetailsController.getChildrenDetailsById(null));
    }

    @Test
    void getChildrenDetailsById_shouldThrowBadRequestWhenIdNotFound() {
        Long id = 1L;
        when(childrenDetailsRepository.existsById(id)).thenReturn(false);

        assertThrows(BadRequestAlertException.class, () -> childrenDetailsController.getChildrenDetailsById(id));
    }

    @Test
    void partialUpdateChildrenDetails_shouldReturnOkResponse() {
        Long id = 1L;
        ChildrenDetailsDTO inputDTO = new ChildrenDetailsDTO();
        inputDTO.setId(id);
        when(childrenDetailsRepository.existsById(id)).thenReturn(true);
        when(childrenDetailsService.partialUpdateChildrenDetails(id, inputDTO)).thenReturn(inputDTO);
        ResponseEntity<ChildrenDetailsDTO> response = childrenDetailsController.partialUpdateChildrenDetails(id, inputDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void partialUpdateChildrenDetails_shouldThrowBadRequestExceptionWhenIdIsNull() {
        Long id = null;
        ChildrenDetailsDTO inputDTO = new ChildrenDetailsDTO();
        inputDTO.setId(id);
        BadRequestAlertException exception = assertThrows(BadRequestAlertException.class, () -> childrenDetailsController.partialUpdateChildrenDetails(id, inputDTO));
        assertEquals("Invalid id", exception.getMessage());
    }

    @Test
    void getChildByParentId_shouldReturnOkResponseWhenParentIdExists() {
        Long parentId = 1L;
        List<ChildrenDetailsDTO> mockDTOs = new ArrayList<>();
        mockDTOs.add(new ChildrenDetailsDTO());
        mockDTOs.add(new ChildrenDetailsDTO());
        when(parentRepository.existsById(parentId)).thenReturn(true);
        when(childrenDetailsService.getChildByParentId(parentId)).thenReturn(mockDTOs);
        ResponseEntity<List<ChildrenDetailsDTO>> response = childrenDetailsController.getChildByParentId(parentId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size()); // Ensure correct number of children details returned
    }

    @Test
    void getChildByParentId_shouldThrowBadRequestWhenParentIdNotFound() {
        Long parentId = 1L;
        when(parentRepository.existsById(parentId)).thenReturn(false);
        assertThrows(BadRequestAlertException.class, () -> childrenDetailsController.getChildByParentId(parentId));
    }

    @Test
    void getChildByParentId_shouldReturnEmptyListWhenNoChildrenDetailsFound() {
        Long parentId = 1L;
        List<ChildrenDetailsDTO> mockDTOs = new ArrayList<>();
        when(parentRepository.existsById(parentId)).thenReturn(true);
        when(childrenDetailsService.getChildByParentId(parentId)).thenReturn(mockDTOs);
        ResponseEntity<List<ChildrenDetailsDTO>> response = childrenDetailsController.getChildByParentId(parentId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().size());
    }
}
