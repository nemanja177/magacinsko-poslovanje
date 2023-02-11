package com.magacin.web.rest;

import com.magacin.domain.PrometMagacinskeKartice;
import com.magacin.repository.PrometMagacinskeKarticeRepository;
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
 * REST controller for managing {@link com.magacin.domain.PrometMagacinskeKartice}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PrometMagacinskeKarticeResource {

    private final Logger log = LoggerFactory.getLogger(PrometMagacinskeKarticeResource.class);

    private static final String ENTITY_NAME = "prometMagacinskeKartice";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PrometMagacinskeKarticeRepository prometMagacinskeKarticeRepository;

    public PrometMagacinskeKarticeResource(PrometMagacinskeKarticeRepository prometMagacinskeKarticeRepository) {
        this.prometMagacinskeKarticeRepository = prometMagacinskeKarticeRepository;
    }

    /**
     * {@code POST  /promet-magacinske-kartices} : Create a new prometMagacinskeKartice.
     *
     * @param prometMagacinskeKartice the prometMagacinskeKartice to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new prometMagacinskeKartice, or with status {@code 400 (Bad Request)} if the prometMagacinskeKartice has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/promet-magacinske-kartices")
    public ResponseEntity<PrometMagacinskeKartice> createPrometMagacinskeKartice(
        @RequestBody PrometMagacinskeKartice prometMagacinskeKartice
    ) throws URISyntaxException {
        log.debug("REST request to save PrometMagacinskeKartice : {}", prometMagacinskeKartice);
        if (prometMagacinskeKartice.getId() != null) {
            throw new BadRequestAlertException("A new prometMagacinskeKartice cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PrometMagacinskeKartice result = prometMagacinskeKarticeRepository.save(prometMagacinskeKartice);
        return ResponseEntity
            .created(new URI("/api/promet-magacinske-kartices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /promet-magacinske-kartices/:id} : Updates an existing prometMagacinskeKartice.
     *
     * @param id the id of the prometMagacinskeKartice to save.
     * @param prometMagacinskeKartice the prometMagacinskeKartice to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated prometMagacinskeKartice,
     * or with status {@code 400 (Bad Request)} if the prometMagacinskeKartice is not valid,
     * or with status {@code 500 (Internal Server Error)} if the prometMagacinskeKartice couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/promet-magacinske-kartices/{id}")
    public ResponseEntity<PrometMagacinskeKartice> updatePrometMagacinskeKartice(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PrometMagacinskeKartice prometMagacinskeKartice
    ) throws URISyntaxException {
        log.debug("REST request to update PrometMagacinskeKartice : {}, {}", id, prometMagacinskeKartice);
        if (prometMagacinskeKartice.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, prometMagacinskeKartice.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!prometMagacinskeKarticeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PrometMagacinskeKartice result = prometMagacinskeKarticeRepository.save(prometMagacinskeKartice);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, prometMagacinskeKartice.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /promet-magacinske-kartices/:id} : Partial updates given fields of an existing prometMagacinskeKartice, field will ignore if it is null
     *
     * @param id the id of the prometMagacinskeKartice to save.
     * @param prometMagacinskeKartice the prometMagacinskeKartice to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated prometMagacinskeKartice,
     * or with status {@code 400 (Bad Request)} if the prometMagacinskeKartice is not valid,
     * or with status {@code 404 (Not Found)} if the prometMagacinskeKartice is not found,
     * or with status {@code 500 (Internal Server Error)} if the prometMagacinskeKartice couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/promet-magacinske-kartices/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PrometMagacinskeKartice> partialUpdatePrometMagacinskeKartice(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PrometMagacinskeKartice prometMagacinskeKartice
    ) throws URISyntaxException {
        log.debug("REST request to partial update PrometMagacinskeKartice partially : {}, {}", id, prometMagacinskeKartice);
        if (prometMagacinskeKartice.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, prometMagacinskeKartice.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!prometMagacinskeKarticeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PrometMagacinskeKartice> result = prometMagacinskeKarticeRepository
            .findById(prometMagacinskeKartice.getId())
            .map(existingPrometMagacinskeKartice -> {
                if (prometMagacinskeKartice.getDatumPrometa() != null) {
                    existingPrometMagacinskeKartice.setDatumPrometa(prometMagacinskeKartice.getDatumPrometa());
                }
                if (prometMagacinskeKartice.getKolicina() != null) {
                    existingPrometMagacinskeKartice.setKolicina(prometMagacinskeKartice.getKolicina());
                }
                if (prometMagacinskeKartice.getCena() != null) {
                    existingPrometMagacinskeKartice.setCena(prometMagacinskeKartice.getCena());
                }
                if (prometMagacinskeKartice.getVrednost() != null) {
                    existingPrometMagacinskeKartice.setVrednost(prometMagacinskeKartice.getVrednost());
                }
                if (prometMagacinskeKartice.getDokument() != null) {
                    existingPrometMagacinskeKartice.setDokument(prometMagacinskeKartice.getDokument());
                }
                if (prometMagacinskeKartice.getSmer() != null) {
                    existingPrometMagacinskeKartice.setSmer(prometMagacinskeKartice.getSmer());
                }

                return existingPrometMagacinskeKartice;
            })
            .map(prometMagacinskeKarticeRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, prometMagacinskeKartice.getId().toString())
        );
    }

    /**
     * {@code GET  /promet-magacinske-kartices} : get all the prometMagacinskeKartices.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of prometMagacinskeKartices in body.
     */
    @GetMapping("/promet-magacinske-kartices")
    public List<PrometMagacinskeKartice> getAllPrometMagacinskeKartices() {
        log.debug("REST request to get all PrometMagacinskeKartices");
        return prometMagacinskeKarticeRepository.findAll();
    }

    /**
     * {@code GET  /promet-magacinske-kartices/:id} : get the "id" prometMagacinskeKartice.
     *
     * @param id the id of the prometMagacinskeKartice to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the prometMagacinskeKartice, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/promet-magacinske-kartices/{id}")
    public ResponseEntity<PrometMagacinskeKartice> getPrometMagacinskeKartice(@PathVariable Long id) {
        log.debug("REST request to get PrometMagacinskeKartice : {}", id);
        Optional<PrometMagacinskeKartice> prometMagacinskeKartice = prometMagacinskeKarticeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(prometMagacinskeKartice);
    }

    /**
     * {@code DELETE  /promet-magacinske-kartices/:id} : delete the "id" prometMagacinskeKartice.
     *
     * @param id the id of the prometMagacinskeKartice to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/promet-magacinske-kartices/{id}")
    public ResponseEntity<Void> deletePrometMagacinskeKartice(@PathVariable Long id) {
        log.debug("REST request to delete PrometMagacinskeKartice : {}", id);
        prometMagacinskeKarticeRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
