package com.magacin.service.dto.support;

import org.springframework.core.convert.converter.Converter;

import com.magacin.domain.Artikal;
import com.magacin.service.dto.ArtikalDTO;

public class ArtikalToArtikalDTO implements Converter<Artikal, ArtikalDTO> {

	@Override
	public ArtikalDTO convert(Artikal artikal) {
		ArtikalDTO artikalDTO = new ArtikalDTO();
		artikalDTO.setId(artikal.getId());
		artikalDTO.setJedinicaMere(artikal.getJedinicaMere());
		artikalDTO.setMagacinskaKarticaId(artikal.getMagacinskaKarticaId());
		artikalDTO.setNaziv(artikal.getNaziv());
		artikalDTO.setOpis(artikal.getOpis());
		artikalDTO.setPakovanje(artikal.getPakovanje());
		artikalDTO.setStavakaDokumenta(artikal.getStavakaDokumenta());
		artikalDTO.setStavkaPropisa(artikal.getStavkaPropisa());
		return artikalDTO;
	}
}
