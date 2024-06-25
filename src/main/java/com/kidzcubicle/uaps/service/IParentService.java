package com.kidzcubicle.uaps.service;

import com.kidzcubicle.uaps.web.exception.handler.InvalidIdException;
import com.kidzcubicle.uaps.web.request.ParentDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.kidzcubicle.uaps.entity.Parent}.
 */
public interface IParentService {
    /**
     * Save a parent.
     *
     * @param parentDTO the entity to save.
     * @return the persisted entity.
     */
    ParentDTO createParent(ParentDTO parentDTO);

    /**
     * Get the "id" parent.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ParentDTO> getParentById(Long id);

    /**
     * Partially updates a parent.
     *
     * @param partialParentDTO the entity to update partially.
     * @return the persisted entity.
     */
    ParentDTO partialUpdateParent(Long id, ParentDTO partialParentDTO);

}
