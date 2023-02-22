package com.magacin.service.dto;

import com.magacin.domain.Magacin;
import com.magacin.domain.PoslovanGodina;
import java.time.LocalDate;

public class PrometniDokumentDTO {

    private Long id;

    private LocalDate datum;

    private String vrsta;

    private String status;

    private PoslovniPartnerDTO poslovniPartner;

    private Magacin magacin;

    private PoslovanGodina poslovanGodina;

    public PrometniDokumentDTO(
        Long id,
        LocalDate datum,
        String vrsta,
        String status,
        PoslovniPartnerDTO poslovniPartner,
        Magacin magacin,
        PoslovanGodina poslovanGodina
    ) {
        super();
        this.id = id;
        this.datum = datum;
        this.vrsta = vrsta;
        this.status = status;
        this.poslovniPartner = poslovniPartner;
        this.magacin = magacin;
        this.poslovanGodina = poslovanGodina;
    }

    public PrometniDokumentDTO() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public String getVrsta() {
        return vrsta;
    }

    public void setVrsta(String vrsta) {
        this.vrsta = vrsta;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public PoslovniPartnerDTO getPoslovniPartner() {
        return poslovniPartner;
    }

    public void setPoslovniPartner(PoslovniPartnerDTO poslovniPartner) {
        this.poslovniPartner = poslovniPartner;
    }

    public Magacin getMagacin() {
        return magacin;
    }

    public void setMagacin(Magacin magacin) {
        this.magacin = magacin;
    }

    public PoslovanGodina getPoslovanGodina() {
        return poslovanGodina;
    }

    public void setPoslovanGodina(PoslovanGodina poslovanGodina) {
        this.poslovanGodina = poslovanGodina;
    }
}
