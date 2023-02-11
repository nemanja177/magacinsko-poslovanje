package com.magacin.repository;

import com.magacin.domain.AnalitickaMagacinskaKartica;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AnalitickaMagacinskaKartica entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnalitickaMagacinskaKarticaRepository extends JpaRepository<AnalitickaMagacinskaKartica, Long> {}
