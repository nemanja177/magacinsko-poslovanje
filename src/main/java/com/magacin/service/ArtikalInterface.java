package com.magacin.service;

import com.magacin.domain.Artikal;
import java.util.List;

public interface ArtikalInterface {
    List<Artikal> findAll();

    //Page<StavkaPrometnogDokumenta> findAll(int pageNum);

    Artikal findOne(Long id);

    Artikal save(Artikal entity);

    void delete(Long id);

    List<Artikal> findByPrometniDokument(Long id);
}
