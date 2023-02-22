package com.magacin.service.dto;

import javax.validation.constraints.NotNull;

import com.magacin.domain.PoslovanGodina;

public class PoslovnaGodinaDTO {
	private Long id;
    @NotNull(message = "Godina ne moze biti prazna!")
    private Long godina;
    private boolean zakljucan;

    public PoslovnaGodinaDTO() {
        super();
    }

    public PoslovnaGodinaDTO(Long id, Long godina, boolean zakljucan) {
        this.id = id;
        this.godina = godina;
        this.zakljucan = zakljucan;
    }

    public PoslovnaGodinaDTO(PoslovanGodina poslovnaGodina){
        this(poslovnaGodina.getId(), poslovnaGodina.getGodina(), poslovnaGodina.getZakljucena());
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

	public boolean isZakljucan() {
		return zakljucan;
	}

	public void setZakljucan(boolean zakljucan) {
		this.zakljucan = zakljucan;
	}

	

}
