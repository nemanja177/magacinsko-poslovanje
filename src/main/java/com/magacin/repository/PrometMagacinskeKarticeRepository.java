package com.magacin.repository;

import com.magacin.domain.PrometMagacinskeKartice;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PrometMagacinskeKartice entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PrometMagacinskeKarticeRepository extends JpaRepository<PrometMagacinskeKartice, Long> {}
