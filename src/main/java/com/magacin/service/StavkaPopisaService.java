package com.magacin.service;

import com.magacin.domain.StavkaPopisa;
import com.magacin.repository.StavkaPopisaRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StavkaPopisaService implements StavkaPopisaInterface {

    @Autowired
    private StavkaPopisaRepository stavkaPopisa;

    @Override
    public List<StavkaPopisa> findAll() {
        return stavkaPopisa.findAll();
    }

    @Override
    public StavkaPopisa findOne(Long id) {
        return stavkaPopisa.getById(id);
    }

    @Override
    public StavkaPopisa save(StavkaPopisa entity) {
        return stavkaPopisa.save(entity);
    }

    @Override
    public void delete(Long id) {
        stavkaPopisa.deleteById(id);
    }
}
