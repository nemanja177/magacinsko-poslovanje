package com.magacin.web.rest;

import com.magacin.domain.StavkaPopisa;
import com.magacin.repository.StavkaPopisaRepository;
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
 * REST controller for managing {@link com.magacin.domain.StavkaPopisa}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class StavkaPopisaResource {

    private final Logger log = LoggerFactory.getLogger(StavkaPopisaResource.class);

    private static final String ENTITY_NAME = "stavkaPopisa";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StavkaPopisaRepository stavkaPopisaRepository;

    public StavkaPopisaResource(StavkaPopisaRepository stavkaPopisaRepository) {
        this.stavkaPopisaRepository = stavkaPopisaRepository;
    }

    /**
     * {@code POST  /stavka-popisas} : Create a new stavkaPopisa.
     *
     * @param stavkaPopisa the stavkaPopisa to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new stavkaPopisa, or with status {@code 400 (Bad Request)} if the stavkaPopisa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/stavka-popisas")
    public ResponseEntity<StavkaPopisa> createStavkaPopisa(@RequestBody StavkaPopisa stavkaPopisa) throws URISyntaxException {
        log.debug("REST request to save StavkaPopisa : {}", stavkaPopisa);
        if (stavkaPopisa.getId() != null) {
            throw new BadRequestAlertException("A new stavkaPopisa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StavkaPopisa result = stavkaPopisaRepository.save(stavkaPopisa);
        return ResponseEntity
            .created(new URI("/api/stavka-popisas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /stavka-popisas/:id} : Updates an existing stavkaPopisa.
     *
     * @param id the id of the stavkaPopisa to save.
     * @param stavkaPopisa the stavkaPopisa to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stavkaPopisa,
     * or with status {@code 400 (Bad Request)} if the stavkaPopisa is not valid,
     * or with status {@code 500 (Internal Server Error)} if the stavkaPopisa couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/stavka-popisas/{id}")
    public ResponseEntity<StavkaPopisa> updateStavkaPopisa(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody StavkaPopisa stavkaPopisa
    ) throws URISyntaxException {
        log.debug("REST request to update StavkaPopisa : {}, {}", id, stavkaPopisa);
        if (stavkaPopisa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, stavkaPopisa.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!stavkaPopisaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        StavkaPopisa result = stavkaPopisaRepository.save(stavkaPopisa);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, stavkaPopisa.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /stavka-popisas/:id} : Partial updates given fields of an existing stavkaPopisa, field will ignore if it is null
     *
     * @param id the id of the stavkaPopisa to save.
     * @param stavkaPopisa the stavkaPopisa to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stavkaPopisa,
     * or with status {@code 400 (Bad Request)} if the stavkaPopisa is not valid,
     * or with status {@code 404 (Not Found)} if the stavkaPopisa is not found,
     * or with status {@code 500 (Internal Server Error)} if the stavkaPopisa couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/stavka-popisas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<StavkaPopisa> partialUpdateStavkaPopisa(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody StavkaPopisa stavkaPopisa
    ) throws URISyntaxException {
        log.debug("REST request to partial update StavkaPopisa partially : {}, {}", id, stavkaPopisa);
        if (stavkaPopisa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, stavkaPopisa.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!stavkaPopisaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<StavkaPopisa> result = stavkaPopisaRepository
            .findById(stavkaPopisa.getId())
            .map(existingStavkaPopisa -> {
                if (stavkaPopisa.getKolicinaPopisa() != null) {
                    existingStavkaPopisa.setKolicinaPopisa(stavkaPopisa.getKolicinaPopisa());
                }
                if (stavkaPopisa.getKolicinaPoKnjigama() != null) {
                    existingStavkaPopisa.setKolicinaPoKnjigama(stavkaPopisa.getKolicinaPoKnjigama());
                }

                return existingStavkaPopisa;
            })
            .map(stavkaPopisaRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, stavkaPopisa.getId().toString())
        );
    }

    /**
     * {@code GET  /stavka-popisas} : get all the stavkaPopisas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stavkaPopisas in body.
     */
    @GetMapping("/stavka-popisas")
    public List<StavkaPopisa> getAllStavkaPopisas() {
        log.debug("REST request to get all StavkaPopisas");
        return stavkaPopisaRepository.findAll();
    }

    /**
     * {@code GET  /stavka-popisas/:id} : get the "id" stavkaPopisa.
     *
     * @param id the id of the stavkaPopisa to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the stavkaPopisa, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/stavka-popisas/{id}")
    public ResponseEntity<StavkaPopisa> getStavkaPopisa(@PathVariable Long id) {
        log.debug("REST request to get StavkaPopisa : {}", id);
        Optional<StavkaPopisa> stavkaPopisa = stavkaPopisaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(stavkaPopisa);
    }

    /**
     * {@code DELETE  /stavka-popisas/:id} : delete the "id" stavkaPopisa.
     *
     * @param id the id of the stavkaPopisa to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/stavka-popisas/{id}")
    public ResponseEntity<Void> deleteStavkaPopisa(@PathVariable Long id) {
        log.debug("REST request to delete StavkaPopisa : {}", id);
        stavkaPopisaRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
