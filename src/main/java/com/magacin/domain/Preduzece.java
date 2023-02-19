package com.magacin.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A Preduzece.
 */
@Entity
@Table(name = "preduzece")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Preduzece implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "naziv")
    private String naziv;

    @Column(name = "adresa")
    private String adresa;

    @Column(name = "telefon")
    private String telefon;

    @Column(name = "mib")
    private String mib;

    @Column(name = "pib")
    private String pib;

    @OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "preduzece")
    private Set<PoslovanGodina> poslovanGodine = new HashSet<>();

    @OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "preduzece")
    private Set<PoslovniPartner> poslovniPartner = new HashSet<>();

    @OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "preduzece")
    private Set<Magacin> magacini = new HashSet<>();

    @OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "preduzece")
    private Set<Radnik> radnici = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    
    

    public Long getId() {
        return this.id;
    }

    public Preduzece() {
		super();
	}

	public Preduzece(String naziv, String adresa, String telefon, String mib, String pib,
			Set<PoslovanGodina> poslovanGodine, Set<PoslovniPartner> poslovniPartner, Set<Magacin> magacini,
			Set<Radnik> radnici) {
		super();
		this.naziv = naziv;
		this.adresa = adresa;
		this.telefon = telefon;
		this.mib = mib;
		this.pib = pib;
		this.poslovanGodine = poslovanGodine;
		this.poslovniPartner = poslovniPartner;
		this.magacini = magacini;
		this.radnici = radnici;
	}

	public Set<PoslovanGodina> getPoslovanGodine() {
		return poslovanGodine;
	}

	public void setPoslovanGodine(Set<PoslovanGodina> poslovanGodine) {
		this.poslovanGodine = poslovanGodine;
	}

	public Set<PoslovniPartner> getPoslovniPartner() {
		return poslovniPartner;
	}

	public void setPoslovniPartner(Set<PoslovniPartner> poslovniPartner) {
		this.poslovniPartner = poslovniPartner;
	}

	public Set<Magacin> getMagacini() {
		return magacini;
	}

	public void setMagacini(Set<Magacin> magacini) {
		this.magacini = magacini;
	}

	public Set<Radnik> getRadnici() {
		return radnici;
	}

	public void setRadnici(Set<Radnik> radnici) {
		this.radnici = radnici;
	}

	public Preduzece id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaziv() {
        return this.naziv;
    }

    public Preduzece naziv(String naziv) {
        this.setNaziv(naziv);
        return this;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getAdresa() {
        return this.adresa;
    }

    public Preduzece adresa(String adresa) {
        this.setAdresa(adresa);
        return this;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getTelefon() {
        return this.telefon;
    }

    public Preduzece telefon(String telefon) {
        this.setTelefon(telefon);
        return this;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getMib() {
        return this.mib;
    }

    public Preduzece mib(String mib) {
        this.setMib(mib);
        return this;
    }

    public void setMib(String mib) {
        this.mib = mib;
    }

    public String getPib() {
        return this.pib;
    }

    public Preduzece pib(String pib) {
        this.setPib(pib);
        return this;
    }

    public void setPib(String pib) {
        this.pib = pib;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Preduzece)) {
            return false;
        }
        return id != null && id.equals(((Preduzece) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Preduzece{" +
            "id=" + getId() +
            ", naziv='" + getNaziv() + "'" +
            ", adresa='" + getAdresa() + "'" +
            ", telefon='" + getTelefon() + "'" +
            ", mib='" + getMib() + "'" +
            ", pib='" + getPib() + "'" +
            "}";
    }
}
