package com.magacin.web.rest;

import com.magacin.domain.AnalitickaMagacinskaKartica;
import com.magacin.repository.AnalitickaMagacinskaKarticaRepository;
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
 * REST controller for managing {@link com.magacin.domain.AnalitickaMagacinskaKartica}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AnalitickaMagacinskaKarticaResource {

    private final Logger log = LoggerFactory.getLogger(AnalitickaMagacinskaKarticaResource.class);

    private static final String ENTITY_NAME = "analitickaMagacinskaKartica";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AnalitickaMagacinskaKarticaRepository analitickaMagacinskaKarticaRepository;

    public AnalitickaMagacinskaKarticaResource(AnalitickaMagacinskaKarticaRepository analitickaMagacinskaKarticaRepository) {
        this.analitickaMagacinskaKarticaRepository = analitickaMagacinskaKarticaRepository;
    }

    /**
     * {@code POST  /analiticka-magacinska-karticas} : Create a new analitickaMagacinskaKartica.
     *
     * @param analitickaMagacinskaKartica the analitickaMagacinskaKartica to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new analitickaMagacinskaKartica, or with status {@code 400 (Bad Request)} if the analitickaMagacinskaKartica has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/analiticka-magacinska-karticas")
    public ResponseEntity<AnalitickaMagacinskaKartica> createAnalitickaMagacinskaKartica(
        @RequestBody AnalitickaMagacinskaKartica analitickaMagacinskaKartica
    ) throws URISyntaxException {
        log.debug("REST request to save AnalitickaMagacinskaKartica : {}", analitickaMagacinskaKartica);
        if (analitickaMagacinskaKartica.getId() != null) {
            throw new BadRequestAlertException("A new analitickaMagacinskaKartica cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AnalitickaMagacinskaKartica result = analitickaMagacinskaKarticaRepository.save(analitickaMagacinskaKartica);
        return ResponseEntity
            .created(new URI("/api/analiticka-magacinska-karticas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /analiticka-magacinska-karticas/:id} : Updates an existing analitickaMagacinskaKartica.
     *
     * @param id the id of the analitickaMagacinskaKartica to save.
     * @param analitickaMagacinskaKartica the analitickaMagacinskaKartica to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated analitickaMagacinskaKartica,
     * or with status {@code 400 (Bad Request)} if the analitickaMagacinskaKartica is not valid,
     * or with status {@code 500 (Internal Server Error)} if the analitickaMagacinskaKartica couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/analiticka-magacinska-karticas/{id}")
    public ResponseEntity<AnalitickaMagacinskaKartica> updateAnalitickaMagacinskaKartica(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AnalitickaMagacinskaKartica analitickaMagacinskaKartica
    ) throws URISyntaxException {
        log.debug("REST request to update AnalitickaMagacinskaKartica : {}, {}", id, analitickaMagacinskaKartica);
        if (analitickaMagacinskaKartica.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, analitickaMagacinskaKartica.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!analitickaMagacinskaKarticaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AnalitickaMagacinskaKartica result = analitickaMagacinskaKarticaRepository.save(analitickaMagacinskaKartica);
        return ResponseEntity
            .ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, analitickaMagacinskaKartica.getId().toString())
            )
            .body(result);
    }

    /**
     * {@code PATCH  /analiticka-magacinska-karticas/:id} : Partial updates given fields of an existing analitickaMagacinskaKartica, field will ignore if it is null
     *
     * @param id the id of the analitickaMagacinskaKartica to save.
     * @param analitickaMagacinskaKartica the analitickaMagacinskaKartica to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated analitickaMagacinskaKartica,
     * or with status {@code 400 (Bad Request)} if the analitickaMagacinskaKartica is not valid,
     * or with status {@code 404 (Not Found)} if the analitickaMagacinskaKartica is not found,
     * or with status {@code 500 (Internal Server Error)} if the analitickaMagacinskaKartica couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/analiticka-magacinska-karticas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AnalitickaMagacinskaKartica> partialUpdateAnalitickaMagacinskaKartica(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AnalitickaMagacinskaKartica analitickaMagacinskaKartica
    ) throws URISyntaxException {
        log.debug("REST request to partial update AnalitickaMagacinskaKartica partially : {}, {}", id, analitickaMagacinskaKartica);
        if (analitickaMagacinskaKartica.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, analitickaMagacinskaKartica.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!analitickaMagacinskaKarticaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AnalitickaMagacinskaKartica> result = analitickaMagacinskaKarticaRepository
            .findById(analitickaMagacinskaKartica.getId())
            .map(existingAnalitickaMagacinskaKartica -> {
                if (analitickaMagacinskaKartica.getDatumPrometa() != null) {
                    existingAnalitickaMagacinskaKartica.setDatumPrometa(analitickaMagacinskaKartica.getDatumPrometa());
                }
                if (analitickaMagacinskaKartica.getKolicina() != null) {
                    existingAnalitickaMagacinskaKartica.setKolicina(analitickaMagacinskaKartica.getKolicina());
                }
                if (analitickaMagacinskaKartica.getCena() != null) {
                    existingAnalitickaMagacinskaKartica.setCena(analitickaMagacinskaKartica.getCena());
                }
                if (analitickaMagacinskaKartica.getVrednost() != null) {
                    existingAnalitickaMagacinskaKartica.setVrednost(analitickaMagacinskaKartica.getVrednost());
                }
                if (analitickaMagacinskaKartica.getDokument() != null) {
                    existingAnalitickaMagacinskaKartica.setDokument(analitickaMagacinskaKartica.getDokument());
                }
                if (analitickaMagacinskaKartica.getSmer() != null) {
                    existingAnalitickaMagacinskaKartica.setSmer(analitickaMagacinskaKartica.getSmer());
                }

                return existingAnalitickaMagacinskaKartica;
            })
            .map(analitickaMagacinskaKarticaRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, analitickaMagacinskaKartica.getId().toString())
        );
    }

    /**
     * {@code GET  /analiticka-magacinska-karticas} : get all the analitickaMagacinskaKarticas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of analitickaMagacinskaKarticas in body.
     */
    @GetMapping("/analiticka-magacinska-karticas")
    public List<AnalitickaMagacinskaKartica> getAllAnalitickaMagacinskaKarticas() {
        log.debug("REST request to get all AnalitickaMagacinskaKarticas");
        return analitickaMagacinskaKarticaRepository.findAll();
    }

    /**
     * {@code GET  /analiticka-magacinska-karticas/:id} : get the "id" analitickaMagacinskaKartica.
     *
     * @param id the id of the analitickaMagacinskaKartica to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the analitickaMagacinskaKartica, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/analiticka-magacinska-karticas/{id}")
    public ResponseEntity<AnalitickaMagacinskaKartica> getAnalitickaMagacinskaKartica(@PathVariable Long id) {
        log.debug("REST request to get AnalitickaMagacinskaKartica : {}", id);
        Optional<AnalitickaMagacinskaKartica> analitickaMagacinskaKartica = analitickaMagacinskaKarticaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(analitickaMagacinskaKartica);
    }

    /**
     * {@code DELETE  /analiticka-magacinska-karticas/:id} : delete the "id" analitickaMagacinskaKartica.
     *
     * @param id the id of the analitickaMagacinskaKartica to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/analiticka-magacinska-karticas/{id}")
    public ResponseEntity<Void> deleteAnalitickaMagacinskaKartica(@PathVariable Long id) {
        log.debug("REST request to delete AnalitickaMagacinskaKartica : {}", id);
        analitickaMagacinskaKarticaRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
