package com.magacin.service.dto;

import com.magacin.domain.Magacin;
import com.magacin.domain.PoslovanGodina;
import com.magacin.domain.PoslovniPartner;
import com.magacin.domain.PrometniDokument;
import com.magacin.domain.Radnik;
import java.time.LocalDate;

public class PrometniDokumentDTO {

    private Long id;

    private LocalDate datum;

    private String vrsta;

    private String status;

    private PoslovniPartnerDTO poslovniPartner;

    private MagacinDTO magacin;

    private PoslovnaGodinaDTO poslovanGodina;

    public PrometniDokumentDTO(
        Long id,
        LocalDate datum,
        String vrsta,
        String status,
        PoslovniPartnerDTO poslovniPartner,
        MagacinDTO magacin,
        PoslovnaGodinaDTO poslovanGodina
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

    public PrometniDokumentDTO(PrometniDokument dokument) {
        this(dokument.getId(), dokument.getDatum(), dokument.getVrsta(), dokument.getStatus(), null, null, null);
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

    public MagacinDTO getMagacin() {
        return magacin;
    }

    public void setMagacin(MagacinDTO magacin) {
        this.magacin = magacin;
    }

    public PoslovnaGodinaDTO getPoslovanGodina() {
        return poslovanGodina;
    }

    public void setPoslovanGodina(PoslovnaGodinaDTO poslovanGodina) {
        this.poslovanGodina = poslovanGodina;
    }
}
