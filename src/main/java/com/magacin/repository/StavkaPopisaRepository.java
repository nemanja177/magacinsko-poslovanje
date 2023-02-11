package com.magacin.repository;

import com.magacin.domain.StavkaPopisa;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the StavkaPopisa entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StavkaPopisaRepository extends JpaRepository<StavkaPopisa, Long> {}
