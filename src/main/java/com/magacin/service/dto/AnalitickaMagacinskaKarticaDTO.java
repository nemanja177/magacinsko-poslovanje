package com.magacin.service.dto;

import java.time.LocalDate;

import com.magacin.domain.AnalitickaMagacinskaKartica;


public class AnalitickaMagacinskaKarticaDTO {
	private Long id;
    private LocalDate datumPrometa;
    private Long kolicina;
    private Long cena;
    private Long vrednost;
    private String dokument;
    private Boolean smer;
    
	public AnalitickaMagacinskaKarticaDTO() {
		super();
	}

	public AnalitickaMagacinskaKarticaDTO(Long id, LocalDate datumPrometa, Long kolicina, Long cena, Long vrednost,
			String dokument, Boolean smer) {
		super();
		this.id = id;
		this.datumPrometa = datumPrometa;
		this.kolicina = kolicina;
		this.cena = cena;
		this.vrednost = vrednost;
		this.dokument = dokument;
		this.smer = smer;
	}
    
    public AnalitickaMagacinskaKarticaDTO(AnalitickaMagacinskaKartica analitickaMagacinskaKarticaDTO) {
    	this(analitickaMagacinskaKarticaDTO.getId(),
    			analitickaMagacinskaKarticaDTO.getDatumPrometa(),
    			analitickaMagacinskaKarticaDTO.getKolicina(),
    			analitickaMagacinskaKarticaDTO.getCena(),
    			analitickaMagacinskaKarticaDTO.getVrednost(),
    			analitickaMagacinskaKarticaDTO.getDokument(),
    			analitickaMagacinskaKarticaDTO.getSmer());
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getDatumPrometa() {
		return datumPrometa;
	}

	public void setDatumPrometa(LocalDate datumPrometa) {
		this.datumPrometa = datumPrometa;
	}

	public Long getKolicina() {
		return kolicina;
	}

	public void setKolicina(Long kolicina) {
		this.kolicina = kolicina;
	}

	public Long getCena() {
		return cena;
	}

	public void setCena(Long cena) {
		this.cena = cena;
	}

	public Long getVrednost() {
		return vrednost;
	}

	public void setVrednost(Long vrednost) {
		this.vrednost = vrednost;
	}

	public String getDokument() {
		return dokument;
	}

	public void setDokument(String dokument) {
		this.dokument = dokument;
	}

	public Boolean getSmer() {
		return smer;
	}

	public void setSmer(Boolean smer) {
		this.smer = smer;
	}
    
    
}
