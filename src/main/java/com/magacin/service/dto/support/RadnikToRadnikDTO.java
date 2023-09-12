package com.magacin.service.dto.support;

import com.magacin.domain.Radnik;
import com.magacin.service.dto.PreduzeceDTO;
import com.magacin.service.dto.RadnikDTO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RadnikToRadnikDTO implements Converter<Radnik, RadnikDTO> {

    @Override
    public RadnikDTO convert(Radnik radnik) {
        RadnikDTO rad = new RadnikDTO();
        rad.setId(radnik.getId());
        rad.setAdresa(radnik.getAdresa());
        rad.setIme(radnik.getIme());
        rad.setPrezime(radnik.getPrezime());
        rad.setTelefon(radnik.getTelefon());
        //rad.setMagacin(new MagacinDTO(radnik.getMagacin().getId()));
        rad.setPreduzece(new PreduzeceDTO(radnik.getPreduzece()));
        return rad;
    }

    public List<RadnikDTO> convert(List<Radnik> radnik) {
        List<RadnikDTO> retVal = new ArrayList<>();
        for (Radnik di : radnik) {
            retVal.add(convert(di));
        }
        return retVal;
    }
}
