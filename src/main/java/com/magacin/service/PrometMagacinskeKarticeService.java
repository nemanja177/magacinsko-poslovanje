package com.magacin.service;

import com.magacin.domain.PrometMagacinskeKartice;
import com.magacin.repository.PrometMagacinskeKarticeRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class PrometMagacinskeKarticeService implements PrometMagacinskeKarticeInterface {

    @Autowired
    private PrometMagacinskeKarticeRepository pmkRep;

    @Override
    public List<PrometMagacinskeKartice> findAll() {
        return pmkRep.findAll();
    }

    @Override
    public PrometMagacinskeKartice findOne(Long id) {
        return pmkRep.getById(id);
    }

    @Override
    public PrometMagacinskeKartice save(PrometMagacinskeKartice entity) {
        return pmkRep.save(entity);
    }

    @Override
    public void delete(Long id) {
        pmkRep.deleteById(id);
    }
}
