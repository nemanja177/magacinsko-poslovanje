package com.magacin.service.dto.support;

import com.magacin.domain.PrometMagacinskeKartice;
import com.magacin.domain.StavkaPrometnogDokumenta;
import com.magacin.service.dto.PrometMagacinskeKarticeDTO;
import com.magacin.service.dto.StavkaPrometnogDokumentaDTO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.core.convert.converter.Converter;

public class PrometMagacinskeKarticeToPrometMagacinskeKarticeDTO implements Converter<PrometMagacinskeKartice, PrometMagacinskeKarticeDTO> {

    @Override
    public PrometMagacinskeKarticeDTO convert(PrometMagacinskeKartice pmk) {
        PrometMagacinskeKarticeDTO pmkDTO = new PrometMagacinskeKarticeDTO();
        pmkDTO.setId(pmk.getId());
        pmkDTO.setCena(pmk.getCena());
        pmkDTO.setDatumPrometa(pmk.getDatumPrometa());
        pmkDTO.setDokument(pmk.getDokument());
        pmkDTO.setKolicina(pmk.getKolicina());
        pmkDTO.setVrednost(pmk.getVrednost());
        pmkDTO.setSmer(pmk.getSmer());

        return pmkDTO;
    }

    public List<PrometMagacinskeKarticeDTO> convert(List<PrometMagacinskeKartice> pmk) {
        List<PrometMagacinskeKarticeDTO> retVal = new ArrayList<>();
        for (PrometMagacinskeKartice bp : pmk) {
            retVal.add(convert(bp));
        }
        return retVal;
    }
}
