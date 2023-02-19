package com.magacin.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A StavkaPopisa.
 */
@Entity
@Table(name = "stavka_popisa")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class StavkaPopisa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "kolicina_popisa")
    private Long kolicinaPopisa;

    @Column(name = "kolicina_po_knjigama")
    private Long kolicinaPoKnjigama;

    @OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "stavka_popisa")
    private Set<Artikal> artikal = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "prometniDokumentId", referencedColumnName = "id", nullable = false)
    private PrometniDokument prometniDokument;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    
    

    public Long getId() {
        return this.id;
    }

    public Set<Artikal> getArtikal() {
		return artikal;
	}

	public void setArtikal(Set<Artikal> artikal) {
		this.artikal = artikal;
	}

	public PrometniDokument getPrometniDokument() {
		return prometniDokument;
	}

	public void setPrometniDokument(PrometniDokument prometniDokument) {
		this.prometniDokument = prometniDokument;
	}

	public StavkaPopisa() {
		super();
	}

	public StavkaPopisa(Long kolicinaPopisa, Long kolicinaPoKnjigama, Set<Artikal> artikal,
			PrometniDokument prometniDokument) {
		super();
		this.kolicinaPopisa = kolicinaPopisa;
		this.kolicinaPoKnjigama = kolicinaPoKnjigama;
		this.artikal = artikal;
		this.prometniDokument = prometniDokument;
	}

	public StavkaPopisa id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getKolicinaPopisa() {
        return this.kolicinaPopisa;
    }

    public StavkaPopisa kolicinaPopisa(Long kolicinaPopisa) {
        this.setKolicinaPopisa(kolicinaPopisa);
        return this;
    }

    public void setKolicinaPopisa(Long kolicinaPopisa) {
        this.kolicinaPopisa = kolicinaPopisa;
    }

    public Long getKolicinaPoKnjigama() {
        return this.kolicinaPoKnjigama;
    }

    public StavkaPopisa kolicinaPoKnjigama(Long kolicinaPoKnjigama) {
        this.setKolicinaPoKnjigama(kolicinaPoKnjigama);
        return this;
    }

    public void setKolicinaPoKnjigama(Long kolicinaPoKnjigama) {
        this.kolicinaPoKnjigama = kolicinaPoKnjigama;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StavkaPopisa)) {
            return false;
        }
        return id != null && id.equals(((StavkaPopisa) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StavkaPopisa{" +
            "id=" + getId() +
            ", kolicinaPopisa=" + getKolicinaPopisa() +
            ", kolicinaPoKnjigama=" + getKolicinaPoKnjigama() +
            "}";
    }
}
