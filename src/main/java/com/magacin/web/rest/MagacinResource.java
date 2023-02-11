package com.magacin.web.rest;

import com.magacin.domain.Magacin;
import com.magacin.repository.MagacinRepository;
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
 * REST controller for managing {@link com.magacin.domain.Magacin}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class MagacinResource {

    private final Logger log = LoggerFactory.getLogger(MagacinResource.class);

    private static final String ENTITY_NAME = "magacin";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MagacinRepository magacinRepository;

    public MagacinResource(MagacinRepository magacinRepository) {
        this.magacinRepository = magacinRepository;
    }

    /**
     * {@code POST  /magacins} : Create a new magacin.
     *
     * @param magacin the magacin to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new magacin, or with status {@code 400 (Bad Request)} if the magacin has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/magacins")
    public ResponseEntity<Magacin> createMagacin(@RequestBody Magacin magacin) throws URISyntaxException {
        log.debug("REST request to save Magacin : {}", magacin);
        if (magacin.getId() != null) {
            throw new BadRequestAlertException("A new magacin cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Magacin result = magacinRepository.save(magacin);
        return ResponseEntity
            .created(new URI("/api/magacins/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /magacins/:id} : Updates an existing magacin.
     *
     * @param id the id of the magacin to save.
     * @param magacin the magacin to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated magacin,
     * or with status {@code 400 (Bad Request)} if the magacin is not valid,
     * or with status {@code 500 (Internal Server Error)} if the magacin couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/magacins/{id}")
    public ResponseEntity<Magacin> updateMagacin(@PathVariable(value = "id", required = false) final Long id, @RequestBody Magacin magacin)
        throws URISyntaxException {
        log.debug("REST request to update Magacin : {}, {}", id, magacin);
        if (magacin.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, magacin.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!magacinRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Magacin result = magacinRepository.save(magacin);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, magacin.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /magacins/:id} : Partial updates given fields of an existing magacin, field will ignore if it is null
     *
     * @param id the id of the magacin to save.
     * @param magacin the magacin to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated magacin,
     * or with status {@code 400 (Bad Request)} if the magacin is not valid,
     * or with status {@code 404 (Not Found)} if the magacin is not found,
     * or with status {@code 500 (Internal Server Error)} if the magacin couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/magacins/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Magacin> partialUpdateMagacin(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Magacin magacin
    ) throws URISyntaxException {
        log.debug("REST request to partial update Magacin partially : {}, {}", id, magacin);
        if (magacin.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, magacin.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!magacinRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Magacin> result = magacinRepository
            .findById(magacin.getId())
            .map(existingMagacin -> {
                if (magacin.getNaziv() != null) {
                    existingMagacin.setNaziv(magacin.getNaziv());
                }

                return existingMagacin;
            })
            .map(magacinRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, magacin.getId().toString())
        );
    }

    /**
     * {@code GET  /magacins} : get all the magacins.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of magacins in body.
     */
    @GetMapping("/magacins")
    public List<Magacin> getAllMagacins() {
        log.debug("REST request to get all Magacins");
        return magacinRepository.findAll();
    }

    /**
     * {@code GET  /magacins/:id} : get the "id" magacin.
     *
     * @param id the id of the magacin to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the magacin, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/magacins/{id}")
    public ResponseEntity<Magacin> getMagacin(@PathVariable Long id) {
        log.debug("REST request to get Magacin : {}", id);
        Optional<Magacin> magacin = magacinRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(magacin);
    }

    /**
     * {@code DELETE  /magacins/:id} : delete the "id" magacin.
     *
     * @param id the id of the magacin to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/magacins/{id}")
    public ResponseEntity<Void> deleteMagacin(@PathVariable Long id) {
        log.debug("REST request to delete Magacin : {}", id);
        magacinRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
