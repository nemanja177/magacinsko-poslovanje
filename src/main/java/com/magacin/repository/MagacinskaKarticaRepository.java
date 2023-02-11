package com.magacin.repository;

import com.magacin.domain.MagacinskaKartica;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MagacinskaKartica entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MagacinskaKarticaRepository extends JpaRepository<MagacinskaKartica, Long> {}
