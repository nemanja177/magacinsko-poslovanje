package com.magacin.web.rest;

import com.magacin.domain.MagacinskaKartica;
import com.magacin.repository.MagacinskaKarticaRepository;
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
 * REST controller for managing {@link com.magacin.domain.MagacinskaKartica}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class MagacinskaKarticaResource {

    private final Logger log = LoggerFactory.getLogger(MagacinskaKarticaResource.class);

    private static final String ENTITY_NAME = "magacinskaKartica";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MagacinskaKarticaRepository magacinskaKarticaRepository;

    public MagacinskaKarticaResource(MagacinskaKarticaRepository magacinskaKarticaRepository) {
        this.magacinskaKarticaRepository = magacinskaKarticaRepository;
    }

    /**
     * {@code POST  /magacinska-karticas} : Create a new magacinskaKartica.
     *
     * @param magacinskaKartica the magacinskaKartica to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new magacinskaKartica, or with status {@code 400 (Bad Request)} if the magacinskaKartica has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/magacinska-karticas")
    public ResponseEntity<MagacinskaKartica> createMagacinskaKartica(@RequestBody MagacinskaKartica magacinskaKartica)
        throws URISyntaxException {
        log.debug("REST request to save MagacinskaKartica : {}", magacinskaKartica);
        if (magacinskaKartica.getId() != null) {
            throw new BadRequestAlertException("A new magacinskaKartica cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MagacinskaKartica result = magacinskaKarticaRepository.save(magacinskaKartica);
        return ResponseEntity
            .created(new URI("/api/magacinska-karticas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /magacinska-karticas/:id} : Updates an existing magacinskaKartica.
     *
     * @param id the id of the magacinskaKartica to save.
     * @param magacinskaKartica the magacinskaKartica to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated magacinskaKartica,
     * or with status {@code 400 (Bad Request)} if the magacinskaKartica is not valid,
     * or with status {@code 500 (Internal Server Error)} if the magacinskaKartica couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/magacinska-karticas/{id}")
    public ResponseEntity<MagacinskaKartica> updateMagacinskaKartica(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MagacinskaKartica magacinskaKartica
    ) throws URISyntaxException {
        log.debug("REST request to update MagacinskaKartica : {}, {}", id, magacinskaKartica);
        if (magacinskaKartica.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, magacinskaKartica.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!magacinskaKarticaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MagacinskaKartica result = magacinskaKarticaRepository.save(magacinskaKartica);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, magacinskaKartica.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /magacinska-karticas/:id} : Partial updates given fields of an existing magacinskaKartica, field will ignore if it is null
     *
     * @param id the id of the magacinskaKartica to save.
     * @param magacinskaKartica the magacinskaKartica to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated magacinskaKartica,
     * or with status {@code 400 (Bad Request)} if the magacinskaKartica is not valid,
     * or with status {@code 404 (Not Found)} if the magacinskaKartica is not found,
     * or with status {@code 500 (Internal Server Error)} if the magacinskaKartica couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/magacinska-karticas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MagacinskaKartica> partialUpdateMagacinskaKartica(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MagacinskaKartica magacinskaKartica
    ) throws URISyntaxException {
        log.debug("REST request to partial update MagacinskaKartica partially : {}, {}", id, magacinskaKartica);
        if (magacinskaKartica.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, magacinskaKartica.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!magacinskaKarticaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MagacinskaKartica> result = magacinskaKarticaRepository
            .findById(magacinskaKartica.getId())
            .map(existingMagacinskaKartica -> {
                if (magacinskaKartica.getPocetnoStanjeKolicina() != null) {
                    existingMagacinskaKartica.setPocetnoStanjeKolicina(magacinskaKartica.getPocetnoStanjeKolicina());
                }
                if (magacinskaKartica.getPrometUlazaKolicina() != null) {
                    existingMagacinskaKartica.setPrometUlazaKolicina(magacinskaKartica.getPrometUlazaKolicina());
                }
                if (magacinskaKartica.getPrometIzlazaKolicina() != null) {
                    existingMagacinskaKartica.setPrometIzlazaKolicina(magacinskaKartica.getPrometIzlazaKolicina());
                }
                if (magacinskaKartica.getUkupnaKolicina() != null) {
                    existingMagacinskaKartica.setUkupnaKolicina(magacinskaKartica.getUkupnaKolicina());
                }
                if (magacinskaKartica.getPocetnoStanjeVrednosti() != null) {
                    existingMagacinskaKartica.setPocetnoStanjeVrednosti(magacinskaKartica.getPocetnoStanjeVrednosti());
                }
                if (magacinskaKartica.getPrometUlazaVrednosti() != null) {
                    existingMagacinskaKartica.setPrometUlazaVrednosti(magacinskaKartica.getPrometUlazaVrednosti());
                }
                if (magacinskaKartica.getPrometIzlazaVrednosti() != null) {
                    existingMagacinskaKartica.setPrometIzlazaVrednosti(magacinskaKartica.getPrometIzlazaVrednosti());
                }
                if (magacinskaKartica.getUkupnaVrednost() != null) {
                    existingMagacinskaKartica.setUkupnaVrednost(magacinskaKartica.getUkupnaVrednost());
                }

                return existingMagacinskaKartica;
            })
            .map(magacinskaKarticaRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, magacinskaKartica.getId().toString())
        );
    }

    /**
     * {@code GET  /magacinska-karticas} : get all the magacinskaKarticas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of magacinskaKarticas in body.
     */
    @GetMapping("/magacinska-karticas")
    public List<MagacinskaKartica> getAllMagacinskaKarticas() {
        log.debug("REST request to get all MagacinskaKarticas");
        return magacinskaKarticaRepository.findAll();
    }

    /**
     * {@code GET  /magacinska-karticas/:id} : get the "id" magacinskaKartica.
     *
     * @param id the id of the magacinskaKartica to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the magacinskaKartica, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/magacinska-karticas/{id}")
    public ResponseEntity<MagacinskaKartica> getMagacinskaKartica(@PathVariable Long id) {
        log.debug("REST request to get MagacinskaKartica : {}", id);
        Optional<MagacinskaKartica> magacinskaKartica = magacinskaKarticaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(magacinskaKartica);
    }

    /**
     * {@code DELETE  /magacinska-karticas/:id} : delete the "id" magacinskaKartica.
     *
     * @param id the id of the magacinskaKartica to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/magacinska-karticas/{id}")
    public ResponseEntity<Void> deleteMagacinskaKartica(@PathVariable Long id) {
        log.debug("REST request to delete MagacinskaKartica : {}", id);
        magacinskaKarticaRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
