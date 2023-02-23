package com.magacin.service.dto.support;

import org.springframework.core.convert.converter.Converter;

import com.magacin.domain.MagacinskaKartica;
import com.magacin.service.dto.MagacinskaKarticaDTO;

public class MagacinskaKarticaDTOToMagacinskaKartica implements Converter<MagacinskaKarticaDTO, MagacinskaKartica> {

	@Override
	public MagacinskaKartica convert(MagacinskaKarticaDTO magacinskaKarticaDTO) {
		MagacinskaKartica magacinskaKartica = new MagacinskaKartica();
		magacinskaKartica.setId(magacinskaKarticaDTO.getId());
		magacinskaKartica.setPocetnoStanjeKolicina(magacinskaKarticaDTO.getPocetnoStanjeKolicina());
		magacinskaKartica.setPocetnoStanjeVrednosti(magacinskaKarticaDTO.getPocetnoStanjeVrednosti());
		magacinskaKartica.setPrometIzlazaKolicina(magacinskaKarticaDTO.getPrometIzlazaKolicina());
		magacinskaKartica.setPrometIzlazaVrednosti(magacinskaKarticaDTO.getPrometIzlazaVrednosti());
		magacinskaKartica.setPrometUlazaKolicina(magacinskaKarticaDTO.getPrometUlazaKolicina());
		magacinskaKartica.setPrometUlazaVrednosti(magacinskaKarticaDTO.getPrometUlazaVrednosti());
		magacinskaKartica.setUkupnaKolicina(magacinskaKarticaDTO.getUkupnaKolicina());
		magacinskaKartica.setUkupnaVrednost(magacinskaKarticaDTO.getUkupnaVrednost());
		return magacinskaKartica;
	}

}
