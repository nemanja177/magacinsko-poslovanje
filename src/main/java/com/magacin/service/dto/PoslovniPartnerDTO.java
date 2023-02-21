package com.magacin.service.dto;

import com.magacin.domain.PoslovniPartner;

public class PoslovniPartnerDTO {

    private Long id;

    private String ime;

    private String prezime;

    private String jmbg;

    private String adresa;

    private PreduzeceDTO preduzece;

    private PrometniDokumentDTO prometniDokumentDTO;

    public PoslovniPartnerDTO(
        Long id,
        String ime,
        String prezime,
        String jmbg,
        String adresa,
        PreduzeceDTO preduzece,
        PrometniDokumentDTO prometniDokumentDTO
    ) {
        super();
        this.id = id;
        this.ime = ime;
        this.prezime = prezime;
        this.jmbg = jmbg;
        this.adresa = adresa;
        this.preduzece = preduzece;
        this.prometniDokumentDTO = prometniDokumentDTO;
    }

    public PoslovniPartnerDTO(PoslovniPartner partner) {
        this(partner.getId(), partner.getIme(), partner.getPrezime(), partner.getJmbg(), partner.getAdresa(), null, null);
    }

    public PoslovniPartnerDTO() {
        super();
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

    public String getJmbg() {
        return jmbg;
    }

    public void setJmbg(String jmbg) {
        this.jmbg = jmbg;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public PreduzeceDTO getPreduzece() {
        return preduzece;
    }

    public void setPreduzece(PreduzeceDTO preduzece) {
        this.preduzece = preduzece;
    }

    public PrometniDokumentDTO getPrometniDokumentDTO() {
        return prometniDokumentDTO;
    }

    public void setPrometniDokumentDTO(PrometniDokumentDTO prometniDokumentDTO) {
        this.prometniDokumentDTO = prometniDokumentDTO;
    }
}
