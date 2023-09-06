package com.magacin.service;

import com.magacin.domain.PoslovniPartner;
import com.magacin.domain.Preduzece;
import com.magacin.service.dto.PoslovniPartnerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PoslovniPartnerDTOToPoslovniPartner implements Converter<PoslovniPartnerDTO, PoslovniPartner> {

    @Autowired
    private PreduzeceInterface placeService;

    @Override
    public PoslovniPartner convert(PoslovniPartnerDTO source) {
        PoslovniPartner bp = new PoslovniPartner();
        bp.setId(source.getId());
        bp.setAdresa(source.getAdresa());
        bp.setIme(source.getIme());

        Preduzece place = placeService.findByName(source.getPreduzece().getNaziv());
        if (place != null) {
            bp.setPreduzece(place);
        }
        return bp;
    }
}
