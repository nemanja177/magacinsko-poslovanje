package com.magacin.service.dto;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.magacin.domain.Artikal;
import com.magacin.domain.JedinicaMere;
import com.magacin.domain.MagacinskaKartica;
import com.magacin.domain.StavkaPopisa;
import com.magacin.domain.StavkaPrometnogDokumenta;

public class ArtikalDTO {

	private Long id;
    private String naziv;
    private String opis;
    private String pakovanje;
    private MagacinskaKartica magacinskaKarticaId;
    private StavkaPrometnogDokumenta stavakaDokumenta;
    private StavkaPopisa stavkaPropisa;
    private Set<JedinicaMere> jedinicaMere = new HashSet<>();
    
	public ArtikalDTO() {
		super();
	}

	public ArtikalDTO(Long id, String naziv, String opis, String pakovanje, MagacinskaKartica magacinskaKarticaId,
			StavkaPrometnogDokumenta stavakaDokumenta, StavkaPopisa stavkaPropisa, Set<JedinicaMere> jedinicaMere) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.opis = opis;
		this.pakovanje = pakovanje;
		this.magacinskaKarticaId = magacinskaKarticaId;
		this.stavakaDokumenta = stavakaDokumenta;
		this.stavkaPropisa = stavkaPropisa;
		this.jedinicaMere = jedinicaMere;
	}
	
    public ArtikalDTO(Artikal artikal) {
    	this(artikal.getId(),
    			artikal.getNaziv(),
    			artikal.getOpis(),
    			artikal.getPakovanje(),
    			artikal.getMagacinskaKarticaId(),
    			artikal.getStavakaDokumenta(),
    			artikal.getStavkaPropisa(),
    			artikal.getJedinicaMere());
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public String getPakovanje() {
		return pakovanje;
	}

	public void setPakovanje(String pakovanje) {
		this.pakovanje = pakovanje;
	}

	public MagacinskaKartica getMagacinskaKarticaId() {
		return magacinskaKarticaId;
	}

	public void setMagacinskaKarticaId(MagacinskaKartica magacinskaKarticaId) {
		this.magacinskaKarticaId = magacinskaKarticaId;
	}

	public StavkaPrometnogDokumenta getStavakaDokumenta() {
		return stavakaDokumenta;
	}

	public void setStavakaDokumenta(StavkaPrometnogDokumenta stavakaDokumenta) {
		this.stavakaDokumenta = stavakaDokumenta;
	}

	public StavkaPopisa getStavkaPropisa() {
		return stavkaPropisa;
	}

	public void setStavkaPropisa(StavkaPopisa stavkaPropisa) {
		this.stavkaPropisa = stavkaPropisa;
	}

	public Set<JedinicaMere> getJedinicaMere() {
		return jedinicaMere;
	}

	public void setJedinicaMere(Set<JedinicaMere> jedinicaMere) {
		this.jedinicaMere = jedinicaMere;
	}
    
    
}
