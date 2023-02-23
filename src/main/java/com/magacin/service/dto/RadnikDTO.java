package com.magacin.service.dto;

import com.magacin.domain.Magacin;
import com.magacin.domain.Preduzece;
import com.magacin.domain.Radnik;

public class RadnikDTO {

    private Long id;

    private String ime;

    private String prezime;

    private String adresa;

    private String telefon;

    private PreduzeceDTO preduzece;

    private MagacinDTO magacin;

    public RadnikDTO(Long id, String ime, String prezime, String adresa, String telefon, PreduzeceDTO preduzece, MagacinDTO magacin) {
        super();
        this.id = id;
        this.ime = ime;
        this.prezime = prezime;
        this.adresa = adresa;
        this.telefon = telefon;
        this.preduzece = preduzece;
        this.magacin = magacin;
    }

    public RadnikDTO() {
        super();
    }

    public RadnikDTO(Radnik radnik) {
        this(radnik.getId(), radnik.getIme(), radnik.getPrezime(), radnik.getAdresa(), radnik.getTelefon(), null, null);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public PreduzeceDTO getPreduzece() {
        return preduzece;
    }

    public void setPreduzece(PreduzeceDTO preduzece) {
        this.preduzece = preduzece;
    }

    public MagacinDTO getMagacin() {
        return magacin;
    }

    public void setMagacin(MagacinDTO magacin) {
        this.magacin = magacin;
    }
}
