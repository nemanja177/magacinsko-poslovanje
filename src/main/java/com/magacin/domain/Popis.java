package com.magacin.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A Popis.
 */
@Entity
@Table(name = "popis")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Popis implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "datum_popisa")
    private LocalDate datumPopisa;

    @Column(name = "broj_popisa")
    private Long brojPopisa;

    @Column(name = "status_popisa")
    private Boolean statusPopisa;

    @ManyToOne
    @JoinColumn(name = "magacinId", referencedColumnName = "id", nullable = false)
    private Magacin magacin;

    @ManyToOne
    @JoinColumn(name = "poslovnaGodinaId", referencedColumnName = "id", nullable = false)
    private PoslovanGodina poslovanGodina;

    @OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "popis")
    private Set<StavkaPopisa> stavkaPopisa = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    
    

    public Long getId() {
        return this.id;
    }

    public Popis() {
		super();
	}

	public Popis(LocalDate datumPopisa, Long brojPopisa, Boolean statusPopisa, Magacin magacin,
			PoslovanGodina poslovanGodina, Set<StavkaPopisa> stavkaPopisa) {
		super();
		this.datumPopisa = datumPopisa;
		this.brojPopisa = brojPopisa;
		this.statusPopisa = statusPopisa;
		this.magacin = magacin;
		this.poslovanGodina = poslovanGodina;
		this.stavkaPopisa = stavkaPopisa;
	}

	public Magacin getMagacin() {
		return magacin;
	}

	public void setMagacin(Magacin magacin) {
		this.magacin = magacin;
	}

	public PoslovanGodina getPoslovanGodina() {
		return poslovanGodina;
	}

	public void setPoslovanGodina(PoslovanGodina poslovanGodina) {
		this.poslovanGodina = poslovanGodina;
	}

	public Set<StavkaPopisa> getStavkaPopisa() {
		return stavkaPopisa;
	}

	public void setStavkaPopisa(Set<StavkaPopisa> stavkaPopisa) {
		this.stavkaPopisa = stavkaPopisa;
	}

	public Popis id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatumPopisa() {
        return this.datumPopisa;
    }

    public Popis datumPopisa(LocalDate datumPopisa) {
        this.setDatumPopisa(datumPopisa);
        return this;
    }

    public void setDatumPopisa(LocalDate datumPopisa) {
        this.datumPopisa = datumPopisa;
    }

    public Long getBrojPopisa() {
        return this.brojPopisa;
    }

    public Popis brojPopisa(Long brojPopisa) {
        this.setBrojPopisa(brojPopisa);
        return this;
    }

    public void setBrojPopisa(Long brojPopisa) {
        this.brojPopisa = brojPopisa;
    }

    public Boolean getStatusPopisa() {
        return this.statusPopisa;
    }

    public Popis statusPopisa(Boolean statusPopisa) {
        this.setStatusPopisa(statusPopisa);
        return this;
    }

    public void setStatusPopisa(Boolean statusPopisa) {
        this.statusPopisa = statusPopisa;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Popis)) {
            return false;
        }
        return id != null && id.equals(((Popis) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Popis{" +
            "id=" + getId() +
            ", datumPopisa='" + getDatumPopisa() + "'" +
            ", brojPopisa=" + getBrojPopisa() +
            ", statusPopisa='" + getStatusPopisa() + "'" +
            "}";
    }
}
