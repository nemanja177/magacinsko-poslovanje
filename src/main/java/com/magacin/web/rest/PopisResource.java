package com.magacin.web.rest;

import com.magacin.domain.Popis;
import com.magacin.repository.PopisRepository;
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
 * REST controller for managing {@link com.magacin.domain.Popis}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PopisResource {

    private final Logger log = LoggerFactory.getLogger(PopisResource.class);

    private static final String ENTITY_NAME = "popis";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PopisRepository popisRepository;

    public PopisResource(PopisRepository popisRepository) {
        this.popisRepository = popisRepository;
    }

    /**
     * {@code POST  /popis} : Create a new popis.
     *
     * @param popis the popis to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new popis, or with status {@code 400 (Bad Request)} if the popis has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/popis")
    public ResponseEntity<Popis> createPopis(@RequestBody Popis popis) throws URISyntaxException {
        log.debug("REST request to save Popis : {}", popis);
        if (popis.getId() != null) {
            throw new BadRequestAlertException("A new popis cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Popis result = popisRepository.save(popis);
        return ResponseEntity
            .created(new URI("/api/popis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /popis/:id} : Updates an existing popis.
     *
     * @param id the id of the popis to save.
     * @param popis the popis to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated popis,
     * or with status {@code 400 (Bad Request)} if the popis is not valid,
     * or with status {@code 500 (Internal Server Error)} if the popis couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/popis/{id}")
    public ResponseEntity<Popis> updatePopis(@PathVariable(value = "id", required = false) final Long id, @RequestBody Popis popis)
        throws URISyntaxException {
        log.debug("REST request to update Popis : {}, {}", id, popis);
        if (popis.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, popis.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!popisRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Popis result = popisRepository.save(popis);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, popis.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /popis/:id} : Partial updates given fields of an existing popis, field will ignore if it is null
     *
     * @param id the id of the popis to save.
     * @param popis the popis to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated popis,
     * or with status {@code 400 (Bad Request)} if the popis is not valid,
     * or with status {@code 404 (Not Found)} if the popis is not found,
     * or with status {@code 500 (Internal Server Error)} if the popis couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/popis/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Popis> partialUpdatePopis(@PathVariable(value = "id", required = false) final Long id, @RequestBody Popis popis)
        throws URISyntaxException {
        log.debug("REST request to partial update Popis partially : {}, {}", id, popis);
        if (popis.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, popis.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!popisRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Popis> result = popisRepository
            .findById(popis.getId())
            .map(existingPopis -> {
                if (popis.getDatumPopisa() != null) {
                    existingPopis.setDatumPopisa(popis.getDatumPopisa());
                }
                if (popis.getBrojPopisa() != null) {
                    existingPopis.setBrojPopisa(popis.getBrojPopisa());
                }
                if (popis.getStatusPopisa() != null) {
                    existingPopis.setStatusPopisa(popis.getStatusPopisa());
                }

                return existingPopis;
            })
            .map(popisRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, popis.getId().toString())
        );
    }

    /**
     * {@code GET  /popis} : get all the popis.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of popis in body.
     */
    @GetMapping("/popis")
    public List<Popis> getAllPopis() {
        log.debug("REST request to get all Popis");
        return popisRepository.findAll();
    }

    /**
     * {@code GET  /popis/:id} : get the "id" popis.
     *
     * @param id the id of the popis to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the popis, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/popis/{id}")
    public ResponseEntity<Popis> getPopis(@PathVariable Long id) {
        log.debug("REST request to get Popis : {}", id);
        Optional<Popis> popis = popisRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(popis);
    }

    /**
     * {@code DELETE  /popis/:id} : delete the "id" popis.
     *
     * @param id the id of the popis to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/popis/{id}")
    public ResponseEntity<Void> deletePopis(@PathVariable Long id) {
        log.debug("REST request to delete Popis : {}", id);
        popisRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
