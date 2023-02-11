package com.magacin.repository;

import com.magacin.domain.Magacin;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Magacin entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MagacinRepository extends JpaRepository<Magacin, Long> {}
