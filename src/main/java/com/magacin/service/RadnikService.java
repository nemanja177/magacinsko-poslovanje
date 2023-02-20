package com.magacin.service;

import com.magacin.domain.Radnik;
import com.magacin.repository.RadnikRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class RadnikService implements RadnikInterface {

    @Autowired
    private RadnikRepository radnikRepository;

    @Override
    public List<Radnik> findAll() {
        return radnikRepository.findAll();
    }

    @Override
    public Radnik findOne(Long id) {
        return radnikRepository.getById(id);
    }

    @Override
    public Radnik save(Radnik entity) {
        return radnikRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        radnikRepository.deleteById(id);
    }
}
