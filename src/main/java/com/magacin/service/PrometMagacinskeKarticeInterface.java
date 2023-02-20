package com.magacin.service;

import com.magacin.domain.PrometMagacinskeKartice;
import java.util.List;

public interface PrometMagacinskeKarticeInterface {
    List<PrometMagacinskeKartice> findAll();

    //Page<StavkaPrometnogDokumenta> findAll(int pageNum);

    PrometMagacinskeKartice findOne(Long id);

    PrometMagacinskeKartice save(PrometMagacinskeKartice entity);

    void delete(Long id);
}
