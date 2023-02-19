package com.magacin.service;

import com.magacin.domain.StavkaPrometnogDokumenta;
import java.util.List;
import org.springframework.data.domain.Page;

public interface StavkaPrometnogDokumentaInterface {
    List<StavkaPrometnogDokumenta> findAll();

    //Page<StavkaPrometnogDokumenta> findAll(int pageNum);

    StavkaPrometnogDokumenta findOne(Long id);

    StavkaPrometnogDokumenta save(StavkaPrometnogDokumenta entity);

    void delete(Long id);
}
