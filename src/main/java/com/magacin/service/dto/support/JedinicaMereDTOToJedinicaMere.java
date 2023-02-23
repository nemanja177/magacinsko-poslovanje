package com.magacin.service.dto.support;

import org.springframework.core.convert.converter.Converter;

import com.magacin.domain.JedinicaMere;
import com.magacin.service.dto.JedinicaMereDTO;

public class JedinicaMereDTOToJedinicaMere implements Converter<JedinicaMereDTO, JedinicaMere>{

	@Override
	public JedinicaMere convert(JedinicaMereDTO jedinicaMereDTO) {
		JedinicaMere jedinicaMere = new JedinicaMere();
		jedinicaMere.setId(jedinicaMereDTO.getId());
		jedinicaMere.setNazivJedinice(jedinicaMereDTO.getNazivJedinice());
		jedinicaMere.setSkraceniNaziv(jedinicaMereDTO.getSkraceniNaziv());
		jedinicaMere.setArtikal(jedinicaMereDTO.getArtikal());
		return jedinicaMere;
	}

}
