package com.magacin.service.dto.support;

import com.magacin.domain.StavkaPrometnogDokumenta;
import com.magacin.service.dto.PopisDTO;
import com.magacin.service.dto.StavkaPrometnogDokumentaDTO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StavkaPrometnogDokumentaToStavkaPromentogDokumentaDTO
    implements Converter<StavkaPrometnogDokumenta, StavkaPrometnogDokumentaDTO> {

    @Override
    public StavkaPrometnogDokumentaDTO convert(StavkaPrometnogDokumenta stavkaDokumenta) {
        StavkaPrometnogDokumentaDTO stDTO = new StavkaPrometnogDokumentaDTO();
        stDTO.setId(stavkaDokumenta.getId());
        stDTO.setCena(stavkaDokumenta.getCena());
        stDTO.setKolicina(stavkaDokumenta.getKolicina());
        stDTO.setVrednost(stavkaDokumenta.getVrednost());
        stDTO.setPopis(new PopisDTO(stavkaDokumenta.getPopis()));
        return stDTO;
    }

    public List<StavkaPrometnogDokumentaDTO> convert(List<StavkaPrometnogDokumenta> stavkaDokumenta) {
        List<StavkaPrometnogDokumentaDTO> retVal = new ArrayList<>();
        for (StavkaPrometnogDokumenta bp : stavkaDokumenta) {
            retVal.add(convert(bp));
        }

        return retVal;
    }
}
