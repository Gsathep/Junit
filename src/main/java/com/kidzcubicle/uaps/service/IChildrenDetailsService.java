package com.kidzcubicle.uaps.service;

import com.kidzcubicle.uaps.web.exception.handler.InvalidIdException;
import com.kidzcubicle.uaps.web.request.ChildrenDetailsDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.kidzcubicle.uaps.entity.ChildrenDetails}.
 */
public interface IChildrenDetailsService {

    /**
     * Save a childrenDetails.
     *
     * @param childrenDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    ChildrenDetailsDTO createChildrenDetails(ChildrenDetailsDTO childrenDetailsDTO);

    /**
     * Get the "id" childrenDetails.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ChildrenDetailsDTO> getChildrenDetailsById(Long id);

    /**
     * Partially updates a childrenDetails.
     *
     * @param childrenDetailsDTO the entity to update partially.
     * @return the persisted entity.
     */

    ChildrenDetailsDTO partialUpdateChildrenDetails(Long id, ChildrenDetailsDTO childrenDetailsDTO) throws InvalidIdException;

    /**
     * Get the "id" childrenDetails by parentId.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    List<ChildrenDetailsDTO> getChildByParentId(Long id);


}
