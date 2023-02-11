package com.magacin.web.rest;

import com.magacin.domain.Radnik;
import com.magacin.repository.RadnikRepository;
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
 * REST controller for managing {@link com.magacin.domain.Radnik}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RadnikResource {

    private final Logger log = LoggerFactory.getLogger(RadnikResource.class);

    private static final String ENTITY_NAME = "radnik";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RadnikRepository radnikRepository;

    public RadnikResource(RadnikRepository radnikRepository) {
        this.radnikRepository = radnikRepository;
    }

    /**
     * {@code POST  /radniks} : Create a new radnik.
     *
     * @param radnik the radnik to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new radnik, or with status {@code 400 (Bad Request)} if the radnik has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/radniks")
    public ResponseEntity<Radnik> createRadnik(@RequestBody Radnik radnik) throws URISyntaxException {
        log.debug("REST request to save Radnik : {}", radnik);
        if (radnik.getId() != null) {
            throw new BadRequestAlertException("A new radnik cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Radnik result = radnikRepository.save(radnik);
        return ResponseEntity
            .created(new URI("/api/radniks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /radniks/:id} : Updates an existing radnik.
     *
     * @param id the id of the radnik to save.
     * @param radnik the radnik to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated radnik,
     * or with status {@code 400 (Bad Request)} if the radnik is not valid,
     * or with status {@code 500 (Internal Server Error)} if the radnik couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/radniks/{id}")
    public ResponseEntity<Radnik> updateRadnik(@PathVariable(value = "id", required = false) final Long id, @RequestBody Radnik radnik)
        throws URISyntaxException {
        log.debug("REST request to update Radnik : {}, {}", id, radnik);
        if (radnik.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, radnik.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!radnikRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Radnik result = radnikRepository.save(radnik);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, radnik.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /radniks/:id} : Partial updates given fields of an existing radnik, field will ignore if it is null
     *
     * @param id the id of the radnik to save.
     * @param radnik the radnik to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated radnik,
     * or with status {@code 400 (Bad Request)} if the radnik is not valid,
     * or with status {@code 404 (Not Found)} if the radnik is not found,
     * or with status {@code 500 (Internal Server Error)} if the radnik couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/radniks/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Radnik> partialUpdateRadnik(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Radnik radnik
    ) throws URISyntaxException {
        log.debug("REST request to partial update Radnik partially : {}, {}", id, radnik);
        if (radnik.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, radnik.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!radnikRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Radnik> result = radnikRepository
            .findById(radnik.getId())
            .map(existingRadnik -> {
                if (radnik.getIme() != null) {
                    existingRadnik.setIme(radnik.getIme());
                }
                if (radnik.getPrezime() != null) {
                    existingRadnik.setPrezime(radnik.getPrezime());
                }
                if (radnik.getAdresa() != null) {
                    existingRadnik.setAdresa(radnik.getAdresa());
                }
                if (radnik.getTelefon() != null) {
                    existingRadnik.setTelefon(radnik.getTelefon());
                }

                return existingRadnik;
            })
            .map(radnikRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, radnik.getId().toString())
        );
    }

    /**
     * {@code GET  /radniks} : get all the radniks.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of radniks in body.
     */
    @GetMapping("/radniks")
    public List<Radnik> getAllRadniks() {
        log.debug("REST request to get all Radniks");
        return radnikRepository.findAll();
    }

    /**
     * {@code GET  /radniks/:id} : get the "id" radnik.
     *
     * @param id the id of the radnik to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the radnik, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/radniks/{id}")
    public ResponseEntity<Radnik> getRadnik(@PathVariable Long id) {
        log.debug("REST request to get Radnik : {}", id);
        Optional<Radnik> radnik = radnikRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(radnik);
    }

    /**
     * {@code DELETE  /radniks/:id} : delete the "id" radnik.
     *
     * @param id the id of the radnik to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/radniks/{id}")
    public ResponseEntity<Void> deleteRadnik(@PathVariable Long id) {
        log.debug("REST request to delete Radnik : {}", id);
        radnikRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
