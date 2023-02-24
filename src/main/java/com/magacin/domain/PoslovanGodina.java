package com.magacin.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A PoslovanGodina.
 */
@Entity
@Table(name = "poslovan_godina")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PoslovanGodina implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "godina")
    private Long godina;

    @Column(name = "zakljucena")
    private Boolean zakljucena;

    @OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "poslovanGodina")
    private Set<Popis> popis = new HashSet<>();

    @OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "poslovnaGodina")
    private Set<MagacinskaKartica> magacinskaKartica = new HashSet<>();

    @OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "poslovnaGodina")
    private Set<PrometniDokument> prometniDokument = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "preduzevceId", referencedColumnName = "id", nullable = false)
    private Preduzece preduzece;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    
    

    public Long getId() {
        return this.id;
    }

    public PoslovanGodina() {
		super();
	}

	public PoslovanGodina(Long godina, Boolean zakljucena, Set<Popis> popis, Set<MagacinskaKartica> magacinskaKartica,
			Set<PrometniDokument> prometniDokument, Preduzece preduzece) {
		super();
		this.godina = godina;
		this.zakljucena = zakljucena;
		this.popis = popis;
		this.magacinskaKartica = magacinskaKartica;
		this.prometniDokument = prometniDokument;
		this.preduzece = preduzece;
	}

	public Set<Popis> getPopis() {
		return popis;
	}

	public void setPopis(Set<Popis> popis) {
		this.popis = popis;
	}

	public Set<MagacinskaKartica> getMagacinskaKartica() {
		return magacinskaKartica;
	}

	public void setMagacinskaKartica(Set<MagacinskaKartica> magacinskaKartica) {
		this.magacinskaKartica = magacinskaKartica;
	}

	public Set<PrometniDokument> getPrometniDokument() {
		return prometniDokument;
	}

	public void setPrometniDokument(Set<PrometniDokument> prometniDokument) {
		this.prometniDokument = prometniDokument;
	}

	public Preduzece getPreduzece() {
		return preduzece;
	}

	public void setPreduzece(Preduzece preduzece) {
		this.preduzece = preduzece;
	}

	public PoslovanGodina id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGodina() {
        return this.godina;
    }

    public PoslovanGodina godina(Long godina) {
        this.setGodina(godina);
        return this;
    }

    public void setGodina(Long godina) {
        this.godina = godina;
    }

    public Boolean getZakljucena() {
        return this.zakljucena;
    }

    public PoslovanGodina zakljucena(Boolean zakljucena) {
        this.setZakljucena(zakljucena);
        return this;
    }

    public void setZakljucena(Boolean zakljucena) {
        this.zakljucena = zakljucena;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PoslovanGodina)) {
            return false;
        }
        return id != null && id.equals(((PoslovanGodina) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PoslovanGodina{" +
            "id=" + getId() +
            ", godina=" + getGodina() +
            ", zakljucena='" + getZakljucena() + "'" +
            "}";
    }
}
