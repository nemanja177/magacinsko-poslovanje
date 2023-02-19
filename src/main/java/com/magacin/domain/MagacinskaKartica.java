package com.magacin.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A MagacinskaKartica.
 */
@Entity
@Table(name = "magacinska_kartica")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MagacinskaKartica implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "pocetno_stanje_kolicina")
    private Double pocetnoStanjeKolicina;

    @Column(name = "promet_ulaza_kolicina")
    private Double prometUlazaKolicina;

    @Column(name = "promet_izlaza_kolicina")
    private Double prometIzlazaKolicina;

    @Column(name = "ukupna_kolicina")
    private Double ukupnaKolicina;

    @Column(name = "pocetno_stanje_vrednosti")
    private Double pocetnoStanjeVrednosti;

    @Column(name = "promet_ulaza_vrednosti")
    private Double prometUlazaVrednosti;

    @Column(name = "promet_izlaza_vrednosti")
    private Double prometIzlazaVrednosti;

    @Column(name = "ukupna_vrednost")
    private Double ukupnaVrednost;

    @ManyToOne
    @JoinColumn(name = "magacinId", referencedColumnName = "id", nullable = false)
    private Magacin magacin;

    @ManyToOne
    @JoinColumn(name = "poslovnaGodinaId", referencedColumnName = "id", nullable = false)
    private PoslovanGodina poslovnaGodina;

    @OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "magacinska_kartica")
    private Set<Artikal> artikal = new HashSet<>();

    @OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "magacinska_kartica")
    private Set<PrometMagacinskeKartice> prometMagacinskeKaritce = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    
    

    public Long getId() {
        return this.id;
    }

    public MagacinskaKartica() {
		super();
	}

	public MagacinskaKartica(Double pocetnoStanjeKolicina, Double prometUlazaKolicina, Double prometIzlazaKolicina,
			Double ukupnaKolicina, Double pocetnoStanjeVrednosti, Double prometUlazaVrednosti,
			Double prometIzlazaVrednosti, Double ukupnaVrednost, Magacin magacin, PoslovanGodina poslovnaGodina,
			Set<Artikal> artikal, Set<PrometMagacinskeKartice> prometMagacinskeKaritce) {
		super();
		this.pocetnoStanjeKolicina = pocetnoStanjeKolicina;
		this.prometUlazaKolicina = prometUlazaKolicina;
		this.prometIzlazaKolicina = prometIzlazaKolicina;
		this.ukupnaKolicina = ukupnaKolicina;
		this.pocetnoStanjeVrednosti = pocetnoStanjeVrednosti;
		this.prometUlazaVrednosti = prometUlazaVrednosti;
		this.prometIzlazaVrednosti = prometIzlazaVrednosti;
		this.ukupnaVrednost = ukupnaVrednost;
		this.magacin = magacin;
		this.poslovnaGodina = poslovnaGodina;
		this.artikal = artikal;
		this.prometMagacinskeKaritce = prometMagacinskeKaritce;
	}

	public Magacin getMagacin() {
		return magacin;
	}

	public void setMagacin(Magacin magacin) {
		this.magacin = magacin;
	}

	public PoslovanGodina getPoslovnaGodina() {
		return poslovnaGodina;
	}

	public void setPoslovnaGodina(PoslovanGodina poslovnaGodina) {
		this.poslovnaGodina = poslovnaGodina;
	}

	public Set<Artikal> getArtikal() {
		return artikal;
	}

	public void setArtikal(Set<Artikal> artikal) {
		this.artikal = artikal;
	}

	public Set<PrometMagacinskeKartice> getPrometMagacinskeKaritce() {
		return prometMagacinskeKaritce;
	}

	public void setPrometMagacinskeKaritce(Set<PrometMagacinskeKartice> prometMagacinskeKaritce) {
		this.prometMagacinskeKaritce = prometMagacinskeKaritce;
	}

	public MagacinskaKartica id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPocetnoStanjeKolicina() {
        return this.pocetnoStanjeKolicina;
    }

    public MagacinskaKartica pocetnoStanjeKolicina(Double pocetnoStanjeKolicina) {
        this.setPocetnoStanjeKolicina(pocetnoStanjeKolicina);
        return this;
    }

    public void setPocetnoStanjeKolicina(Double pocetnoStanjeKolicina) {
        this.pocetnoStanjeKolicina = pocetnoStanjeKolicina;
    }

    public Double getPrometUlazaKolicina() {
        return this.prometUlazaKolicina;
    }

    public MagacinskaKartica prometUlazaKolicina(Double prometUlazaKolicina) {
        this.setPrometUlazaKolicina(prometUlazaKolicina);
        return this;
    }

    public void setPrometUlazaKolicina(Double prometUlazaKolicina) {
        this.prometUlazaKolicina = prometUlazaKolicina;
    }

    public Double getPrometIzlazaKolicina() {
        return this.prometIzlazaKolicina;
    }

    public MagacinskaKartica prometIzlazaKolicina(Double prometIzlazaKolicina) {
        this.setPrometIzlazaKolicina(prometIzlazaKolicina);
        return this;
    }

    public void setPrometIzlazaKolicina(Double prometIzlazaKolicina) {
        this.prometIzlazaKolicina = prometIzlazaKolicina;
    }

    public Double getUkupnaKolicina() {
        return this.ukupnaKolicina;
    }

    public MagacinskaKartica ukupnaKolicina(Double ukupnaKolicina) {
        this.setUkupnaKolicina(ukupnaKolicina);
        return this;
    }

    public void setUkupnaKolicina(Double ukupnaKolicina) {
        this.ukupnaKolicina = ukupnaKolicina;
    }

    public Double getPocetnoStanjeVrednosti() {
        return this.pocetnoStanjeVrednosti;
    }

    public MagacinskaKartica pocetnoStanjeVrednosti(Double pocetnoStanjeVrednosti) {
        this.setPocetnoStanjeVrednosti(pocetnoStanjeVrednosti);
        return this;
    }

    public void setPocetnoStanjeVrednosti(Double pocetnoStanjeVrednosti) {
        this.pocetnoStanjeVrednosti = pocetnoStanjeVrednosti;
    }

    public Double getPrometUlazaVrednosti() {
        return this.prometUlazaVrednosti;
    }

    public MagacinskaKartica prometUlazaVrednosti(Double prometUlazaVrednosti) {
        this.setPrometUlazaVrednosti(prometUlazaVrednosti);
        return this;
    }

    public void setPrometUlazaVrednosti(Double prometUlazaVrednosti) {
        this.prometUlazaVrednosti = prometUlazaVrednosti;
    }

    public Double getPrometIzlazaVrednosti() {
        return this.prometIzlazaVrednosti;
    }

    public MagacinskaKartica prometIzlazaVrednosti(Double prometIzlazaVrednosti) {
        this.setPrometIzlazaVrednosti(prometIzlazaVrednosti);
        return this;
    }

    public void setPrometIzlazaVrednosti(Double prometIzlazaVrednosti) {
        this.prometIzlazaVrednosti = prometIzlazaVrednosti;
    }

    public Double getUkupnaVrednost() {
        return this.ukupnaVrednost;
    }

    public MagacinskaKartica ukupnaVrednost(Double ukupnaVrednost) {
        this.setUkupnaVrednost(ukupnaVrednost);
        return this;
    }

    public void setUkupnaVrednost(Double ukupnaVrednost) {
        this.ukupnaVrednost = ukupnaVrednost;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and
    // setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MagacinskaKartica)) {
            return false;
        }
        return id != null && id.equals(((MagacinskaKartica) o).id);
    }

    @Override
    public int hashCode() {
        // see
        // https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
	@Override
	public String toString() {
		return "MagacinskaKartica{" + "id=" + getId() + ", pocetnoStanjeKolicina=" + getPocetnoStanjeKolicina()
				+ ", prometUlazaKolicina=" + getPrometUlazaKolicina() + ", prometIzlazaKolicina="
				+ getPrometIzlazaKolicina() + ", ukupnaKolicina=" + getUkupnaKolicina() + ", pocetnoStanjeVrednosti="
				+ getPocetnoStanjeVrednosti() + ", prometUlazaVrednosti=" + getPrometUlazaVrednosti()
				+ ", prometIzlazaVrednosti=" + getPrometIzlazaVrednosti() + ", ukupnaVrednost=" + getUkupnaVrednost()
				+ "}";
	}
}
