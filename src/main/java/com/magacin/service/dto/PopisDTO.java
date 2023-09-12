package com.magacin.service.dto;

import com.magacin.domain.Popis;
import java.time.LocalDate;

public class PopisDTO {

    private Long id;

    private LocalDate datum_popisa;

    private Long brojPopisa;

    private boolean statusPopisa;

    private MagacinDTO magacinDTO;

    private PoslovnaGodinaDTO poslovnaGodinaDTO;

    public PopisDTO(
        Long id,
        LocalDate datum_popisa,
        Long brojPopisa,
        boolean statusPopisa,
        MagacinDTO magacinDTO,
        PoslovnaGodinaDTO poslovnaGodinaDTO
    ) {
        this.id = id;
        this.datum_popisa = datum_popisa;
        this.brojPopisa = brojPopisa;
        this.statusPopisa = statusPopisa;
        this.magacinDTO = magacinDTO;
        this.poslovnaGodinaDTO = poslovnaGodinaDTO;
    }

    public PopisDTO(Popis popis) {
        this(
            popis.getId(),
            popis.getDatumPopisa(),
            popis.getBrojPopisa(),
            popis.getStatusPopisa(),
            new MagacinDTO(popis.getMagacin()),
            new PoslovnaGodinaDTO(popis.getPoslovanGodina())
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatum_popisa() {
        return datum_popisa;
    }

    public void setDatum_popisa(LocalDate datum_popisa) {
        this.datum_popisa = datum_popisa;
    }

    public Long getBrojPopisa() {
        return brojPopisa;
    }

    public void setBrojPopisa(Long brojPopisa) {
        this.brojPopisa = brojPopisa;
    }

    public boolean isStatusPopisa() {
        return statusPopisa;
    }

    public void setStatusPopisa(boolean statusPopisa) {
        this.statusPopisa = statusPopisa;
    }

    public MagacinDTO getMagacinDTO() {
        return magacinDTO;
    }

    public void setMagacinDTO(MagacinDTO magacinDTO) {
        this.magacinDTO = magacinDTO;
    }

    public PoslovnaGodinaDTO getPoslovnaGodinaDTO() {
        return poslovnaGodinaDTO;
    }

    public void setPoslovnaGodinaDTO(PoslovnaGodinaDTO poslovnaGodinaDTO) {
        this.poslovnaGodinaDTO = poslovnaGodinaDTO;
    }
}
