package com.kidzcubicle.uaps.web.controller;

import com.kidzcubicle.uaps.repository.ChildrenDetailsRepository;
import com.kidzcubicle.uaps.repository.ParentRepository;
import com.kidzcubicle.uaps.service.IChildrenDetailsService;
import com.kidzcubicle.uaps.web.exception.handler.BadRequestAlertException;
import com.kidzcubicle.uaps.web.request.ChildrenDetailsDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import static org.hibernate.id.IdentifierGenerator.ENTITY_NAME;

/**
 * REST controller for managing {@link com.kidzcubicle.uaps.entity.ChildrenDetails}.
 */

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class ChildrenDetailsController {

    private final IChildrenDetailsService childrenDetailsService;

    private final ChildrenDetailsRepository childrenDetailsRepository;

    private final ParentRepository parentRepository;

    public static final String ENTITY_NAME = "children-details";

    @Autowired
    public ChildrenDetailsController(IChildrenDetailsService childrenDetailsService, ChildrenDetailsRepository childrenDetailsRepository, ParentRepository parentRepository) {
        this.childrenDetailsService = childrenDetailsService;
        this.childrenDetailsRepository = childrenDetailsRepository;
        this.parentRepository = parentRepository;
    }


    /**
     * {@code POST  /childrenDetails} : Create a new childrenDetails.
     *
     * @param childrenDetailsDTO the childrenDetailsDTO to create.
     *                           the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new childrenDetailsDTO, or with status {@code 400 (Bad Request)} if the childrenDetails has already an ID.
     * @throws URISyntaxException
     */

    @PostMapping("/children-details")
    public ResponseEntity<ChildrenDetailsDTO> createChildrenDetails(@RequestBody ChildrenDetailsDTO childrenDetailsDTO) throws URISyntaxException {
        log.debug("REST request to save ChildrenDetails : {}", childrenDetailsDTO);
        if (childrenDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new ChildrenDetails cannot already have an ID", ENTITY_NAME, "id is already present");
        }
        if (!parentRepository.existsById(childrenDetailsDTO.getParentId())) {
            throw new BadRequestAlertException("Parent id not found", ENTITY_NAME, "parent-id-not-found");
        } else {
            ChildrenDetailsDTO createdParentDTO = childrenDetailsService.createChildrenDetails(childrenDetailsDTO);
            return ResponseEntity.created(new URI("/api/childrenDetails" + createdParentDTO.getId())).body(createdParentDTO);
        }
    }

    /**
     * {@code GET  /children-details/:id} : get the "id" ChildrenDetails.
     *
     * @param id the id of the ChildrenDetails to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ChildrenDetails, or with status {@code 404 (Not Found)}.
     */

    @GetMapping("/children-details/{id}")
    public ResponseEntity<ChildrenDetailsDTO> getChildrenDetailsById(@PathVariable Long id) {
        log.debug("REST request to retrieve ChildrenDetails with ID: {}", id);
        if (id == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "id-null");
        }
        if (!childrenDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("ChildrenDetails id not found", ENTITY_NAME, "childrenDetails-id-not-found");
        }
        Optional<ChildrenDetailsDTO> childrenDetailsDTO = childrenDetailsService.getChildrenDetailsById(id);
        return ResponseEntity.ok().body(childrenDetailsDTO.get());
    }

    /**
     * {@code PUT  /children-details/:id} : Updates an existing childrenDetails.
     *
     * @param id                 the id of the childrenDetailsDTO to save.
     * @param childrenDetailsDTO the childrenDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated childrenDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the childrenDetailsDTO is not valid,
     */

    @PatchMapping("/children-details/{id}")
    public ResponseEntity<ChildrenDetailsDTO> partialUpdateChildrenDetails(@PathVariable Long id, @RequestBody ChildrenDetailsDTO childrenDetailsDTO) {
        log.debug("REST request to partial update ChildrenDetails with ID: {}", id);

        if (childrenDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "id-null");
        }
        if (!childrenDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("ChildrenDetails id not found", ENTITY_NAME, "childrenDetails-id-not-found");
        }

        ChildrenDetailsDTO updatedChildrenDetails = childrenDetailsService.partialUpdateChildrenDetails(id, childrenDetailsDTO);
        return new ResponseEntity<>(updatedChildrenDetails, HttpStatus.OK);
    }


    /**
     * {@code GET  /children-details/:id} : get the "id" ChildrenDetails by parentId.
     *
     * @param id the id of the ChildrenDetails to retrieve by parentId.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ChildrenDetails, or with status {@code 404 (Not Found)}.
     */

    @GetMapping("/children-details/parent/{id}")
    public ResponseEntity<List<ChildrenDetailsDTO>> getChildByParentId(@PathVariable Long id) {
        log.debug("REST request to get child by Parent ID: {}", id);
        if (!parentRepository.existsById(id)) {
            throw new BadRequestAlertException("parent id not found", ENTITY_NAME, "parent-id-not-found");
        }
        List<ChildrenDetailsDTO> childrenDetailsDTOS = childrenDetailsService.getChildByParentId(id);
        return ResponseEntity.ok().body(childrenDetailsDTOS);
    }
}