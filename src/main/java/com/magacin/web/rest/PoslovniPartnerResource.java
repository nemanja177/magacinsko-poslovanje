package com.magacin.web.rest;

import com.magacin.domain.PoslovniPartner;
import com.magacin.repository.PoslovniPartnerRepository;
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
 * REST controller for managing {@link com.magacin.domain.PoslovniPartner}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PoslovniPartnerResource {

    private final Logger log = LoggerFactory.getLogger(PoslovniPartnerResource.class);

    private static final String ENTITY_NAME = "poslovniPartner";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PoslovniPartnerRepository poslovniPartnerRepository;

    public PoslovniPartnerResource(PoslovniPartnerRepository poslovniPartnerRepository) {
        this.poslovniPartnerRepository = poslovniPartnerRepository;
    }

    /**
     * {@code POST  /poslovni-partners} : Create a new poslovniPartner.
     *
     * @param poslovniPartner the poslovniPartner to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new poslovniPartner, or with status {@code 400 (Bad Request)} if the poslovniPartner has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/poslovni-partners")
    public ResponseEntity<PoslovniPartner> createPoslovniPartner(@RequestBody PoslovniPartner poslovniPartner) throws URISyntaxException {
        log.debug("REST request to save PoslovniPartner : {}", poslovniPartner);
        if (poslovniPartner.getId() != null) {
            throw new BadRequestAlertException("A new poslovniPartner cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PoslovniPartner result = poslovniPartnerRepository.save(poslovniPartner);
        return ResponseEntity
            .created(new URI("/api/poslovni-partners/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /poslovni-partners/:id} : Updates an existing poslovniPartner.
     *
     * @param id the id of the poslovniPartner to save.
     * @param poslovniPartner the poslovniPartner to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated poslovniPartner,
     * or with status {@code 400 (Bad Request)} if the poslovniPartner is not valid,
     * or with status {@code 500 (Internal Server Error)} if the poslovniPartner couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/poslovni-partners/{id}")
    public ResponseEntity<PoslovniPartner> updatePoslovniPartner(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PoslovniPartner poslovniPartner
    ) throws URISyntaxException {
        log.debug("REST request to update PoslovniPartner : {}, {}", id, poslovniPartner);
        if (poslovniPartner.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, poslovniPartner.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!poslovniPartnerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PoslovniPartner result = poslovniPartnerRepository.save(poslovniPartner);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, poslovniPartner.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /poslovni-partners/:id} : Partial updates given fields of an existing poslovniPartner, field will ignore if it is null
     *
     * @param id the id of the poslovniPartner to save.
     * @param poslovniPartner the poslovniPartner to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated poslovniPartner,
     * or with status {@code 400 (Bad Request)} if the poslovniPartner is not valid,
     * or with status {@code 404 (Not Found)} if the poslovniPartner is not found,
     * or with status {@code 500 (Internal Server Error)} if the poslovniPartner couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/poslovni-partners/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PoslovniPartner> partialUpdatePoslovniPartner(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PoslovniPartner poslovniPartner
    ) throws URISyntaxException {
        log.debug("REST request to partial update PoslovniPartner partially : {}, {}", id, poslovniPartner);
        if (poslovniPartner.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, poslovniPartner.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!poslovniPartnerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PoslovniPartner> result = poslovniPartnerRepository
            .findById(poslovniPartner.getId())
            .map(existingPoslovniPartner -> {
                if (poslovniPartner.getIme() != null) {
                    existingPoslovniPartner.setIme(poslovniPartner.getIme());
                }
                if (poslovniPartner.getPrezime() != null) {
                    existingPoslovniPartner.setPrezime(poslovniPartner.getPrezime());
                }
                if (poslovniPartner.getEmail() != null) {
                    existingPoslovniPartner.setEmail(poslovniPartner.getEmail());
                }
                if (poslovniPartner.getJmbg() != null) {
                    existingPoslovniPartner.setJmbg(poslovniPartner.getJmbg());
                }
                if (poslovniPartner.getAdresa() != null) {
                    existingPoslovniPartner.setAdresa(poslovniPartner.getAdresa());
                }

                return existingPoslovniPartner;
            })
            .map(poslovniPartnerRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, poslovniPartner.getId().toString())
        );
    }

    /**
     * {@code GET  /poslovni-partners} : get all the poslovniPartners.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of poslovniPartners in body.
     */
    @GetMapping("/poslovni-partners")
    public List<PoslovniPartner> getAllPoslovniPartners() {
        log.debug("REST request to get all PoslovniPartners");
        return poslovniPartnerRepository.findAll();
    }

    /**
     * {@code GET  /poslovni-partners/:id} : get the "id" poslovniPartner.
     *
     * @param id the id of the poslovniPartner to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the poslovniPartner, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/poslovni-partners/{id}")
    public ResponseEntity<PoslovniPartner> getPoslovniPartner(@PathVariable Long id) {
        log.debug("REST request to get PoslovniPartner : {}", id);
        Optional<PoslovniPartner> poslovniPartner = poslovniPartnerRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(poslovniPartner);
    }

    /**
     * {@code DELETE  /poslovni-partners/:id} : delete the "id" poslovniPartner.
     *
     * @param id the id of the poslovniPartner to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/poslovni-partners/{id}")
    public ResponseEntity<Void> deletePoslovniPartner(@PathVariable Long id) {
        log.debug("REST request to delete PoslovniPartner : {}", id);
        poslovniPartnerRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
