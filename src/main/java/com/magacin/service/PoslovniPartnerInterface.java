package com.magacin.service;

import com.magacin.domain.PoslovniPartner;
import com.magacin.domain.Preduzece;
import java.util.List;
import org.springframework.data.domain.Page;

public interface PoslovniPartnerInterface {
    List<PoslovniPartner> findAll();

    Page<PoslovniPartner> findAll(int pageNum);

    //Page<StavkaPrometnogDokumenta> findAll(int pageNum);

    PoslovniPartner findOne(Long id);

    PoslovniPartner save(PoslovniPartner entity);

    void delete(Long id);
}
