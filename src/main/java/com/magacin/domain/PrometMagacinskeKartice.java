package com.magacin.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;

/**
 * A PrometMagacinskeKartice.
 */
@Entity
@Table(name = "promet_magacinske_kartice")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PrometMagacinskeKartice implements Serializable {

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
    private String smer;

    @ManyToOne
    @JoinColumn(name = "magacinskaKrticaId", referencedColumnName = "id", nullable = false)
    private MagacinskaKartica magacinskaKartica;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    
    

    public Long getId() {
        return this.id;
    }

    public PrometMagacinskeKartice() {
		super();
	}

	public PrometMagacinskeKartice(LocalDate datumPrometa, Long kolicina, Long cena, Long vrednost, String dokument,
			String smer, MagacinskaKartica magacinskaKartica) {
		super();
		this.datumPrometa = datumPrometa;
		this.kolicina = kolicina;
		this.cena = cena;
		this.vrednost = vrednost;
		this.dokument = dokument;
		this.smer = smer;
		this.magacinskaKartica = magacinskaKartica;
	}

	public MagacinskaKartica getMagacinskaKartica() {
		return magacinskaKartica;
	}

	public void setMagacinskaKartica(MagacinskaKartica magacinskaKartica) {
		this.magacinskaKartica = magacinskaKartica;
	}

	public PrometMagacinskeKartice id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatumPrometa() {
        return this.datumPrometa;
    }

    public PrometMagacinskeKartice datumPrometa(LocalDate datumPrometa) {
        this.setDatumPrometa(datumPrometa);
        return this;
    }

    public void setDatumPrometa(LocalDate datumPrometa) {
        this.datumPrometa = datumPrometa;
    }

    public Long getKolicina() {
        return this.kolicina;
    }

    public PrometMagacinskeKartice kolicina(Long kolicina) {
        this.setKolicina(kolicina);
        return this;
    }

    public void setKolicina(Long kolicina) {
        this.kolicina = kolicina;
    }

    public Long getCena() {
        return this.cena;
    }

    public PrometMagacinskeKartice cena(Long cena) {
        this.setCena(cena);
        return this;
    }

    public void setCena(Long cena) {
        this.cena = cena;
    }

    public Long getVrednost() {
        return this.vrednost;
    }

    public PrometMagacinskeKartice vrednost(Long vrednost) {
        this.setVrednost(vrednost);
        return this;
    }

    public void setVrednost(Long vrednost) {
        this.vrednost = vrednost;
    }

    public String getDokument() {
        return this.dokument;
    }

    public PrometMagacinskeKartice dokument(String dokument) {
        this.setDokument(dokument);
        return this;
    }

    public void setDokument(String dokument) {
        this.dokument = dokument;
    }

    public String getSmer() {
        return this.smer;
    }

    public PrometMagacinskeKartice smer(String smer) {
        this.setSmer(smer);
        return this;
    }

    public void setSmer(String smer) {
        this.smer = smer;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PrometMagacinskeKartice)) {
            return false;
        }
        return id != null && id.equals(((PrometMagacinskeKartice) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PrometMagacinskeKartice{" +
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
