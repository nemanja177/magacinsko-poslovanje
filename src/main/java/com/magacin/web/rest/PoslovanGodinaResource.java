package com.magacin.web.rest;

import com.magacin.domain.PoslovanGodina;
import com.magacin.repository.PoslovanGodinaRepository;
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
 * REST controller for managing {@link com.magacin.domain.PoslovanGodina}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PoslovanGodinaResource {

    private final Logger log = LoggerFactory.getLogger(PoslovanGodinaResource.class);

    private static final String ENTITY_NAME = "poslovanGodina";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PoslovanGodinaRepository poslovanGodinaRepository;

    public PoslovanGodinaResource(PoslovanGodinaRepository poslovanGodinaRepository) {
        this.poslovanGodinaRepository = poslovanGodinaRepository;
    }

    /**
     * {@code POST  /poslovan-godinas} : Create a new poslovanGodina.
     *
     * @param poslovanGodina the poslovanGodina to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new poslovanGodina, or with status {@code 400 (Bad Request)} if the poslovanGodina has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/poslovan-godinas")
    public ResponseEntity<PoslovanGodina> createPoslovanGodina(@RequestBody PoslovanGodina poslovanGodina) throws URISyntaxException {
        log.debug("REST request to save PoslovanGodina : {}", poslovanGodina);
        if (poslovanGodina.getId() != null) {
            throw new BadRequestAlertException("A new poslovanGodina cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PoslovanGodina result = poslovanGodinaRepository.save(poslovanGodina);
        return ResponseEntity
            .created(new URI("/api/poslovan-godinas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /poslovan-godinas/:id} : Updates an existing poslovanGodina.
     *
     * @param id the id of the poslovanGodina to save.
     * @param poslovanGodina the poslovanGodina to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated poslovanGodina,
     * or with status {@code 400 (Bad Request)} if the poslovanGodina is not valid,
     * or with status {@code 500 (Internal Server Error)} if the poslovanGodina couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/poslovan-godinas/{id}")
    public ResponseEntity<PoslovanGodina> updatePoslovanGodina(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PoslovanGodina poslovanGodina
    ) throws URISyntaxException {
        log.debug("REST request to update PoslovanGodina : {}, {}", id, poslovanGodina);
        if (poslovanGodina.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, poslovanGodina.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!poslovanGodinaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PoslovanGodina result = poslovanGodinaRepository.save(poslovanGodina);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, poslovanGodina.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /poslovan-godinas/:id} : Partial updates given fields of an existing poslovanGodina, field will ignore if it is null
     *
     * @param id the id of the poslovanGodina to save.
     * @param poslovanGodina the poslovanGodina to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated poslovanGodina,
     * or with status {@code 400 (Bad Request)} if the poslovanGodina is not valid,
     * or with status {@code 404 (Not Found)} if the poslovanGodina is not found,
     * or with status {@code 500 (Internal Server Error)} if the poslovanGodina couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/poslovan-godinas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PoslovanGodina> partialUpdatePoslovanGodina(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PoslovanGodina poslovanGodina
    ) throws URISyntaxException {
        log.debug("REST request to partial update PoslovanGodina partially : {}, {}", id, poslovanGodina);
        if (poslovanGodina.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, poslovanGodina.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!poslovanGodinaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PoslovanGodina> result = poslovanGodinaRepository
            .findById(poslovanGodina.getId())
            .map(existingPoslovanGodina -> {
                if (poslovanGodina.getGodina() != null) {
                    existingPoslovanGodina.setGodina(poslovanGodina.getGodina());
                }
                if (poslovanGodina.getZakljucena() != null) {
                    existingPoslovanGodina.setZakljucena(poslovanGodina.getZakljucena());
                }

                return existingPoslovanGodina;
            })
            .map(poslovanGodinaRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, poslovanGodina.getId().toString())
        );
    }

    /**
     * {@code GET  /poslovan-godinas} : get all the poslovanGodinas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of poslovanGodinas in body.
     */
    @GetMapping("/poslovan-godinas")
    public List<PoslovanGodina> getAllPoslovanGodinas() {
        log.debug("REST request to get all PoslovanGodinas");
        return poslovanGodinaRepository.findAll();
    }

    /**
     * {@code GET  /poslovan-godinas/:id} : get the "id" poslovanGodina.
     *
     * @param id the id of the poslovanGodina to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the poslovanGodina, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/poslovan-godinas/{id}")
    public ResponseEntity<PoslovanGodina> getPoslovanGodina(@PathVariable Long id) {
        log.debug("REST request to get PoslovanGodina : {}", id);
        Optional<PoslovanGodina> poslovanGodina = poslovanGodinaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(poslovanGodina);
    }

    /**
     * {@code DELETE  /poslovan-godinas/:id} : delete the "id" poslovanGodina.
     *
     * @param id the id of the poslovanGodina to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/poslovan-godinas/{id}")
    public ResponseEntity<Void> deletePoslovanGodina(@PathVariable Long id) {
        log.debug("REST request to delete PoslovanGodina : {}", id);
        poslovanGodinaRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
