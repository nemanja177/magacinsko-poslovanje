package com.magacin.service;

import com.magacin.domain.PoslovniPartner;
import com.magacin.repository.PoslovniPartnerRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class PoslovniPartnerService implements PoslovniPartnerInterface {

    @Autowired
    private PoslovniPartnerRepository poslovniPartnerRep;

    @Override
    public List<PoslovniPartner> findAll() {
        return poslovniPartnerRep.findAll();
    }

    @Override
    public PoslovniPartner findOne(Long id) {
        return poslovniPartnerRep.getById(id);
    }

    @Override
    public PoslovniPartner save(PoslovniPartner entity) {
        return poslovniPartnerRep.save(entity);
    }

    @Override
    public void delete(Long id) {
        poslovniPartnerRep.deleteById(id);
    }
}
