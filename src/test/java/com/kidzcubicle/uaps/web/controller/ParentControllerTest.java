package com.kidzcubicle.uaps.web.controller;

import com.kidzcubicle.uaps.entity.Parent;
import com.kidzcubicle.uaps.repository.ParentRepository;
import com.kidzcubicle.uaps.service.IParentService;
import com.kidzcubicle.uaps.web.exception.handler.BadRequestAlertException;
import com.kidzcubicle.uaps.web.exception.handler.EmailAlreadyUsedException;
import com.kidzcubicle.uaps.web.request.ParentDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ParentControllerTest {

    @Mock
    private IParentService parentService;

    @Mock
    private ParentRepository parentRepository;

    @InjectMocks
    private ParentController parentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void createParent_withNullIdAndExistingSubId_shouldReturnOkResponseForPartialUpdate() throws URISyntaxException {
        // Given
        ParentDTO inputDTO = new ParentDTO();
        inputDTO.setId(null);
        inputDTO.setSubId("sub123");
        when(parentRepository.existsBySubId(inputDTO.getSubId())).thenReturn(true);
        when(parentRepository.findBySubId(inputDTO.getSubId())).thenReturn(new Parent());
        when(parentService.partialUpdateParent(any(Long.class), any(ParentDTO.class))).thenReturn(inputDTO);
        ResponseEntity<ParentDTO> response = parentController.createParent(inputDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void createParent_withNonNullId_shouldThrowBadRequestException() {
        // Given
        ParentDTO inputDTO = new ParentDTO();
        inputDTO.setId(1L);
        BadRequestAlertException exception = assertThrows(BadRequestAlertException.class, () -> parentController.createParent(inputDTO));
        assertEquals("Invalid id", exception.getMessage());
    }

    @Test
    void createParent_withNonExistingSubId_shouldReturnCreatedResponse() throws URISyntaxException {
        ParentDTO inputDTO = new ParentDTO();
        inputDTO.setId(null);
        inputDTO.setSubId("sub123");
        when(parentRepository.existsBySubId(inputDTO.getSubId())).thenReturn(false);
        when(parentService.createParent(inputDTO)).thenReturn(inputDTO);
        URI expectedUri = new URI("/register" + inputDTO.getId());
        ResponseEntity<ParentDTO> response = parentController.createParent(inputDTO);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expectedUri, response.getHeaders().getLocation());
    }


    @Test
    public void testGetParentById_ValidId() {
        parentService = mock(IParentService.class);
        parentRepository = mock(ParentRepository.class);
        parentController = new ParentController(parentService, parentRepository);
        Long validId = 1L;
        ParentDTO mockParentDTO = new ParentDTO();
        mockParentDTO.setId(validId);
        when(parentRepository.existsById(validId)).thenReturn(true);
        when(parentService.getParentById(validId)).thenReturn(Optional.of(mockParentDTO));
        ResponseEntity<ParentDTO> response = parentController.getParentById(validId);
        assert response.getStatusCode().equals(HttpStatus.OK);
        assert response.getBody() != null;
        assert response.getBody().getId().equals(validId);
    }


    @Test
    void testGetParentById_InvalidId() {
        Long id = null;
        assertThrows(BadRequestAlertException.class, () -> parentController.getParentById(id));
    }


    @Test
    void testGetParentById_ParentNotFound() {
        Long id = 1L;
        when(parentService.getParentById(anyLong())).thenReturn(Optional.empty());
        assertThrows(BadRequestAlertException.class, () -> parentController.getParentById(id));
    }

    @Test
    void partialUpdateParent_shouldReturnOkResponse() {
        Long id = 1L;
        ParentDTO inputDTO = new ParentDTO();
        inputDTO.setId(id);
        when(parentRepository.existsById(id)).thenReturn(true);
        when(parentService.partialUpdateParent(id, inputDTO)).thenReturn(inputDTO);
        ResponseEntity<ParentDTO> response = parentController.partialUpdateParent(id, inputDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void partialUpdateParent_shouldThrowBadRequestExceptionWhenIdIsNull() {
        Long id = null;
        ParentDTO inputDTO = new ParentDTO();
        inputDTO.setId(id);
        BadRequestAlertException exception = assertThrows(BadRequestAlertException.class, () -> parentController.partialUpdateParent(id, inputDTO));
        assertEquals("Invalid id", exception.getMessage());
    }
}
