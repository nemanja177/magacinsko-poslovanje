package com.magacin.service.dto.support;

import org.springframework.core.convert.converter.Converter;

import com.magacin.domain.AnalitickaMagacinskaKartica;
import com.magacin.service.dto.AnalitickaMagacinskaKarticaDTO;

public class AnalitickaMagacinskaKarticaDTOToAnalitickaMagacinskaKartica implements Converter<AnalitickaMagacinskaKarticaDTO, AnalitickaMagacinskaKartica>{

	@Override
	public AnalitickaMagacinskaKartica convert(AnalitickaMagacinskaKarticaDTO analitickaMagacinskaKarticaDTO) {
		AnalitickaMagacinskaKartica analitickaMagacinskaKartica = new AnalitickaMagacinskaKartica();
		analitickaMagacinskaKartica.setId(analitickaMagacinskaKarticaDTO.getId());
		analitickaMagacinskaKartica.setCena(analitickaMagacinskaKarticaDTO.getCena());
		analitickaMagacinskaKartica.setDatumPrometa(analitickaMagacinskaKarticaDTO.getDatumPrometa());
		analitickaMagacinskaKartica.setKolicina(analitickaMagacinskaKarticaDTO.getKolicina());
		analitickaMagacinskaKartica.setSmer(analitickaMagacinskaKarticaDTO.getSmer());
		analitickaMagacinskaKartica.setVrednost(analitickaMagacinskaKarticaDTO.getVrednost());
		return analitickaMagacinskaKartica;
	}
}
