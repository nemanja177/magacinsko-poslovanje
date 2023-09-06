package com.magacin.service;

import com.magacin.domain.PoslovniPartner;
import com.magacin.service.dto.PoslovniPartnerDTO;
import com.magacin.service.dto.PreduzeceDTO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PoslovniPartnerToPoslovniPartnerDTOConverter implements Converter<PoslovniPartner, PoslovniPartnerDTO> {

    @Override
    public PoslovniPartnerDTO convert(PoslovniPartner source) {
        PoslovniPartnerDTO dto = new PoslovniPartnerDTO();
        dto.setId(source.getId());
        dto.setIme(source.getIme());
        dto.setAdresa(source.getAdresa());
        dto.setPreduzece(new PreduzeceDTO(source.getPreduzece()));

        return dto;
    }

    public List<PoslovniPartnerDTO> convert(List<PoslovniPartner> businessPartners) {
        List<PoslovniPartnerDTO> retVal = new ArrayList<>();
        for (PoslovniPartner bp : businessPartners) {
            retVal.add(convert(bp));
        }

        return retVal;
    }
}
