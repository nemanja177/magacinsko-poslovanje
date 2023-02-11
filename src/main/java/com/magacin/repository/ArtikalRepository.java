package com.magacin.repository;

import com.magacin.domain.Artikal;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Artikal entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArtikalRepository extends JpaRepository<Artikal, Long> {}
