package com.magacin.service.dto.support;

import com.magacin.domain.PrometniDokument;
import com.magacin.service.dto.PoslovniPartnerDTO;
import com.magacin.service.dto.PrometniDokumentDTO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PrometniDokumentToPrometniDokumentDTO implements Converter<PrometniDokument, PrometniDokumentDTO> {

    @Override
    public PrometniDokumentDTO convert(PrometniDokument prom) {
        PrometniDokumentDTO dok = new PrometniDokumentDTO();
        dok.setId(prom.getId());
        dok.setDatum(prom.getDatum());
        dok.setVrsta(prom.getVrsta());
        dok.setStatus(prom.getStatus());
        dok.setPoslovniPartner(new PoslovniPartnerDTO(prom.getPartner()));

        return dok;
    }

    public List<PrometniDokumentDTO> convert(List<PrometniDokument> dokument) {
        List<PrometniDokumentDTO> retVal = new ArrayList<>();
        for (PrometniDokument di : dokument) {
            retVal.add(convert(di));
        }
        return retVal;
    }
}
