package com.magacin.service.dto.support;

import org.springframework.core.convert.converter.Converter;

import com.magacin.domain.MagacinskaKartica;
import com.magacin.service.dto.MagacinskaKarticaDTO;

public class MagacinskaKarticaToMagacinskaKarticaDTO implements Converter<MagacinskaKartica, MagacinskaKarticaDTO> {

	@Override
	public MagacinskaKarticaDTO convert(MagacinskaKartica magacinskaKartica) {
		MagacinskaKarticaDTO magacinskaKarticaDTO = new MagacinskaKarticaDTO();
		magacinskaKarticaDTO.setId(magacinskaKartica.getId());
		magacinskaKarticaDTO.setPocetnoStanjeKolicina(magacinskaKartica.getPocetnoStanjeKolicina());
		magacinskaKarticaDTO.setPocetnoStanjeVrednosti(magacinskaKartica.getPocetnoStanjeVrednosti());
		magacinskaKarticaDTO.setPrometIzlazaKolicina(magacinskaKartica.getPrometIzlazaKolicina());
		magacinskaKarticaDTO.setPrometIzlazaVrednosti(magacinskaKartica.getPrometIzlazaVrednosti());
		magacinskaKarticaDTO.setPrometUlazaKolicina(magacinskaKartica.getPrometUlazaKolicina());
		magacinskaKarticaDTO.setPrometUlazaVrednosti(magacinskaKartica.getPrometUlazaVrednosti());
		magacinskaKarticaDTO.setUkupnaKolicina(magacinskaKartica.getUkupnaKolicina());
		magacinskaKarticaDTO.setUkupnaVrednost(magacinskaKartica.getUkupnaVrednost());
		return magacinskaKarticaDTO;
	}
	
	

}
