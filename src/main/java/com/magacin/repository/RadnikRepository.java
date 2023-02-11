package com.magacin.repository;

import com.magacin.domain.Radnik;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Radnik entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RadnikRepository extends JpaRepository<Radnik, Long> {}
