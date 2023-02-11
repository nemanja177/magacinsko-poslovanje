package com.magacin.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A Magacin.
 */
@Entity
@Table(name = "magacin")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Magacin implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "naziv")
    private String naziv;

    @ManyToOne
    @JoinColumn(name = "preduzeceId", referencedColumnName = "id", nullable = false)
    private Preduzece preduzece;

    @OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "magacin")
    private Set<Radnik> radnici = new HashSet<>();

    @OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "magacin")
    private Set<Popis> popis = new HashSet<>();

    @OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "magacin")
    private Set<MagacinskaKartica> magacinskaKartica = new HashSet<>();

    @OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "magacin")
    private Set<PrometniDokument> prometniDokument = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Magacin id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaziv() {
        return this.naziv;
    }

    public Magacin naziv(String naziv) {
        this.setNaziv(naziv);
        return this;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Magacin)) {
            return false;
        }
        return id != null && id.equals(((Magacin) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Magacin{" +
            "id=" + getId() +
            ", naziv='" + getNaziv() + "'" +
            "}";
    }
}
