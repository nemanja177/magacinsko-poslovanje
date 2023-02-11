package com.magacin.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A PrometniDokument.
 */
@Entity
@Table(name = "prometni_dokument")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PrometniDokument implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "broj_dokumenata")
    private Long brojDokumenata;

    @Column(name = "datum")
    private LocalDate datum;

    @Column(name = "vrsta")
    private String vrsta;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "magacinId", referencedColumnName = "id", nullable = false)
    private Magacin magacin;

    @ManyToOne
    @JoinColumn(name = "partnerId", referencedColumnName = "id", nullable = false)
    private PoslovniPartner partner;

    @ManyToOne
    @JoinColumn(name = "poslovnaGodinaId", referencedColumnName = "id", nullable = false)
    private PoslovanGodina poslovnaGodina;

    @OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "prometni_dokument")
    private Set<StavkaPrometnogDokumenta> stavkaDokumenta = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PrometniDokument id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBrojDokumenata() {
        return this.brojDokumenata;
    }

    public PrometniDokument brojDokumenata(Long brojDokumenata) {
        this.setBrojDokumenata(brojDokumenata);
        return this;
    }

    public void setBrojDokumenata(Long brojDokumenata) {
        this.brojDokumenata = brojDokumenata;
    }

    public LocalDate getDatum() {
        return this.datum;
    }

    public PrometniDokument datum(LocalDate datum) {
        this.setDatum(datum);
        return this;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public String getVrsta() {
        return this.vrsta;
    }

    public PrometniDokument vrsta(String vrsta) {
        this.setVrsta(vrsta);
        return this;
    }

    public void setVrsta(String vrsta) {
        this.vrsta = vrsta;
    }

    public String getStatus() {
        return this.status;
    }

    public PrometniDokument status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PrometniDokument)) {
            return false;
        }
        return id != null && id.equals(((PrometniDokument) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PrometniDokument{" +
            "id=" + getId() +
            ", brojDokumenata=" + getBrojDokumenata() +
            ", datum='" + getDatum() + "'" +
            ", vrsta='" + getVrsta() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
