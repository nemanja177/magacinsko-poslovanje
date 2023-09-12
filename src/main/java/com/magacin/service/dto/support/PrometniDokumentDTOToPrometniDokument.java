package com.magacin.service.dto.support;

import com.magacin.domain.PrometniDokument;
import com.magacin.service.dto.PrometniDokumentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PrometniDokumentDTOToPrometniDokument implements Converter<PrometniDokumentDTO, PrometniDokument> {

    @Override
    public PrometniDokument convert(PrometniDokumentDTO dokumentDTO) {
        PrometniDokument dokumetn = new PrometniDokument();
        dokumetn.setId(dokumentDTO.getId());
        dokumetn.setDatum(dokumentDTO.getDatum());
        dokumetn.setStatus(dokumentDTO.getStatus());
        dokumetn.setVrsta(dokumentDTO.getVrsta());

        return dokumetn;
    }
}
