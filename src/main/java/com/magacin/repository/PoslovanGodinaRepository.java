package com.magacin.repository;

import com.magacin.domain.PoslovanGodina;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PoslovanGodina entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PoslovanGodinaRepository extends JpaRepository<PoslovanGodina, Long> {}
