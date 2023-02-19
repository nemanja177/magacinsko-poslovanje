package com.magacin.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A JedinicaMere.
 */
@Entity
@Table(name = "jedinica_mere")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class JedinicaMere implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "naziv_jedinice")
    private String nazivJedinice;

    @Column(name = "skraceni_naziv")
    private String skraceniNaziv;

    @ManyToOne
    @JoinColumn(name = "artikalId", referencedColumnName = "id", nullable = false)
    private Artikal artikal;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    
    
    
    public Long getId() {
        return this.id;
    }

    public JedinicaMere() {
		super();
	}

	public JedinicaMere(String nazivJedinice, String skraceniNaziv, Artikal artikal) {
		super();
		this.nazivJedinice = nazivJedinice;
		this.skraceniNaziv = skraceniNaziv;
		this.artikal = artikal;
	}

	public Artikal getArtikal() {
		return artikal;
	}

	public void setArtikal(Artikal artikal) {
		this.artikal = artikal;
	}

	public JedinicaMere id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNazivJedinice() {
        return this.nazivJedinice;
    }

    public JedinicaMere nazivJedinice(String nazivJedinice) {
        this.setNazivJedinice(nazivJedinice);
        return this;
    }

    public void setNazivJedinice(String nazivJedinice) {
        this.nazivJedinice = nazivJedinice;
    }

    public String getSkraceniNaziv() {
        return this.skraceniNaziv;
    }

    public JedinicaMere skraceniNaziv(String skraceniNaziv) {
        this.setSkraceniNaziv(skraceniNaziv);
        return this;
    }

    public void setSkraceniNaziv(String skraceniNaziv) {
        this.skraceniNaziv = skraceniNaziv;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JedinicaMere)) {
            return false;
        }
        return id != null && id.equals(((JedinicaMere) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JedinicaMere{" +
            "id=" + getId() +
            ", nazivJedinice='" + getNazivJedinice() + "'" +
            ", skraceniNaziv='" + getSkraceniNaziv() + "'" +
            "}";
    }
}
