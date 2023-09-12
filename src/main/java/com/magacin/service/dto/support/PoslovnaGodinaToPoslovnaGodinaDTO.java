package com.magacin.service.dto.support;

import com.magacin.domain.PoslovanGodina;
import com.magacin.service.dto.PoslovnaGodinaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PoslovnaGodinaToPoslovnaGodinaDTO implements Converter<PoslovanGodina, PoslovnaGodinaDTO> {

    @Override
    public PoslovnaGodinaDTO convert(PoslovanGodina poslovnaGodina) {
        PoslovnaGodinaDTO poslovnaGodinaDTO = new PoslovnaGodinaDTO();
        poslovnaGodinaDTO.setId(poslovnaGodina.getId());
        poslovnaGodinaDTO.setGodina(poslovnaGodina.getGodina());
        poslovnaGodinaDTO.setZakljucena(poslovnaGodina.getZakljucena());
        poslovnaGodinaDTO.setPopis(poslovnaGodina.getPopis());
        poslovnaGodinaDTO.setPreduzece(poslovnaGodina.getPreduzece());
        poslovnaGodinaDTO.setPrometniDokument(poslovnaGodina.getPrometniDokument());

        return poslovnaGodinaDTO;
    }
}
