package com.kidzcubicle.uaps.repository;

import com.kidzcubicle.uaps.entity.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the Parent entity.
 */
@Repository
public interface ParentRepository extends JpaRepository<Parent, Long> {

    boolean existsBySubId(String subId);

    Parent findBySubId(String subId);

}

