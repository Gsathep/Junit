package com.kidzcubicle.uaps.web.controller;

import com.kidzcubicle.uaps.entity.Parent;
import com.kidzcubicle.uaps.repository.ParentRepository;
import com.kidzcubicle.uaps.service.IParentService;
import com.kidzcubicle.uaps.web.exception.handler.BadRequestAlertException;
import com.kidzcubicle.uaps.web.exception.handler.EmailAlreadyUsedException;
import com.kidzcubicle.uaps.web.exception.handler.InvalidIdException;
import com.kidzcubicle.uaps.web.request.ParentDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

import static org.hibernate.id.IdentifierGenerator.ENTITY_NAME;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class ParentController {

    public static final String ENTITY_NAME = "parent";

    private final IParentService parentService;

    private final ParentRepository parentRepository;

    public ParentController(IParentService parentService, ParentRepository parentRepository) {
        this.parentService = parentService;
        this.parentRepository = parentRepository;
    }

    /**
     * {@code POST  /register} : Create a new Parent.
     *
     * @param parentDTO the ParentDTO to create.
     *                  the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new parentDTO, or with status {@code 400 (Bad Request)} if the parent has already an ID.
     * @throws URISyntaxException
     */
    @PostMapping("/register")
    public ResponseEntity<ParentDTO> createParent(@RequestBody ParentDTO parentDTO) throws URISyntaxException {
        log.debug("REST request to save or patch Parent : {}", parentDTO);

        if (parentDTO.getId() != null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "id is already present");
        } if (parentRepository.existsBySubId(parentDTO.getSubId())) {
            Parent existingParent = parentRepository.findBySubId(parentDTO.getSubId());
            ParentDTO updatedParentDTO = parentService.partialUpdateParent(existingParent.getId(), parentDTO);
            return ResponseEntity.ok().body(updatedParentDTO);
        } else {
            ParentDTO createdParentDTO = parentService.createParent(parentDTO);
            log.info("parent created");
            return ResponseEntity.created(new URI("/register" + createdParentDTO.getId())).body(createdParentDTO);
        }
    }


    /**
     * {@code GET  /parents/:id} : get the "id" parents.
     *
     * @param id the id of the parents to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the parents, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/parents/{id}")
    public ResponseEntity<ParentDTO> getParentById(@PathVariable Long id) {
        log.debug("REST request to retrieve Parent with ID: {}", id);
        if (id == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!parentRepository.existsById(id)) {
            throw new BadRequestAlertException("Parent id not found", ENTITY_NAME, "parent-id-not-found");
        }
        Optional<ParentDTO> parentDTO = parentService.getParentById(id);
        return ResponseEntity.ok(parentDTO.get());
    }

    /**
     * {@code PUT  /parents/:id} : Updates an existing parents.
     *
     * @param id        the id of the ParentDTO to save.
     * @param parentDTO the parentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated parentDTO,
     * or with status {@code 400 (Bad Request)} if the parentDTO is not valid,
     */
    @PatchMapping("/parents/{id}")
    public ResponseEntity<ParentDTO> partialUpdateParent(@PathVariable Long id, @RequestBody ParentDTO parentDTO) {
        log.debug("REST request to partial update Parent with ID: {}", id);
        if (parentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!parentRepository.existsById(id)) {
            throw new BadRequestAlertException("Parent id not found", ENTITY_NAME, "parent-id-not-found");
        }

        ParentDTO updatedParent = parentService.partialUpdateParent(id, parentDTO);
        return new ResponseEntity<>(updatedParent, HttpStatus.OK);
    }

}





