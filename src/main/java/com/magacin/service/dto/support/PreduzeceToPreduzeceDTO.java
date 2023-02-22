package com.magacin.service.dto.support;

import com.magacin.domain.Preduzece;
import com.magacin.service.dto.PreduzeceDTO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.core.convert.converter.Converter;

public class PreduzeceToPreduzeceDTO implements Converter<Preduzece, PreduzeceDTO> {

    @Override
    public PreduzeceDTO convert(Preduzece preduzece) {
        PreduzeceDTO preduzeceDTO = new PreduzeceDTO();
        preduzeceDTO.setId(preduzece.getId());
        preduzeceDTO.setNaziv(preduzece.getNaziv());
        preduzeceDTO.setAdresa(preduzece.getAdresa());
        preduzeceDTO.setTelefon(preduzece.getTelefon());
        preduzeceDTO.setPib(preduzece.getPib());
        preduzece.setMib(preduzece.getMib());

        return preduzeceDTO;
    }

    public List<PreduzeceDTO> convert(List<Preduzece> preduzece) {
        List<PreduzeceDTO> retVal = new ArrayList<>();
        for (Preduzece di : preduzece) {
            retVal.add(convert(di));
        }
        return retVal;
    }
}
