package com.magacin.service.dto;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.magacin.domain.Artikal;
import com.magacin.domain.JedinicaMere;

public class JedinicaMereDTO {
	private Long id;
    private String nazivJedinice;
    private String skraceniNaziv;
    private Artikal artikal;
    
	public JedinicaMereDTO() {
		super();
	}

	public JedinicaMereDTO(Long id, String nazivJedinice, String skraceniNaziv, Artikal artikal) {
		super();
		this.id = id;
		this.nazivJedinice = nazivJedinice;
		this.skraceniNaziv = skraceniNaziv;
		this.artikal = artikal;
	}
	
    public JedinicaMereDTO(JedinicaMere jedinicaMere) {
    	this(jedinicaMere.getId(),
    			jedinicaMere.getNazivJedinice(),
    			jedinicaMere.getSkraceniNaziv(),
    			jedinicaMere.getArtikal());
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNazivJedinice() {
		return nazivJedinice;
	}

	public void setNazivJedinice(String nazivJedinice) {
		this.nazivJedinice = nazivJedinice;
	}

	public String getSkraceniNaziv() {
		return skraceniNaziv;
	}

	public void setSkraceniNaziv(String skraceniNaziv) {
		this.skraceniNaziv = skraceniNaziv;
	}

	public Artikal getArtikal() {
		return artikal;
	}

	public void setArtikal(Artikal artikal) {
		this.artikal = artikal;
	}
    
    
}
