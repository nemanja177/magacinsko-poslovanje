package com.magacin.service;

import com.magacin.domain.StavkaPrometnogDokumenta;
import com.magacin.repository.StavkaPrometnogDokumentaRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class StavkaPrometnogDokumentaService implements StavkaPrometnogDokumentaInterface {

    @Autowired
    private StavkaPrometnogDokumentaRepository stavkaRep;

    @Override
    public List<StavkaPrometnogDokumenta> findAll() {
        return stavkaRep.findAll();
    }

    @Override
    public StavkaPrometnogDokumenta findOne(Long id) {
        return stavkaRep.getOne(id);
    }

    @Override
    public StavkaPrometnogDokumenta save(StavkaPrometnogDokumenta entity) {
        return stavkaRep.save(entity);
    }

    @Override
    public void delete(Long id) {
        stavkaRep.deleteById(id);
    }
}
