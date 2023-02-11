package com.magacin.web.rest;

import com.magacin.domain.Artikal;
import com.magacin.repository.ArtikalRepository;
import com.magacin.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.magacin.domain.Artikal}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ArtikalResource {

    private final Logger log = LoggerFactory.getLogger(ArtikalResource.class);

    private static final String ENTITY_NAME = "artikal";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ArtikalRepository artikalRepository;

    public ArtikalResource(ArtikalRepository artikalRepository) {
        this.artikalRepository = artikalRepository;
    }

    /**
     * {@code POST  /artikals} : Create a new artikal.
     *
     * @param artikal the artikal to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new artikal, or with status {@code 400 (Bad Request)} if the artikal has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/artikals")
    public ResponseEntity<Artikal> createArtikal(@RequestBody Artikal artikal) throws URISyntaxException {
        log.debug("REST request to save Artikal : {}", artikal);
        if (artikal.getId() != null) {
            throw new BadRequestAlertException("A new artikal cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Artikal result = artikalRepository.save(artikal);
        return ResponseEntity
            .created(new URI("/api/artikals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /artikals/:id} : Updates an existing artikal.
     *
     * @param id the id of the artikal to save.
     * @param artikal the artikal to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated artikal,
     * or with status {@code 400 (Bad Request)} if the artikal is not valid,
     * or with status {@code 500 (Internal Server Error)} if the artikal couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/artikals/{id}")
    public ResponseEntity<Artikal> updateArtikal(@PathVariable(value = "id", required = false) final Long id, @RequestBody Artikal artikal)
        throws URISyntaxException {
        log.debug("REST request to update Artikal : {}, {}", id, artikal);
        if (artikal.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, artikal.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!artikalRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Artikal result = artikalRepository.save(artikal);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, artikal.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /artikals/:id} : Partial updates given fields of an existing artikal, field will ignore if it is null
     *
     * @param id the id of the artikal to save.
     * @param artikal the artikal to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated artikal,
     * or with status {@code 400 (Bad Request)} if the artikal is not valid,
     * or with status {@code 404 (Not Found)} if the artikal is not found,
     * or with status {@code 500 (Internal Server Error)} if the artikal couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/artikals/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Artikal> partialUpdateArtikal(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Artikal artikal
    ) throws URISyntaxException {
        log.debug("REST request to partial update Artikal partially : {}, {}", id, artikal);
        if (artikal.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, artikal.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!artikalRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Artikal> result = artikalRepository
            .findById(artikal.getId())
            .map(existingArtikal -> {
                if (artikal.getNaziv() != null) {
                    existingArtikal.setNaziv(artikal.getNaziv());
                }
                if (artikal.getOpis() != null) {
                    existingArtikal.setOpis(artikal.getOpis());
                }
                if (artikal.getPakovanje() != null) {
                    existingArtikal.setPakovanje(artikal.getPakovanje());
                }

                return existingArtikal;
            })
            .map(artikalRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, artikal.getId().toString())
        );
    }

    /**
     * {@code GET  /artikals} : get all the artikals.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of artikals in body.
     */
    @GetMapping("/artikals")
    public List<Artikal> getAllArtikals() {
        log.debug("REST request to get all Artikals");
        return artikalRepository.findAll();
    }

    /**
     * {@code GET  /artikals/:id} : get the "id" artikal.
     *
     * @param id the id of the artikal to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the artikal, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/artikals/{id}")
    public ResponseEntity<Artikal> getArtikal(@PathVariable Long id) {
        log.debug("REST request to get Artikal : {}", id);
        Optional<Artikal> artikal = artikalRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(artikal);
    }

    /**
     * {@code DELETE  /artikals/:id} : delete the "id" artikal.
     *
     * @param id the id of the artikal to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/artikals/{id}")
    public ResponseEntity<Void> deleteArtikal(@PathVariable Long id) {
        log.debug("REST request to delete Artikal : {}", id);
        artikalRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
