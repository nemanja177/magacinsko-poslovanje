package com.magacin.service;

import com.magacin.domain.Artikal;
import com.magacin.repository.ArtikalRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class ArtikalService implements ArtikalInterface {

    @Autowired
    private ArtikalRepository artRep;

    @Override
    public List<Artikal> findAll() {
        return artRep.findAll();
    }

    @Override
    public Artikal findOne(Long id) {
        return artRep.getById(id);
    }

    @Override
    public Artikal save(Artikal entity) {
        return artRep.save(entity);
    }

    @Override
    public void delete(Long id) {
        artRep.deleteById(id);
    }
}
