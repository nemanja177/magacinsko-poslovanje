package com.magacin.service.dto.support;

import org.springframework.core.convert.converter.Converter;

import com.magacin.domain.Artikal;
import com.magacin.service.dto.ArtikalDTO;

public class ArtikalDTOToArtikal implements Converter<ArtikalDTO, Artikal> {

	@Override
	public Artikal convert(ArtikalDTO artikalDTO) {
		Artikal artikal = new Artikal();
		artikal.setId(artikalDTO.getId());
		artikal.setJedinicaMere(artikalDTO.getJedinicaMere());
		artikal.setMagacinskaKarticaId(artikalDTO.getMagacinskaKarticaId());
		artikal.setNaziv(artikalDTO.getNaziv());
		artikal.setOpis(artikalDTO.getOpis());
		artikal.setPakovanje(artikalDTO.getPakovanje());
		artikal.setStavakaDokumenta(artikalDTO.getStavakaDokumenta());
		artikal.setStavkaPropisa(artikalDTO.getStavkaPropisa());
		return artikal;
	}
}
