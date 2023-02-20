package com.magacin.service.dto;

import com.magacin.domain.Magacin;
import com.magacin.domain.PoslovanGodina;

public class PreduzeceDTO {

    private Long id;

    private String naziv;

    private String adresa;

    private String telefon;

    private String mib;

    private String pib;

    private PoslovniPartnerDTO poslovniPartnerDTO;

    private RadnikDTO radnikDTO;

    private Magacin magacin;

    private PoslovanGodina poslovnaGodina;

    public PreduzeceDTO(
        Long id,
        String naziv,
        String adresa,
        String telefon,
        String mib,
        String pib,
        PoslovniPartnerDTO poslovniPartnerDTO,
        RadnikDTO radnikDTO,
        Magacin magacin,
        PoslovanGodina poslovnaGodina
    ) {
        super();
        this.id = id;
        this.naziv = naziv;
        this.adresa = adresa;
        this.telefon = telefon;
        this.mib = mib;
        this.pib = pib;
        this.poslovniPartnerDTO = poslovniPartnerDTO;
        this.radnikDTO = radnikDTO;
        this.magacin = magacin;
        this.poslovnaGodina = poslovnaGodina;
    }

    private PreduzeceDTO() {}
}
