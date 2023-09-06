package com.magacin.service;

import com.magacin.domain.PrometniDokument;
import com.magacin.repository.PrometniDokumentRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrometniDokumentService implements PrometniDokumentInterface {

    @Autowired
    private PrometniDokumentRepository promRep;

    @Override
    public List<PrometniDokument> findAll() {
        return promRep.findAll();
    }

    @Override
    public PrometniDokument findOne(Long id) {
        return promRep.getById(id);
    }

    @Override
    public PrometniDokument save(PrometniDokument entity) {
        return promRep.save(entity);
    }

    @Override
    public void delete(Long id) {
        promRep.deleteById(id);
    }
}
