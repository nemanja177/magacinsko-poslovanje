package com.magacin.service.dto.support;

import org.springframework.core.convert.converter.Converter;

import com.magacin.domain.AnalitickaMagacinskaKartica;
import com.magacin.service.dto.AnalitickaMagacinskaKarticaDTO;

public class AnalitickaMagacinskaKarticaToAnalitickaMagacinskaKarticaDTO implements Converter<AnalitickaMagacinskaKartica, AnalitickaMagacinskaKarticaDTO>{

	@Override
	public AnalitickaMagacinskaKarticaDTO convert(AnalitickaMagacinskaKartica analitickaMagacinskaKartica) {
		AnalitickaMagacinskaKarticaDTO analitickaMagacinskaKarticaDTO = new AnalitickaMagacinskaKarticaDTO();
		analitickaMagacinskaKarticaDTO.setId(analitickaMagacinskaKartica.getId());
		analitickaMagacinskaKarticaDTO.setCena(analitickaMagacinskaKartica.getCena());
		analitickaMagacinskaKarticaDTO.setDatumPrometa(analitickaMagacinskaKartica.getDatumPrometa());
		analitickaMagacinskaKarticaDTO.setKolicina(analitickaMagacinskaKartica.getKolicina());
		analitickaMagacinskaKarticaDTO.setSmer(analitickaMagacinskaKartica.getSmer());
		analitickaMagacinskaKarticaDTO.setVrednost(analitickaMagacinskaKartica.getVrednost());
		return analitickaMagacinskaKarticaDTO;
	}

}
