package com.magacin.service;

import com.magacin.domain.Preduzece;
import java.util.List;

public interface PreduzeceInterface {
    List<Preduzece> findAll();

    //Page<StavkaPrometnogDokumenta> findAll(int pageNum);

    Preduzece findOne(Long id);

    Preduzece save(Preduzece entity);

    void delete(Long id);
}
