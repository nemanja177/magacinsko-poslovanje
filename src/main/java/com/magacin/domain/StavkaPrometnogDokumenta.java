package com.magacin.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A StavkaPrometnogDokumenta.
 */
@Entity
@Table(name = "stavka_prometnog_dokumenta")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class StavkaPrometnogDokumenta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "kolicina")
    private Long kolicina;

    @Column(name = "cena")
    private Long cena;

    @Column(name = "vrednost")
    private Long vrednost;

    @OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "stavakaDokumenta")
    private Set<Artikal> artikal = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "popisId", referencedColumnName = "id", nullable = false)
    private Popis popis;
    
    @ManyToOne
    @JoinColumn(name = "prometniDokument", referencedColumnName = "id", nullable = false)
    private PrometniDokument prometniDokument;

    public StavkaPrometnogDokumenta() {
		super();
	}

	public StavkaPrometnogDokumenta(Long kolicina, Long cena, Long vrednost, Set<Artikal> artikal, Popis popis,
			PrometniDokument prometniDokument) {
		super();
		this.kolicina = kolicina;
		this.cena = cena;
		this.vrednost = vrednost;
		this.artikal = artikal;
		this.popis = popis;
		this.prometniDokument = prometniDokument;
	}

	// jhipster-needle-entity-add-field - JHipster will add fields here
	
	

    public Set<Artikal> getArtikal() {
		return artikal;
	}

	public PrometniDokument getPrometniDokument() {
		return prometniDokument;
	}

	public void setPrometniDokument(PrometniDokument prometniDokument) {
		this.prometniDokument = prometniDokument;
	}

	public void setArtikal(Set<Artikal> artikal) {
		this.artikal = artikal;
	}

	public Popis getPopis() {
		return popis;
	}

	public void setPopis(Popis popis) {
		this.popis = popis;
	}

	public Long getId() {
        return this.id;
    }

    public StavkaPrometnogDokumenta id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getKolicina() {
        return this.kolicina;
    }

    public StavkaPrometnogDokumenta kolicina(Long kolicina) {
        this.setKolicina(kolicina);
        return this;
    }

    public void setKolicina(Long kolicina) {
        this.kolicina = kolicina;
    }

    public Long getCena() {
        return this.cena;
    }

    public StavkaPrometnogDokumenta cena(Long cena) {
        this.setCena(cena);
        return this;
    }

    public void setCena(Long cena) {
        this.cena = cena;
    }

    public Long getVrednost() {
        return this.vrednost;
    }

    public StavkaPrometnogDokumenta vrednost(Long vrednost) {
        this.setVrednost(vrednost);
        return this;
    }

    public void setVrednost(Long vrednost) {
        this.vrednost = vrednost;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StavkaPrometnogDokumenta)) {
            return false;
        }
        return id != null && id.equals(((StavkaPrometnogDokumenta) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StavkaPrometnogDokumenta{" +
            "id=" + getId() +
            ", kolicina=" + getKolicina() +
            ", cena=" + getCena() +
            ", vrednost=" + getVrednost() +
            "}";
    }
}
