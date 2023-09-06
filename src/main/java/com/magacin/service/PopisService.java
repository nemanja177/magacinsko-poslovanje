package com.magacin.service;

import com.magacin.domain.Popis;
import com.magacin.repository.PopisRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PopisService implements PopisInterface {

    @Autowired
    private PopisRepository popRep;

    @Override
    public List<Popis> findAll() {
        return popRep.findAll();
    }

    @Override
    public Popis findOne(Long id) {
        return popRep.getById(id);
    }

    @Override
    public Popis save(Popis entity) {
        return popRep.save(entity);
    }

    @Override
    public void delete(Long id) {
        popRep.deleteById(id);
    }
}
