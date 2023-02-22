package com.magacin.service.dto.support;

import com.magacin.domain.Preduzece;
import com.magacin.service.dto.PreduzeceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

public class PreduzeceDTOToPreduzece implements Converter<PreduzeceDTO, Preduzece> {

    @Autowired
    @Override
    public Preduzece convert(PreduzeceDTO preduzeceDto) {
        Preduzece preduzece = new Preduzece();
        preduzece.setId(preduzeceDto.getId());
        preduzece.setNaziv(preduzeceDto.getNaziv());
        preduzece.setAdresa(preduzeceDto.getAdresa());
        preduzece.setTelefon(preduzeceDto.getTelefon());
        preduzece.setMib(preduzeceDto.getMib());
        preduzece.setPib(preduzeceDto.getPib());

        return preduzece;
    }
}
