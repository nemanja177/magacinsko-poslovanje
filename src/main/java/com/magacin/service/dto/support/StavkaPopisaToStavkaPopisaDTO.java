package com.magacin.service.dto.support;

import com.magacin.domain.StavkaPopisa;
import com.magacin.service.dto.StavkaPopisaDTO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StavkaPopisaToStavkaPopisaDTO implements Converter<StavkaPopisa, StavkaPopisaDTO> {

    @Override
    public StavkaPopisaDTO convert(StavkaPopisa stavkaPopisa) {
        StavkaPopisaDTO st = new StavkaPopisaDTO();
        st.setId(stavkaPopisa.getId());
        st.setKolicinaPoKnjigama(stavkaPopisa.getKolicinaPoKnjigama());
        st.setKolicinaPopisa(stavkaPopisa.getKolicinaPopisa());
        //st.setArtikal(new ArtikalDTO(stavkaPopisa.getArtikal()));
        //st.setPrometniDokument(new PrometniDokumentDTO(stavkaPopisa.getPrometniDokument()));

        return st;
    }

    public List<StavkaPopisaDTO> convert(List<StavkaPopisa> stavkaPopisa) {
        List<StavkaPopisaDTO> retVal = new ArrayList<>();
        for (StavkaPopisa di : stavkaPopisa) {
            retVal.add(convert(di));
        }
        return retVal;
    }
}
