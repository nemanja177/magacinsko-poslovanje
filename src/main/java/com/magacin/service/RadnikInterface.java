package com.magacin.service;

import com.magacin.domain.Radnik;
import java.util.List;

public interface RadnikInterface {
    List<Radnik> findAll();

    //Page<StavkaPrometnogDokumenta> findAll(int pageNum);

    Radnik findOne(Long id);

    Radnik save(Radnik entity);

    void delete(Long id);
}
