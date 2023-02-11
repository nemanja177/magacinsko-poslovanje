package com.magacin.repository;

import com.magacin.domain.JedinicaMere;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the JedinicaMere entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JedinicaMereRepository extends JpaRepository<JedinicaMere, Long> {}
