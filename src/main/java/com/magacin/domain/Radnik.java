package com.magacin.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A Radnik.
 */
@Entity
@Table(name = "radnik")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Radnik implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "ime")
    private String ime;

    @Column(name = "prezime")
    private String prezime;

    @Column(name = "adresa")
    private String adresa;

    @Column(name = "telefon")
    private String telefon;

    @ManyToOne
    @JoinColumn(name = "preduzeceId", referencedColumnName = "id", nullable = false)
    private Preduzece preduzece;

    @ManyToOne
    @JoinColumn(name = "magacinId", referencedColumnName = "id", nullable = false)
    private Magacin magacin;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Radnik id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIme() {
        return this.ime;
    }

    public Radnik ime(String ime) {
        this.setIme(ime);
        return this;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return this.prezime;
    }

    public Radnik prezime(String prezime) {
        this.setPrezime(prezime);
        return this;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getAdresa() {
        return this.adresa;
    }

    public Radnik adresa(String adresa) {
        this.setAdresa(adresa);
        return this;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getTelefon() {
        return this.telefon;
    }

    public Radnik telefon(String telefon) {
        this.setTelefon(telefon);
        return this;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Radnik)) {
            return false;
        }
        return id != null && id.equals(((Radnik) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Radnik{" +
            "id=" + getId() +
            ", ime='" + getIme() + "'" +
            ", prezime='" + getPrezime() + "'" +
            ", adresa='" + getAdresa() + "'" +
            ", telefon='" + getTelefon() + "'" +
            "}";
    }
}
