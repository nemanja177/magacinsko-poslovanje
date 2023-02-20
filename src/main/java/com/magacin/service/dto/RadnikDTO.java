package com.magacin.service.dto;

import com.magacin.domain.Magacin;
import com.magacin.domain.Preduzece;

public class RadnikDTO {

    private Long id;

    private String name;

    private String prezime;

    private String adresa;

    private String telefon;

    private Preduzece preduzece;

    private Magacin magacin;

    public RadnikDTO(Long id, String name, String prezime, String adresa, String telefon, Preduzece preduzece, Magacin magacin) {
        super();
        this.id = id;
        this.name = name;
        this.prezime = prezime;
        this.adresa = adresa;
        this.telefon = telefon;
        this.preduzece = preduzece;
        this.magacin = magacin;
    }

    private RadnikDTO() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Preduzece getPreduzece() {
        return preduzece;
    }

    public void setPreduzece(Preduzece preduzece) {
        this.preduzece = preduzece;
    }

    public Magacin getMagacin() {
        return magacin;
    }

    public void setMagacin(Magacin magacin) {
        this.magacin = magacin;
    }
}
