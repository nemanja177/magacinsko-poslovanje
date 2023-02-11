package com.magacin.repository;

import com.magacin.domain.PoslovniPartner;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PoslovniPartner entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PoslovniPartnerRepository extends JpaRepository<PoslovniPartner, Long> {}
