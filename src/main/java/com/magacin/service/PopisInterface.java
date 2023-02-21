package com.magacin.service;

import com.magacin.domain.Popis;
import java.util.List;

public interface PopisInterface {
    List<Popis> findAll();

    //Page<StavkaPrometnogDokumenta> findAll(int pageNum);

    Popis findOne(Long id);

    Popis save(Popis entity);

    void delete(Long id);
}
