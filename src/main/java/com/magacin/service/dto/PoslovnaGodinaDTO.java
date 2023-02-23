package com.magacin.service.dto;

import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.NotNull;

import com.magacin.domain.MagacinskaKartica;
import com.magacin.domain.Popis;
import com.magacin.domain.PoslovanGodina;
import com.magacin.domain.Preduzece;
import com.magacin.domain.PrometniDokument;

public class PoslovnaGodinaDTO {
	private Long id;
    @NotNull(message = "Godina ne moze biti prazna!")
    private Long godina;
    private boolean zakljucena;
    private Set<Popis> popis = new HashSet<>();
    private Set<MagacinskaKartica> magacinskaKartica = new HashSet<>();
    private Set<PrometniDokument> prometniDokument = new HashSet<>();
    private Preduzece preduzece;

    public PoslovnaGodinaDTO() {
        super();
    }

    

    public PoslovnaGodinaDTO(Long id, @NotNull(message = "Godina ne moze biti prazna!") Long godina, boolean zakljucena,
			Set<Popis> popis, Set<MagacinskaKartica> magacinskaKartica, Set<PrometniDokument> prometniDokument,
			Preduzece preduzece) {
		super();
		this.id = id;
		this.godina = godina;
		this.zakljucena = zakljucena;
		this.popis = popis;
		this.magacinskaKartica = magacinskaKartica;
		this.prometniDokument = prometniDokument;
		this.preduzece = preduzece;
	}



	public PoslovnaGodinaDTO(PoslovanGodina poslovnaGodina){
        this(poslovnaGodina.getId(), poslovnaGodina.getGodina(), poslovnaGodina.getZakljucena(), poslovnaGodina.getPopis(), poslovnaGodina.getMagacinskaKartica(), poslovnaGodina.getPrometniDokument(), poslovnaGodina.getPreduzece());
    }



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public Long getGodina() {
		return godina;
	}



	public void setGodina(Long godina) {
		this.godina = godina;
	}



	public boolean isZakljucena() {
		return zakljucena;
	}



	public void setZakljucena(boolean zakljucena) {
		this.zakljucena = zakljucena;
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



	public Preduzece getPreduzece() {
		return preduzece;
	}



	public void setPreduzece(Preduzece preduzece) {
		this.preduzece = preduzece;
	}
	

}
