package com.magacin.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A PoslovniPartner.
 */
@Entity
@Table(name = "poslovni_partner")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PoslovniPartner implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "ime")
    private String ime;

    @Column(name = "prezime")
    private String prezime;

    @Column(name = "email")
    private String email;

    @Column(name = "jmbg")
    private String jmbg;

    @Column(name = "adresa")
    private String adresa;

    @ManyToOne
    @JoinColumn(name = "prometniDokumentId", referencedColumnName = "id", nullable = false)
    private PrometniDokument prometniDokument;

    @ManyToOne
    @JoinColumn(name = "preduzeceId", referencedColumnName = "id", nullable = false)
    private Preduzece preduzece;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PoslovniPartner id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIme() {
        return this.ime;
    }

    public PoslovniPartner ime(String ime) {
        this.setIme(ime);
        return this;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return this.prezime;
    }

    public PoslovniPartner prezime(String prezime) {
        this.setPrezime(prezime);
        return this;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getEmail() {
        return this.email;
    }

    public PoslovniPartner email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJmbg() {
        return this.jmbg;
    }

    public PoslovniPartner jmbg(String jmbg) {
        this.setJmbg(jmbg);
        return this;
    }

    public void setJmbg(String jmbg) {
        this.jmbg = jmbg;
    }

    public String getAdresa() {
        return this.adresa;
    }

    public PoslovniPartner adresa(String adresa) {
        this.setAdresa(adresa);
        return this;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PoslovniPartner)) {
            return false;
        }
        return id != null && id.equals(((PoslovniPartner) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PoslovniPartner{" +
            "id=" + getId() +
            ", ime='" + getIme() + "'" +
            ", prezime='" + getPrezime() + "'" +
            ", email='" + getEmail() + "'" +
            ", jmbg='" + getJmbg() + "'" +
            ", adresa='" + getAdresa() + "'" +
            "}";
    }
}
