package com.magacin.service.dto.support;

import org.springframework.core.convert.converter.Converter;

import com.magacin.domain.Magacin;
import com.magacin.service.dto.MagacinDTO;

public class MagacinDTOToMagacin implements Converter<MagacinDTO, Magacin> {

	@Override
	public Magacin convert(MagacinDTO magacinDTO) {
		Magacin magacin = new Magacin();
		magacin.setId(magacinDTO.getId());
		magacin.setMagacinskaKartica(magacinDTO.getMagacinskaKartica());
		magacin.setNaziv(magacinDTO.getNaziv());
		magacin.setPopis(magacinDTO.getPopis());
		magacin.setPreduzece(magacinDTO.getPreduzece());
		magacin.setPrometniDokument(magacinDTO.getPrometniDokument());
		magacin.setRadnici(magacinDTO.getRadnici());
		return magacin;
	}
}
