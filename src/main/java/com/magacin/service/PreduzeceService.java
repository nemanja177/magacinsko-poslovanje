package com.magacin.service;

import com.magacin.domain.Preduzece;
import com.magacin.repository.PreduzeceRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PreduzeceService implements PreduzeceInterface {

    @Autowired
    private PreduzeceRepository predRep;

    @Override
    public List<Preduzece> findAll() {
        return predRep.findAll();
    }

    @Override
    public Preduzece findOne(Long id) {
        return predRep.getById(id);
    }

    @Override
    public Preduzece save(Preduzece entity) {
        return predRep.save(entity);
    }

    @Override
    public Preduzece findByName(String name) {
        return predRep.findByNaziv(name);
    }

    @Override
    public void delete(Long id) {
        predRep.deleteById(id);
    }
}
