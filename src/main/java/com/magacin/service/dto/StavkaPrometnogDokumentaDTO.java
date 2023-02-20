package com.magacin.service.dto;

import com.magacin.domain.Artikal;
import com.magacin.domain.Popis;

public class StavkaPrometnogDokumentaDTO {

    private Long id;

    private Long kolicina;

    private Long cena;

    private Long vrednost;

    private Artikal artikal;

    private Popis popis;

    public StavkaPrometnogDokumentaDTO(Long id, Long kolicina, Long cena, Long vrednost, Artikal artikal, Popis popis) {
        super();
        this.id = id;
        this.kolicina = kolicina;
        this.cena = cena;
        this.vrednost = vrednost;
        this.artikal = artikal;
        this.popis = popis;
    }

    public StavkaPrometnogDokumentaDTO() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Artikal getArtikal() {
        return artikal;
    }

    public void setArtikal(Artikal artikal) {
        this.artikal = artikal;
    }

    public Popis getPopis() {
        return popis;
    }

    public void setPopis(Popis popis) {
        this.popis = popis;
    }
}
