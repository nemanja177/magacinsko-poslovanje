package com.magacin.service.dto;

import com.magacin.domain.Artikal;
import com.magacin.domain.PrometniDokument;
import com.magacin.domain.StavkaPopisa;

public class StavkaPopisaDTO {

    private Long id;

    private Long kolicinaPopisa;

    private Long kolicinaPoKnjigama;

    private Artikal artikal;

    private PrometniDokumentDTO prometniDokument;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getKolicinaPopisa() {
        return kolicinaPopisa;
    }

    public void setKolicinaPopisa(Long kolicinaPopisa) {
        this.kolicinaPopisa = kolicinaPopisa;
    }

    public Long getKolicinaPoKnjigama() {
        return kolicinaPoKnjigama;
    }

    public void setKolicinaPoKnjigama(Long kolicinaPoKnjigama) {
        this.kolicinaPoKnjigama = kolicinaPoKnjigama;
    }

    public Artikal getArtikal() {
        return artikal;
    }

    public void setArtikal(Artikal artikal) {
        this.artikal = artikal;
    }

    public PrometniDokumentDTO getPrometniDokument() {
        return prometniDokument;
    }

    public void setPrometniDokument(PrometniDokumentDTO prometniDokument) {
        this.prometniDokument = prometniDokument;
    }

    public StavkaPopisaDTO(Long id, Long kolicinaPopisa, Long kolicinaPoKnjigama, Artikal artikal, PrometniDokumentDTO prometniDokument) {
        super();
        this.id = id;
        this.kolicinaPopisa = kolicinaPopisa;
        this.kolicinaPoKnjigama = kolicinaPoKnjigama;
        this.artikal = artikal;
        this.prometniDokument = prometniDokument;
    }

    public StavkaPopisaDTO() {}

    public StavkaPopisaDTO(StavkaPopisa stavka) {
        this(stavka.getId(), stavka.getKolicinaPopisa(), stavka.getKolicinaPoKnjigama(), null, null);
    }
}
