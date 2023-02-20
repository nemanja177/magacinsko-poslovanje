package com.magacin.service;

import com.magacin.domain.PoslovniPartner;
import java.util.List;

public interface PoslovniPartnerInterface {
    List<PoslovniPartner> findAll();

    //Page<StavkaPrometnogDokumenta> findAll(int pageNum);

    PoslovniPartner findOne(Long id);

    PoslovniPartner save(PoslovniPartner entity);

    void delete(Long id);
}
