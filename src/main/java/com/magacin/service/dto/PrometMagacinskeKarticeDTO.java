package com.magacin.service.dto;

import com.magacin.domain.MagacinskaKartica;
import java.time.LocalDate;

public class PrometMagacinskeKarticeDTO {

    private Long id;

    private LocalDate datumPrometa;

    private Long kolicina;

    private Long cena;

    private Long vrednost;

    private String dokument;

    private String smer;

    private MagacinskaKartica magacinskaKartica;

    public PrometMagacinskeKarticeDTO(
        Long id,
        LocalDate datumPrometa,
        Long kolicina,
        Long cena,
        Long vrednost,
        String dokument,
        String smer,
        MagacinskaKartica magacinskaKartica
    ) {
        super();
        this.id = id;
        this.datumPrometa = datumPrometa;
        this.kolicina = kolicina;
        this.cena = cena;
        this.vrednost = vrednost;
        this.dokument = dokument;
        this.smer = smer;
        this.magacinskaKartica = magacinskaKartica;
    }

    public PrometMagacinskeKarticeDTO() {
        super();
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

    public String getSmer() {
        return smer;
    }

    public void setSmer(String smer) {
        this.smer = smer;
    }

    public MagacinskaKartica getMagacinskaKartica() {
        return magacinskaKartica;
    }

    public void setMagacinskaKartica(MagacinskaKartica magacinskaKartica) {
        this.magacinskaKartica = magacinskaKartica;
    }
}
