package com.kidzcubicle.uaps.repository;

import com.kidzcubicle.uaps.entity.ChildrenDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the Parent entity.
 */
@Repository
public interface ChildrenDetailsRepository extends JpaRepository<ChildrenDetails, Long> {

    List<ChildrenDetails> findByParentId(Long id);
}
