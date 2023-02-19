package com.magacin.service.dto.support;

import com.magacin.domain.StavkaPrometnogDokumenta;
import com.magacin.service.dto.StavkaPrometnogDokumentaDTO;
import org.springframework.core.convert.converter.Converter;

public class StavkaPrometnogDokumentaDTOtoStavkaPrometnogDokumenta
    implements Converter<StavkaPrometnogDokumentaDTO, StavkaPrometnogDokumenta> {

    @Override
    public StavkaPrometnogDokumenta convert(StavkaPrometnogDokumentaDTO stavkaPrometnogDTO) {
        StavkaPrometnogDokumenta stp = new StavkaPrometnogDokumenta();
        stp.setId(stavkaPrometnogDTO.getId());
        stp.setCena(stavkaPrometnogDTO.getCena());
        stp.setKolicina(stavkaPrometnogDTO.getKolicina());
        stp.setVrednost(stavkaPrometnogDTO.getVrednost());

        return stp;
    }
}
