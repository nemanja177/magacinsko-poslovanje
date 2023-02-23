package com.magacin.service.dto.support;

import org.springframework.core.convert.converter.Converter;

import com.magacin.domain.JedinicaMere;
import com.magacin.service.dto.JedinicaMereDTO;

public class JedinicaMereToJedinicaMereDTO implements Converter<JedinicaMere, JedinicaMereDTO>{

	@Override
	public JedinicaMereDTO convert(JedinicaMere jedinicaMere) {
		JedinicaMereDTO jedinicaMereDTO = new JedinicaMereDTO();
		jedinicaMereDTO.setId(jedinicaMere.getId());
		jedinicaMereDTO.setNazivJedinice(jedinicaMere.getNazivJedinice());
		jedinicaMereDTO.setSkraceniNaziv(jedinicaMere.getSkraceniNaziv());
		jedinicaMereDTO.setArtikal(jedinicaMere.getArtikal());
		return jedinicaMereDTO;
	}

}
