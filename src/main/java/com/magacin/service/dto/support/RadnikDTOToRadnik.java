package com.magacin.service.dto.support;

import com.magacin.domain.Radnik;
import com.magacin.service.dto.RadnikDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

public class RadnikDTOToRadnik implements Converter<RadnikDTO, Radnik> {

    @Autowired
    @Override
    public Radnik convert(RadnikDTO radnikDTO) {
        Radnik radnik = new Radnik();
        radnik.setId(radnikDTO.getId());
        radnik.setIme(radnikDTO.getIme());
        radnik.setPrezime(radnikDTO.getPrezime());
        radnik.setAdresa(radnikDTO.getAdresa());
        radnik.setTelefon(radnikDTO.getTelefon());

        return radnik;
    }
}
