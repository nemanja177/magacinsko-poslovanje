package com.magacin.service.dto.support;

import com.magacin.domain.Artikal;
import com.magacin.domain.PrometniDokument;
import com.magacin.domain.StavkaPopisa;
import com.magacin.service.ArtikalInterface;
import com.magacin.service.PrometniDokumentInterface;
import com.magacin.service.PrometniDokumentService;
import com.magacin.service.StavkaPopisaInterface;
import com.magacin.service.StavkaPopisaService;
import com.magacin.service.dto.StavkaPopisaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

public class StavkaPopisaDTOToStavkaPopisa implements Converter<StavkaPopisaDTO, StavkaPopisa> {

    @Autowired
    private PrometniDokumentInterface dokumentInterface;

    @Autowired
    private ArtikalInterface artikalInterface;

    @Override
    public StavkaPopisa convert(StavkaPopisaDTO stavkaPopisaDTO) {
        StavkaPopisa stp = new StavkaPopisa();
        stp.setId(stavkaPopisaDTO.getId());
        stp.setKolicinaPoKnjigama(stavkaPopisaDTO.getKolicinaPoKnjigama());
        stp.setKolicinaPopisa(stavkaPopisaDTO.getKolicinaPopisa());

        PrometniDokument dokument = dokumentInterface.findOne(stavkaPopisaDTO.getPrometniDokument().getId());
        if (dokument != null) {
            stp.setPrometniDokument(dokument);
        }

        Artikal artikal = artikalInterface.findOne(stavkaPopisaDTO.getArtikal().getId());
        /*if(artikal != null) {
			stp.setArtikal(artikal);
		}*/

        return stp;
    }
}
