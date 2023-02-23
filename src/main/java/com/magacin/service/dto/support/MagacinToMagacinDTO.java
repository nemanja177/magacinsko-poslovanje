package com.magacin.service.dto.support;

import org.springframework.core.convert.converter.Converter;

import com.magacin.domain.Magacin;
import com.magacin.service.dto.MagacinDTO;

public class MagacinToMagacinDTO implements Converter<Magacin, MagacinDTO>{

	@Override
	public MagacinDTO convert(Magacin magacin) {
		MagacinDTO magacinDTO = new MagacinDTO();
		magacinDTO.setId(magacin.getId());
		magacinDTO.setMagacinskaKartica(magacin.getMagacinskaKartica());
		magacinDTO.setNaziv(magacin.getNaziv());
		magacinDTO.setPopis(magacin.getPopis());
		magacinDTO.setPreduzece(magacin.getPreduzece());
		magacinDTO.setPrometniDokument(magacin.getPrometniDokument());
		magacinDTO.setRadnici(magacin.getRadnici());
		return magacinDTO;
	}

}
