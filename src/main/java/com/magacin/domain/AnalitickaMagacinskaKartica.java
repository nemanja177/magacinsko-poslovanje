package com.magacin.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;

/**
 * A AnalitickaMagacinskaKartica.
 */
@Entity
@Table(name = "analiticka_magacinska_kartica")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AnalitickaMagacinskaKartica implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "datum_prometa")
    private LocalDate datumPrometa;

    @Column(name = "kolicina")
    private Long kolicina;

    @Column(name = "cena")
    private Long cena;

    @Column(name = "vrednost")
    private Long vrednost;

    @Column(name = "dokument")
    private String dokument;

    @Column(name = "smer")
    private Boolean smer;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AnalitickaMagacinskaKartica id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatumPrometa() {
        return this.datumPrometa;
    }

    public AnalitickaMagacinskaKartica datumPrometa(LocalDate datumPrometa) {
        this.setDatumPrometa(datumPrometa);
        return this;
    }

    public void setDatumPrometa(LocalDate datumPrometa) {
        this.datumPrometa = datumPrometa;
    }

    public Long getKolicina() {
        return this.kolicina;
    }

    public AnalitickaMagacinskaKartica kolicina(Long kolicina) {
        this.setKolicina(kolicina);
        return this;
    }

    public void setKolicina(Long kolicina) {
        this.kolicina = kolicina;
    }

    public Long getCena() {
        return this.cena;
    }

    public AnalitickaMagacinskaKartica cena(Long cena) {
        this.setCena(cena);
        return this;
    }

    public void setCena(Long cena) {
        this.cena = cena;
    }

    public Long getVrednost() {
        return this.vrednost;
    }

    public AnalitickaMagacinskaKartica vrednost(Long vrednost) {
        this.setVrednost(vrednost);
        return this;
    }

    public void setVrednost(Long vrednost) {
        this.vrednost = vrednost;
    }

    public String getDokument() {
        return this.dokument;
    }

    public AnalitickaMagacinskaKartica dokument(String dokument) {
        this.setDokument(dokument);
        return this;
    }

    public void setDokument(String dokument) {
        this.dokument = dokument;
    }

    public Boolean getSmer() {
        return this.smer;
    }

    public AnalitickaMagacinskaKartica smer(Boolean smer) {
        this.setSmer(smer);
        return this;
    }

    public void setSmer(Boolean smer) {
        this.smer = smer;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AnalitickaMagacinskaKartica)) {
            return false;
        }
        return id != null && id.equals(((AnalitickaMagacinskaKartica) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AnalitickaMagacinskaKartica{" +
            "id=" + getId() +
            ", datumPrometa='" + getDatumPrometa() + "'" +
            ", kolicina=" + getKolicina() +
            ", cena=" + getCena() +
            ", vrednost=" + getVrednost() +
            ", dokument='" + getDokument() + "'" +
            ", smer='" + getSmer() + "'" +
            "}";
    }
}
