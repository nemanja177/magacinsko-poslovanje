package com.magacin.web.rest;

import com.magacin.domain.StavkaPrometnogDokumenta;
import com.magacin.repository.StavkaPrometnogDokumentaRepository;
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
 * REST controller for managing {@link com.magacin.domain.StavkaPrometnogDokumenta}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class StavkaPrometnogDokumentaResource {

    private final Logger log = LoggerFactory.getLogger(StavkaPrometnogDokumentaResource.class);

    private static final String ENTITY_NAME = "stavkaPrometnogDokumenta";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StavkaPrometnogDokumentaRepository stavkaPrometnogDokumentaRepository;

    public StavkaPrometnogDokumentaResource(StavkaPrometnogDokumentaRepository stavkaPrometnogDokumentaRepository) {
        this.stavkaPrometnogDokumentaRepository = stavkaPrometnogDokumentaRepository;
    }

    /**
     * {@code POST  /stavka-prometnog-dokumentas} : Create a new stavkaPrometnogDokumenta.
     *
     * @param stavkaPrometnogDokumenta the stavkaPrometnogDokumenta to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new stavkaPrometnogDokumenta, or with status {@code 400 (Bad Request)} if the stavkaPrometnogDokumenta has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/stavka-prometnog-dokumentas")
    public ResponseEntity<StavkaPrometnogDokumenta> createStavkaPrometnogDokumenta(
        @RequestBody StavkaPrometnogDokumenta stavkaPrometnogDokumenta
    ) throws URISyntaxException {
        log.debug("REST request to save StavkaPrometnogDokumenta : {}", stavkaPrometnogDokumenta);
        if (stavkaPrometnogDokumenta.getId() != null) {
            throw new BadRequestAlertException("A new stavkaPrometnogDokumenta cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StavkaPrometnogDokumenta result = stavkaPrometnogDokumentaRepository.save(stavkaPrometnogDokumenta);
        return ResponseEntity
            .created(new URI("/api/stavka-prometnog-dokumentas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /stavka-prometnog-dokumentas/:id} : Updates an existing stavkaPrometnogDokumenta.
     *
     * @param id the id of the stavkaPrometnogDokumenta to save.
     * @param stavkaPrometnogDokumenta the stavkaPrometnogDokumenta to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stavkaPrometnogDokumenta,
     * or with status {@code 400 (Bad Request)} if the stavkaPrometnogDokumenta is not valid,
     * or with status {@code 500 (Internal Server Error)} if the stavkaPrometnogDokumenta couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/stavka-prometnog-dokumentas/{id}")
    public ResponseEntity<StavkaPrometnogDokumenta> updateStavkaPrometnogDokumenta(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody StavkaPrometnogDokumenta stavkaPrometnogDokumenta
    ) throws URISyntaxException {
        log.debug("REST request to update StavkaPrometnogDokumenta : {}, {}", id, stavkaPrometnogDokumenta);
        if (stavkaPrometnogDokumenta.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, stavkaPrometnogDokumenta.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!stavkaPrometnogDokumentaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        StavkaPrometnogDokumenta result = stavkaPrometnogDokumentaRepository.save(stavkaPrometnogDokumenta);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, stavkaPrometnogDokumenta.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /stavka-prometnog-dokumentas/:id} : Partial updates given fields of an existing stavkaPrometnogDokumenta, field will ignore if it is null
     *
     * @param id the id of the stavkaPrometnogDokumenta to save.
     * @param stavkaPrometnogDokumenta the stavkaPrometnogDokumenta to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stavkaPrometnogDokumenta,
     * or with status {@code 400 (Bad Request)} if the stavkaPrometnogDokumenta is not valid,
     * or with status {@code 404 (Not Found)} if the stavkaPrometnogDokumenta is not found,
     * or with status {@code 500 (Internal Server Error)} if the stavkaPrometnogDokumenta couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/stavka-prometnog-dokumentas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<StavkaPrometnogDokumenta> partialUpdateStavkaPrometnogDokumenta(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody StavkaPrometnogDokumenta stavkaPrometnogDokumenta
    ) throws URISyntaxException {
        log.debug("REST request to partial update StavkaPrometnogDokumenta partially : {}, {}", id, stavkaPrometnogDokumenta);
        if (stavkaPrometnogDokumenta.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, stavkaPrometnogDokumenta.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!stavkaPrometnogDokumentaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<StavkaPrometnogDokumenta> result = stavkaPrometnogDokumentaRepository
            .findById(stavkaPrometnogDokumenta.getId())
            .map(existingStavkaPrometnogDokumenta -> {
                if (stavkaPrometnogDokumenta.getKolicina() != null) {
                    existingStavkaPrometnogDokumenta.setKolicina(stavkaPrometnogDokumenta.getKolicina());
                }
                if (stavkaPrometnogDokumenta.getCena() != null) {
                    existingStavkaPrometnogDokumenta.setCena(stavkaPrometnogDokumenta.getCena());
                }
                if (stavkaPrometnogDokumenta.getVrednost() != null) {
                    existingStavkaPrometnogDokumenta.setVrednost(stavkaPrometnogDokumenta.getVrednost());
                }

                return existingStavkaPrometnogDokumenta;
            })
            .map(stavkaPrometnogDokumentaRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, stavkaPrometnogDokumenta.getId().toString())
        );
    }

    /**
     * {@code GET  /stavka-prometnog-dokumentas} : get all the stavkaPrometnogDokumentas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stavkaPrometnogDokumentas in body.
     */
    @GetMapping("/stavka-prometnog-dokumentas")
    public List<StavkaPrometnogDokumenta> getAllStavkaPrometnogDokumentas() {
        log.debug("REST request to get all StavkaPrometnogDokumentas");
        return stavkaPrometnogDokumentaRepository.findAll();
    }

    /**
     * {@code GET  /stavka-prometnog-dokumentas/:id} : get the "id" stavkaPrometnogDokumenta.
     *
     * @param id the id of the stavkaPrometnogDokumenta to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the stavkaPrometnogDokumenta, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/stavka-prometnog-dokumentas/{id}")
    public ResponseEntity<StavkaPrometnogDokumenta> getStavkaPrometnogDokumenta(@PathVariable Long id) {
        log.debug("REST request to get StavkaPrometnogDokumenta : {}", id);
        Optional<StavkaPrometnogDokumenta> stavkaPrometnogDokumenta = stavkaPrometnogDokumentaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(stavkaPrometnogDokumenta);
    }

    /**
     * {@code DELETE  /stavka-prometnog-dokumentas/:id} : delete the "id" stavkaPrometnogDokumenta.
     *
     * @param id the id of the stavkaPrometnogDokumenta to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/stavka-prometnog-dokumentas/{id}")
    public ResponseEntity<Void> deleteStavkaPrometnogDokumenta(@PathVariable Long id) {
        log.debug("REST request to delete StavkaPrometnogDokumenta : {}", id);
        stavkaPrometnogDokumentaRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
