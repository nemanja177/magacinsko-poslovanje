package com.magacin.service.dto.support;

import com.magacin.domain.PoslovanGodina;
import com.magacin.service.dto.PoslovnaGodinaDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PoslovnaGodinaDTOToPoslovnaGodina implements Converter<PoslovnaGodinaDTO, PoslovanGodina> {

    @Override
    public PoslovanGodina convert(PoslovnaGodinaDTO poslovnaGodinaDTO) {
        PoslovanGodina poslovnaGodina = new PoslovanGodina();
        poslovnaGodina.setId(poslovnaGodinaDTO.getId());
        poslovnaGodina.setGodina(poslovnaGodinaDTO.getGodina());
        poslovnaGodina.setZakljucena(poslovnaGodinaDTO.isZakljucena());
        poslovnaGodina.setPopis(poslovnaGodinaDTO.getPopis());
        poslovnaGodina.setPreduzece(poslovnaGodinaDTO.getPreduzece());
        poslovnaGodina.setPrometniDokument(poslovnaGodinaDTO.getPrometniDokument());

        return poslovnaGodina;
    }
}
