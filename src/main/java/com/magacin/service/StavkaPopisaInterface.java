package com.magacin.service;

import com.magacin.domain.StavkaPopisa;
import java.util.List;

public interface StavkaPopisaInterface {
    List<StavkaPopisa> findAll();

    //Page<StavkaPrometnogDokumenta> findAll(int pageNum);

    StavkaPopisa findOne(Long id);

    StavkaPopisa save(StavkaPopisa entity);

    void delete(Long id);
}
