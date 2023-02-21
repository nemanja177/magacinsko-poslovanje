package com.magacin.service.dto.support;

import com.magacin.domain.Artikal;
import com.magacin.domain.Popis;
import com.magacin.domain.StavkaPrometnogDokumenta;
import com.magacin.service.ArtikalInterface;
import com.magacin.service.PopisInterface;
import com.magacin.service.dto.StavkaPrometnogDokumentaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

public class StavkaPrometnogDokumentaDTOtoStavkaPrometnogDokumenta
    implements Converter<StavkaPrometnogDokumentaDTO, StavkaPrometnogDokumenta> {

    @Autowired
    private PopisInterface popInterafce;

    @Autowired
    private ArtikalInterface artikalInterface;

    @Override
    public StavkaPrometnogDokumenta convert(StavkaPrometnogDokumentaDTO stavkaPrometnogDTO) {
        StavkaPrometnogDokumenta stp = new StavkaPrometnogDokumenta();
        stp.setId(stavkaPrometnogDTO.getId());
        stp.setCena(stavkaPrometnogDTO.getCena());
        stp.setKolicina(stavkaPrometnogDTO.getKolicina());
        stp.setVrednost(stavkaPrometnogDTO.getVrednost());

        Popis popis = popInterafce.findOne(stavkaPrometnogDTO.getPopis().getId());
        if (popis != null) {
            stp.setPopis(popis);
        }

        return stp;
    }
}
