package com.magacin.service.dto;

import com.magacin.domain.Magacin;
import com.magacin.domain.PoslovanGodina;
import com.magacin.domain.PoslovniPartner;
import com.magacin.domain.Preduzece;

public class PreduzeceDTO {

    private Long id;

    private String naziv;

    private String adresa;

    private String telefon;

    private String mib;

    private String pib;

    public PreduzeceDTO(Long id, String naziv, String adresa, String telefon, String mib, String pib) {
        super();
        this.id = id;
        this.naziv = naziv;
        this.adresa = adresa;
        this.telefon = telefon;
        this.mib = mib;
        this.pib = pib;
    }

    public PreduzeceDTO(Preduzece preduzece) {
        this(
            preduzece.getId(),
            preduzece.getNaziv(),
            preduzece.getAdresa(),
            preduzece.getTelefon(),
            preduzece.getMib(),
            preduzece.getPib()
        );
    }

    private PreduzeceDTO() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
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

    public String getMib() {
        return mib;
    }

    public void setMib(String mib) {
        this.mib = mib;
    }

    public String getPib() {
        return pib;
    }

    public void setPib(String pib) {
        this.pib = pib;
    }
}
