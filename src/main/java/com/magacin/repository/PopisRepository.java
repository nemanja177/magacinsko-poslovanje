package com.magacin.repository;

import com.magacin.domain.Popis;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Popis entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PopisRepository extends JpaRepository<Popis, Long> {}
