package com.magacin.service.dto;

import java.util.HashSet;
import java.util.Set;

import com.magacin.domain.Magacin;
import com.magacin.domain.MagacinskaKartica;
import com.magacin.domain.Popis;
import com.magacin.domain.Preduzece;
import com.magacin.domain.PrometniDokument;
import com.magacin.domain.Radnik;

public class MagacinDTO {
	private Long id;
    private String naziv;
    private Preduzece preduzece;
    private Set<Radnik> radnici = new HashSet<>();
    private Set<Popis> popis = new HashSet<>();
    private Set<MagacinskaKartica> magacinskaKartica = new HashSet<>();
    private Set<PrometniDokument> prometniDokument = new HashSet<>();
	public MagacinDTO() {
		super();
	}
	public MagacinDTO(Long id, String naziv, Preduzece preduzece, Set<Radnik> radnici, Set<Popis> popis,
			Set<MagacinskaKartica> magacinskaKartica, Set<PrometniDokument> prometniDokument) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.preduzece = preduzece;
		this.radnici = radnici;
		this.popis = popis;
		this.magacinskaKartica = magacinskaKartica;
		this.prometniDokument = prometniDokument;
	}
    
    public MagacinDTO(Magacin magacin) {
    	this(magacin.getId(),
    			magacin.getNaziv(),
    			magacin.getPreduzece(),
    			magacin.getRadnici(),
    			magacin.getPopis(),
    			magacin.getMagacinskaKartica(),
    			magacin.getPrometniDokument());
    	
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
	public Preduzece getPreduzece() {
		return preduzece;
	}
	public void setPreduzece(Preduzece preduzece) {
		this.preduzece = preduzece;
	}
	public Set<Radnik> getRadnici() {
		return radnici;
	}
	public void setRadnici(Set<Radnik> radnici) {
		this.radnici = radnici;
	}
	public Set<Popis> getPopis() {
		return popis;
	}
	public void setPopis(Set<Popis> popis) {
		this.popis = popis;
	}
	public Set<MagacinskaKartica> getMagacinskaKartica() {
		return magacinskaKartica;
	}
	public void setMagacinskaKartica(Set<MagacinskaKartica> magacinskaKartica) {
		this.magacinskaKartica = magacinskaKartica;
	}
	public Set<PrometniDokument> getPrometniDokument() {
		return prometniDokument;
	}
	public void setPrometniDokument(Set<PrometniDokument> prometniDokument) {
		this.prometniDokument = prometniDokument;
	}
    
    
}
