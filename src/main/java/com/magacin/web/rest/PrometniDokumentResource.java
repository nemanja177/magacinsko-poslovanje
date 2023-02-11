package com.magacin.web.rest;

import com.magacin.domain.PrometniDokument;
import com.magacin.repository.PrometniDokumentRepository;
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
 * REST controller for managing {@link com.magacin.domain.PrometniDokument}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PrometniDokumentResource {

    private final Logger log = LoggerFactory.getLogger(PrometniDokumentResource.class);

    private static final String ENTITY_NAME = "prometniDokument";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PrometniDokumentRepository prometniDokumentRepository;

    public PrometniDokumentResource(PrometniDokumentRepository prometniDokumentRepository) {
        this.prometniDokumentRepository = prometniDokumentRepository;
    }

    /**
     * {@code POST  /prometni-dokuments} : Create a new prometniDokument.
     *
     * @param prometniDokument the prometniDokument to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new prometniDokument, or with status {@code 400 (Bad Request)} if the prometniDokument has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/prometni-dokuments")
    public ResponseEntity<PrometniDokument> createPrometniDokument(@RequestBody PrometniDokument prometniDokument)
        throws URISyntaxException {
        log.debug("REST request to save PrometniDokument : {}", prometniDokument);
        if (prometniDokument.getId() != null) {
            throw new BadRequestAlertException("A new prometniDokument cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PrometniDokument result = prometniDokumentRepository.save(prometniDokument);
        return ResponseEntity
            .created(new URI("/api/prometni-dokuments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /prometni-dokuments/:id} : Updates an existing prometniDokument.
     *
     * @param id the id of the prometniDokument to save.
     * @param prometniDokument the prometniDokument to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated prometniDokument,
     * or with status {@code 400 (Bad Request)} if the prometniDokument is not valid,
     * or with status {@code 500 (Internal Server Error)} if the prometniDokument couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/prometni-dokuments/{id}")
    public ResponseEntity<PrometniDokument> updatePrometniDokument(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PrometniDokument prometniDokument
    ) throws URISyntaxException {
        log.debug("REST request to update PrometniDokument : {}, {}", id, prometniDokument);
        if (prometniDokument.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, prometniDokument.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!prometniDokumentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PrometniDokument result = prometniDokumentRepository.save(prometniDokument);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, prometniDokument.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /prometni-dokuments/:id} : Partial updates given fields of an existing prometniDokument, field will ignore if it is null
     *
     * @param id the id of the prometniDokument to save.
     * @param prometniDokument the prometniDokument to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated prometniDokument,
     * or with status {@code 400 (Bad Request)} if the prometniDokument is not valid,
     * or with status {@code 404 (Not Found)} if the prometniDokument is not found,
     * or with status {@code 500 (Internal Server Error)} if the prometniDokument couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/prometni-dokuments/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PrometniDokument> partialUpdatePrometniDokument(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PrometniDokument prometniDokument
    ) throws URISyntaxException {
        log.debug("REST request to partial update PrometniDokument partially : {}, {}", id, prometniDokument);
        if (prometniDokument.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, prometniDokument.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!prometniDokumentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PrometniDokument> result = prometniDokumentRepository
            .findById(prometniDokument.getId())
            .map(existingPrometniDokument -> {
                if (prometniDokument.getBrojDokumenata() != null) {
                    existingPrometniDokument.setBrojDokumenata(prometniDokument.getBrojDokumenata());
                }
                if (prometniDokument.getDatum() != null) {
                    existingPrometniDokument.setDatum(prometniDokument.getDatum());
                }
                if (prometniDokument.getVrsta() != null) {
                    existingPrometniDokument.setVrsta(prometniDokument.getVrsta());
                }
                if (prometniDokument.getStatus() != null) {
                    existingPrometniDokument.setStatus(prometniDokument.getStatus());
                }

                return existingPrometniDokument;
            })
            .map(prometniDokumentRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, prometniDokument.getId().toString())
        );
    }

    /**
     * {@code GET  /prometni-dokuments} : get all the prometniDokuments.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of prometniDokuments in body.
     */
    @GetMapping("/prometni-dokuments")
    public List<PrometniDokument> getAllPrometniDokuments() {
        log.debug("REST request to get all PrometniDokuments");
        return prometniDokumentRepository.findAll();
    }

    /**
     * {@code GET  /prometni-dokuments/:id} : get the "id" prometniDokument.
     *
     * @param id the id of the prometniDokument to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the prometniDokument, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/prometni-dokuments/{id}")
    public ResponseEntity<PrometniDokument> getPrometniDokument(@PathVariable Long id) {
        log.debug("REST request to get PrometniDokument : {}", id);
        Optional<PrometniDokument> prometniDokument = prometniDokumentRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(prometniDokument);
    }

    /**
     * {@code DELETE  /prometni-dokuments/:id} : delete the "id" prometniDokument.
     *
     * @param id the id of the prometniDokument to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/prometni-dokuments/{id}")
    public ResponseEntity<Void> deletePrometniDokument(@PathVariable Long id) {
        log.debug("REST request to delete PrometniDokument : {}", id);
        prometniDokumentRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
