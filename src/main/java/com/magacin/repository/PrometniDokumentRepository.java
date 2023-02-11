package com.magacin.repository;

import com.magacin.domain.PrometniDokument;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PrometniDokument entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PrometniDokumentRepository extends JpaRepository<PrometniDokument, Long> {}
