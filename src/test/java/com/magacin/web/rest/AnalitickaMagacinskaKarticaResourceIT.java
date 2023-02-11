package com.magacin.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.magacin.IntegrationTest;
import com.magacin.domain.AnalitickaMagacinskaKartica;
import com.magacin.repository.AnalitickaMagacinskaKarticaRepository;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AnalitickaMagacinskaKarticaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AnalitickaMagacinskaKarticaResourceIT {

    private static final LocalDate DEFAULT_DATUM_PROMETA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUM_PROMETA = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_KOLICINA = 1L;
    private static final Long UPDATED_KOLICINA = 2L;

    private static final Long DEFAULT_CENA = 1L;
    private static final Long UPDATED_CENA = 2L;

    private static final Long DEFAULT_VREDNOST = 1L;
    private static final Long UPDATED_VREDNOST = 2L;

    private static final String DEFAULT_DOKUMENT = "AAAAAAAAAA";
    private static final String UPDATED_DOKUMENT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_SMER = false;
    private static final Boolean UPDATED_SMER = true;

    private static final String ENTITY_API_URL = "/api/analiticka-magacinska-karticas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AnalitickaMagacinskaKarticaRepository analitickaMagacinskaKarticaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAnalitickaMagacinskaKarticaMockMvc;

    private AnalitickaMagacinskaKartica analitickaMagacinskaKartica;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnalitickaMagacinskaKartica createEntity(EntityManager em) {
        AnalitickaMagacinskaKartica analitickaMagacinskaKartica = new AnalitickaMagacinskaKartica()
            .datumPrometa(DEFAULT_DATUM_PROMETA)
            .kolicina(DEFAULT_KOLICINA)
            .cena(DEFAULT_CENA)
            .vrednost(DEFAULT_VREDNOST)
            .dokument(DEFAULT_DOKUMENT)
            .smer(DEFAULT_SMER);
        return analitickaMagacinskaKartica;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnalitickaMagacinskaKartica createUpdatedEntity(EntityManager em) {
        AnalitickaMagacinskaKartica analitickaMagacinskaKartica = new AnalitickaMagacinskaKartica()
            .datumPrometa(UPDATED_DATUM_PROMETA)
            .kolicina(UPDATED_KOLICINA)
            .cena(UPDATED_CENA)
            .vrednost(UPDATED_VREDNOST)
            .dokument(UPDATED_DOKUMENT)
            .smer(UPDATED_SMER);
        return analitickaMagacinskaKartica;
    }

    @BeforeEach
    public void initTest() {
        analitickaMagacinskaKartica = createEntity(em);
    }

    @Test
    @Transactional
    void createAnalitickaMagacinskaKartica() throws Exception {
        int databaseSizeBeforeCreate = analitickaMagacinskaKarticaRepository.findAll().size();
        // Create the AnalitickaMagacinskaKartica
        restAnalitickaMagacinskaKarticaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(analitickaMagacinskaKartica))
            )
            .andExpect(status().isCreated());

        // Validate the AnalitickaMagacinskaKartica in the database
        List<AnalitickaMagacinskaKartica> analitickaMagacinskaKarticaList = analitickaMagacinskaKarticaRepository.findAll();
        assertThat(analitickaMagacinskaKarticaList).hasSize(databaseSizeBeforeCreate + 1);
        AnalitickaMagacinskaKartica testAnalitickaMagacinskaKartica = analitickaMagacinskaKarticaList.get(
            analitickaMagacinskaKarticaList.size() - 1
        );
        assertThat(testAnalitickaMagacinskaKartica.getDatumPrometa()).isEqualTo(DEFAULT_DATUM_PROMETA);
        assertThat(testAnalitickaMagacinskaKartica.getKolicina()).isEqualTo(DEFAULT_KOLICINA);
        assertThat(testAnalitickaMagacinskaKartica.getCena()).isEqualTo(DEFAULT_CENA);
        assertThat(testAnalitickaMagacinskaKartica.getVrednost()).isEqualTo(DEFAULT_VREDNOST);
        assertThat(testAnalitickaMagacinskaKartica.getDokument()).isEqualTo(DEFAULT_DOKUMENT);
        assertThat(testAnalitickaMagacinskaKartica.getSmer()).isEqualTo(DEFAULT_SMER);
    }

    @Test
    @Transactional
    void createAnalitickaMagacinskaKarticaWithExistingId() throws Exception {
        // Create the AnalitickaMagacinskaKartica with an existing ID
        analitickaMagacinskaKartica.setId(1L);

        int databaseSizeBeforeCreate = analitickaMagacinskaKarticaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnalitickaMagacinskaKarticaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(analitickaMagacinskaKartica))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnalitickaMagacinskaKartica in the database
        List<AnalitickaMagacinskaKartica> analitickaMagacinskaKarticaList = analitickaMagacinskaKarticaRepository.findAll();
        assertThat(analitickaMagacinskaKarticaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAnalitickaMagacinskaKarticas() throws Exception {
        // Initialize the database
        analitickaMagacinskaKarticaRepository.saveAndFlush(analitickaMagacinskaKartica);

        // Get all the analitickaMagacinskaKarticaList
        restAnalitickaMagacinskaKarticaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(analitickaMagacinskaKartica.getId().intValue())))
            .andExpect(jsonPath("$.[*].datumPrometa").value(hasItem(DEFAULT_DATUM_PROMETA.toString())))
            .andExpect(jsonPath("$.[*].kolicina").value(hasItem(DEFAULT_KOLICINA.intValue())))
            .andExpect(jsonPath("$.[*].cena").value(hasItem(DEFAULT_CENA.intValue())))
            .andExpect(jsonPath("$.[*].vrednost").value(hasItem(DEFAULT_VREDNOST.intValue())))
            .andExpect(jsonPath("$.[*].dokument").value(hasItem(DEFAULT_DOKUMENT)))
            .andExpect(jsonPath("$.[*].smer").value(hasItem(DEFAULT_SMER.booleanValue())));
    }

    @Test
    @Transactional
    void getAnalitickaMagacinskaKartica() throws Exception {
        // Initialize the database
        analitickaMagacinskaKarticaRepository.saveAndFlush(analitickaMagacinskaKartica);

        // Get the analitickaMagacinskaKartica
        restAnalitickaMagacinskaKarticaMockMvc
            .perform(get(ENTITY_API_URL_ID, analitickaMagacinskaKartica.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(analitickaMagacinskaKartica.getId().intValue()))
            .andExpect(jsonPath("$.datumPrometa").value(DEFAULT_DATUM_PROMETA.toString()))
            .andExpect(jsonPath("$.kolicina").value(DEFAULT_KOLICINA.intValue()))
            .andExpect(jsonPath("$.cena").value(DEFAULT_CENA.intValue()))
            .andExpect(jsonPath("$.vrednost").value(DEFAULT_VREDNOST.intValue()))
            .andExpect(jsonPath("$.dokument").value(DEFAULT_DOKUMENT))
            .andExpect(jsonPath("$.smer").value(DEFAULT_SMER.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingAnalitickaMagacinskaKartica() throws Exception {
        // Get the analitickaMagacinskaKartica
        restAnalitickaMagacinskaKarticaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAnalitickaMagacinskaKartica() throws Exception {
        // Initialize the database
        analitickaMagacinskaKarticaRepository.saveAndFlush(analitickaMagacinskaKartica);

        int databaseSizeBeforeUpdate = analitickaMagacinskaKarticaRepository.findAll().size();

        // Update the analitickaMagacinskaKartica
        AnalitickaMagacinskaKartica updatedAnalitickaMagacinskaKartica = analitickaMagacinskaKarticaRepository
            .findById(analitickaMagacinskaKartica.getId())
            .get();
        // Disconnect from session so that the updates on updatedAnalitickaMagacinskaKartica are not directly saved in db
        em.detach(updatedAnalitickaMagacinskaKartica);
        updatedAnalitickaMagacinskaKartica
            .datumPrometa(UPDATED_DATUM_PROMETA)
            .kolicina(UPDATED_KOLICINA)
            .cena(UPDATED_CENA)
            .vrednost(UPDATED_VREDNOST)
            .dokument(UPDATED_DOKUMENT)
            .smer(UPDATED_SMER);

        restAnalitickaMagacinskaKarticaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAnalitickaMagacinskaKartica.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAnalitickaMagacinskaKartica))
            )
            .andExpect(status().isOk());

        // Validate the AnalitickaMagacinskaKartica in the database
        List<AnalitickaMagacinskaKartica> analitickaMagacinskaKarticaList = analitickaMagacinskaKarticaRepository.findAll();
        assertThat(analitickaMagacinskaKarticaList).hasSize(databaseSizeBeforeUpdate);
        AnalitickaMagacinskaKartica testAnalitickaMagacinskaKartica = analitickaMagacinskaKarticaList.get(
            analitickaMagacinskaKarticaList.size() - 1
        );
        assertThat(testAnalitickaMagacinskaKartica.getDatumPrometa()).isEqualTo(UPDATED_DATUM_PROMETA);
        assertThat(testAnalitickaMagacinskaKartica.getKolicina()).isEqualTo(UPDATED_KOLICINA);
        assertThat(testAnalitickaMagacinskaKartica.getCena()).isEqualTo(UPDATED_CENA);
        assertThat(testAnalitickaMagacinskaKartica.getVrednost()).isEqualTo(UPDATED_VREDNOST);
        assertThat(testAnalitickaMagacinskaKartica.getDokument()).isEqualTo(UPDATED_DOKUMENT);
        assertThat(testAnalitickaMagacinskaKartica.getSmer()).isEqualTo(UPDATED_SMER);
    }

    @Test
    @Transactional
    void putNonExistingAnalitickaMagacinskaKartica() throws Exception {
        int databaseSizeBeforeUpdate = analitickaMagacinskaKarticaRepository.findAll().size();
        analitickaMagacinskaKartica.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnalitickaMagacinskaKarticaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, analitickaMagacinskaKartica.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(analitickaMagacinskaKartica))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnalitickaMagacinskaKartica in the database
        List<AnalitickaMagacinskaKartica> analitickaMagacinskaKarticaList = analitickaMagacinskaKarticaRepository.findAll();
        assertThat(analitickaMagacinskaKarticaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAnalitickaMagacinskaKartica() throws Exception {
        int databaseSizeBeforeUpdate = analitickaMagacinskaKarticaRepository.findAll().size();
        analitickaMagacinskaKartica.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnalitickaMagacinskaKarticaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(analitickaMagacinskaKartica))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnalitickaMagacinskaKartica in the database
        List<AnalitickaMagacinskaKartica> analitickaMagacinskaKarticaList = analitickaMagacinskaKarticaRepository.findAll();
        assertThat(analitickaMagacinskaKarticaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAnalitickaMagacinskaKartica() throws Exception {
        int databaseSizeBeforeUpdate = analitickaMagacinskaKarticaRepository.findAll().size();
        analitickaMagacinskaKartica.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnalitickaMagacinskaKarticaMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(analitickaMagacinskaKartica))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AnalitickaMagacinskaKartica in the database
        List<AnalitickaMagacinskaKartica> analitickaMagacinskaKarticaList = analitickaMagacinskaKarticaRepository.findAll();
        assertThat(analitickaMagacinskaKarticaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAnalitickaMagacinskaKarticaWithPatch() throws Exception {
        // Initialize the database
        analitickaMagacinskaKarticaRepository.saveAndFlush(analitickaMagacinskaKartica);

        int databaseSizeBeforeUpdate = analitickaMagacinskaKarticaRepository.findAll().size();

        // Update the analitickaMagacinskaKartica using partial update
        AnalitickaMagacinskaKartica partialUpdatedAnalitickaMagacinskaKartica = new AnalitickaMagacinskaKartica();
        partialUpdatedAnalitickaMagacinskaKartica.setId(analitickaMagacinskaKartica.getId());

        partialUpdatedAnalitickaMagacinskaKartica
            .datumPrometa(UPDATED_DATUM_PROMETA)
            .kolicina(UPDATED_KOLICINA)
            .cena(UPDATED_CENA)
            .dokument(UPDATED_DOKUMENT)
            .smer(UPDATED_SMER);

        restAnalitickaMagacinskaKarticaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAnalitickaMagacinskaKartica.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAnalitickaMagacinskaKartica))
            )
            .andExpect(status().isOk());

        // Validate the AnalitickaMagacinskaKartica in the database
        List<AnalitickaMagacinskaKartica> analitickaMagacinskaKarticaList = analitickaMagacinskaKarticaRepository.findAll();
        assertThat(analitickaMagacinskaKarticaList).hasSize(databaseSizeBeforeUpdate);
        AnalitickaMagacinskaKartica testAnalitickaMagacinskaKartica = analitickaMagacinskaKarticaList.get(
            analitickaMagacinskaKarticaList.size() - 1
        );
        assertThat(testAnalitickaMagacinskaKartica.getDatumPrometa()).isEqualTo(UPDATED_DATUM_PROMETA);
        assertThat(testAnalitickaMagacinskaKartica.getKolicina()).isEqualTo(UPDATED_KOLICINA);
        assertThat(testAnalitickaMagacinskaKartica.getCena()).isEqualTo(UPDATED_CENA);
        assertThat(testAnalitickaMagacinskaKartica.getVrednost()).isEqualTo(DEFAULT_VREDNOST);
        assertThat(testAnalitickaMagacinskaKartica.getDokument()).isEqualTo(UPDATED_DOKUMENT);
        assertThat(testAnalitickaMagacinskaKartica.getSmer()).isEqualTo(UPDATED_SMER);
    }

    @Test
    @Transactional
    void fullUpdateAnalitickaMagacinskaKarticaWithPatch() throws Exception {
        // Initialize the database
        analitickaMagacinskaKarticaRepository.saveAndFlush(analitickaMagacinskaKartica);

        int databaseSizeBeforeUpdate = analitickaMagacinskaKarticaRepository.findAll().size();

        // Update the analitickaMagacinskaKartica using partial update
        AnalitickaMagacinskaKartica partialUpdatedAnalitickaMagacinskaKartica = new AnalitickaMagacinskaKartica();
        partialUpdatedAnalitickaMagacinskaKartica.setId(analitickaMagacinskaKartica.getId());

        partialUpdatedAnalitickaMagacinskaKartica
            .datumPrometa(UPDATED_DATUM_PROMETA)
            .kolicina(UPDATED_KOLICINA)
            .cena(UPDATED_CENA)
            .vrednost(UPDATED_VREDNOST)
            .dokument(UPDATED_DOKUMENT)
            .smer(UPDATED_SMER);

        restAnalitickaMagacinskaKarticaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAnalitickaMagacinskaKartica.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAnalitickaMagacinskaKartica))
            )
            .andExpect(status().isOk());

        // Validate the AnalitickaMagacinskaKartica in the database
        List<AnalitickaMagacinskaKartica> analitickaMagacinskaKarticaList = analitickaMagacinskaKarticaRepository.findAll();
        assertThat(analitickaMagacinskaKarticaList).hasSize(databaseSizeBeforeUpdate);
        AnalitickaMagacinskaKartica testAnalitickaMagacinskaKartica = analitickaMagacinskaKarticaList.get(
            analitickaMagacinskaKarticaList.size() - 1
        );
        assertThat(testAnalitickaMagacinskaKartica.getDatumPrometa()).isEqualTo(UPDATED_DATUM_PROMETA);
        assertThat(testAnalitickaMagacinskaKartica.getKolicina()).isEqualTo(UPDATED_KOLICINA);
        assertThat(testAnalitickaMagacinskaKartica.getCena()).isEqualTo(UPDATED_CENA);
        assertThat(testAnalitickaMagacinskaKartica.getVrednost()).isEqualTo(UPDATED_VREDNOST);
        assertThat(testAnalitickaMagacinskaKartica.getDokument()).isEqualTo(UPDATED_DOKUMENT);
        assertThat(testAnalitickaMagacinskaKartica.getSmer()).isEqualTo(UPDATED_SMER);
    }

    @Test
    @Transactional
    void patchNonExistingAnalitickaMagacinskaKartica() throws Exception {
        int databaseSizeBeforeUpdate = analitickaMagacinskaKarticaRepository.findAll().size();
        analitickaMagacinskaKartica.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnalitickaMagacinskaKarticaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, analitickaMagacinskaKartica.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(analitickaMagacinskaKartica))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnalitickaMagacinskaKartica in the database
        List<AnalitickaMagacinskaKartica> analitickaMagacinskaKarticaList = analitickaMagacinskaKarticaRepository.findAll();
        assertThat(analitickaMagacinskaKarticaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAnalitickaMagacinskaKartica() throws Exception {
        int databaseSizeBeforeUpdate = analitickaMagacinskaKarticaRepository.findAll().size();
        analitickaMagacinskaKartica.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnalitickaMagacinskaKarticaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(analitickaMagacinskaKartica))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnalitickaMagacinskaKartica in the database
        List<AnalitickaMagacinskaKartica> analitickaMagacinskaKarticaList = analitickaMagacinskaKarticaRepository.findAll();
        assertThat(analitickaMagacinskaKarticaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAnalitickaMagacinskaKartica() throws Exception {
        int databaseSizeBeforeUpdate = analitickaMagacinskaKarticaRepository.findAll().size();
        analitickaMagacinskaKartica.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnalitickaMagacinskaKarticaMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(analitickaMagacinskaKartica))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AnalitickaMagacinskaKartica in the database
        List<AnalitickaMagacinskaKartica> analitickaMagacinskaKarticaList = analitickaMagacinskaKarticaRepository.findAll();
        assertThat(analitickaMagacinskaKarticaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAnalitickaMagacinskaKartica() throws Exception {
        // Initialize the database
        analitickaMagacinskaKarticaRepository.saveAndFlush(analitickaMagacinskaKartica);

        int databaseSizeBeforeDelete = analitickaMagacinskaKarticaRepository.findAll().size();

        // Delete the analitickaMagacinskaKartica
        restAnalitickaMagacinskaKarticaMockMvc
            .perform(delete(ENTITY_API_URL_ID, analitickaMagacinskaKartica.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AnalitickaMagacinskaKartica> analitickaMagacinskaKarticaList = analitickaMagacinskaKarticaRepository.findAll();
        assertThat(analitickaMagacinskaKarticaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
