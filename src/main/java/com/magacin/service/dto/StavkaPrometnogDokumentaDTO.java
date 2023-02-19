package com.magacin.service.dto;

public class StavkaPrometnogDokumentaDTO {

    private Long id;

    private Long kolicina;

    private Long cena;

    private Long vrednost;

    public StavkaPrometnogDokumentaDTO(Long id, Long kolicina, Long cena, Long vrednost) {
        super();
        this.id = id;
        this.kolicina = kolicina;
        this.cena = cena;
        this.vrednost = vrednost;
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
}
