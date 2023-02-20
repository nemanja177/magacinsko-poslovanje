package com.magacin.service;

import com.magacin.domain.PrometniDokument;
import java.util.List;

public interface PrometniDokumentInterface {
    List<PrometniDokument> findAll();

    //Page<StavkaPrometnogDokumenta> findAll(int pageNum);

    PrometniDokument findOne(Long id);

    PrometniDokument save(PrometniDokument entity);

    void delete(Long id);
}
