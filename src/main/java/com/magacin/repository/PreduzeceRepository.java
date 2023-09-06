package com.magacin.repository;

import com.magacin.domain.Preduzece;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Preduzece entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PreduzeceRepository extends JpaRepository<Preduzece, Long> {
    Preduzece findByNaziv(String name);

    Preduzece getOne(Integer id);
}
