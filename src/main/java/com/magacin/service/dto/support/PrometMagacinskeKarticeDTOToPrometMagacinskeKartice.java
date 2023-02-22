package com.magacin.service.dto.support;

import com.magacin.domain.PrometMagacinskeKartice;
import com.magacin.service.dto.PrometMagacinskeKarticeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

public class PrometMagacinskeKarticeDTOToPrometMagacinskeKartice implements Converter<PrometMagacinskeKarticeDTO, PrometMagacinskeKartice> {

    @Autowired
    @Override
    public PrometMagacinskeKartice convert(PrometMagacinskeKarticeDTO karticaDTO) {
        PrometMagacinskeKartice kartica = new PrometMagacinskeKartice();
        kartica.setId(karticaDTO.getId());
        kartica.setCena(karticaDTO.getCena());
        kartica.setDatumPrometa(karticaDTO.getDatumPrometa());
        kartica.setKolicina(karticaDTO.getKolicina());
        kartica.setVrednost(karticaDTO.getVrednost());
        kartica.setKolicina(karticaDTO.getKolicina());

        return kartica;
    }
}
