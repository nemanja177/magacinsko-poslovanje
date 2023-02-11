package com.magacin.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A Artikal.
 */
@Entity
@Table(name = "artikal")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Artikal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "naziv")
    private String naziv;

    @Column(name = "opis")
    private String opis;

    @Column(name = "pakovanje")
    private String pakovanje;

    @ManyToOne
    @JoinColumn(name = "magacinksaKarticaId", referencedColumnName = "id", nullable = false)
    private MagacinskaKartica magacinskaKarticaId;

    @ManyToOne
    @JoinColumn(name = "stavakaDokumentaId", referencedColumnName = "id", nullable = false)
    private StavkaPrometnogDokumenta stavakaDokumenta;

    @ManyToOne
    @JoinColumn(name = "stavkaPropisaId", referencedColumnName = "id", nullable = false)
    private StavkaPopisa stavkaPropisa;

    @OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "artikal")
    private Set<JedinicaMere> jedinicaMere = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Artikal id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaziv() {
        return this.naziv;
    }

    public Artikal naziv(String naziv) {
        this.setNaziv(naziv);
        return this;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getOpis() {
        return this.opis;
    }

    public Artikal opis(String opis) {
        this.setOpis(opis);
        return this;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getPakovanje() {
        return this.pakovanje;
    }

    public Artikal pakovanje(String pakovanje) {
        this.setPakovanje(pakovanje);
        return this;
    }

    public void setPakovanje(String pakovanje) {
        this.pakovanje = pakovanje;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Artikal)) {
            return false;
        }
        return id != null && id.equals(((Artikal) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Artikal{" +
            "id=" + getId() +
            ", naziv='" + getNaziv() + "'" +
            ", opis='" + getOpis() + "'" +
            ", pakovanje='" + getPakovanje() + "'" +
            "}";
    }
}
