package com.magacin.repository;

import com.magacin.domain.StavkaPrometnogDokumenta;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the StavkaPrometnogDokumenta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StavkaPrometnogDokumentaRepository extends JpaRepository<StavkaPrometnogDokumenta, Long> {}
