package com.magacin.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.magacin.IntegrationTest;
import com.magacin.domain.StavkaPrometnogDokumenta;
import com.magacin.repository.StavkaPrometnogDokumentaRepository;
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
 * Integration tests for the {@link StavkaPrometnogDokumentaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StavkaPrometnogDokumentaResourceIT {

    private static final Long DEFAULT_KOLICINA = 1L;
    private static final Long UPDATED_KOLICINA = 2L;

    private static final Long DEFAULT_CENA = 1L;
    private static final Long UPDATED_CENA = 2L;

    private static final Long DEFAULT_VREDNOST = 1L;
    private static final Long UPDATED_VREDNOST = 2L;

    private static final String ENTITY_API_URL = "/api/stavka-prometnog-dokumentas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private StavkaPrometnogDokumentaRepository stavkaPrometnogDokumentaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStavkaPrometnogDokumentaMockMvc;

    private StavkaPrometnogDokumenta stavkaPrometnogDokumenta;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StavkaPrometnogDokumenta createEntity(EntityManager em) {
        StavkaPrometnogDokumenta stavkaPrometnogDokumenta = new StavkaPrometnogDokumenta()
            .kolicina(DEFAULT_KOLICINA)
            .cena(DEFAULT_CENA)
            .vrednost(DEFAULT_VREDNOST);
        return stavkaPrometnogDokumenta;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StavkaPrometnogDokumenta createUpdatedEntity(EntityManager em) {
        StavkaPrometnogDokumenta stavkaPrometnogDokumenta = new StavkaPrometnogDokumenta()
            .kolicina(UPDATED_KOLICINA)
            .cena(UPDATED_CENA)
            .vrednost(UPDATED_VREDNOST);
        return stavkaPrometnogDokumenta;
    }

    @BeforeEach
    public void initTest() {
        stavkaPrometnogDokumenta = createEntity(em);
    }

    @Test
    @Transactional
    void createStavkaPrometnogDokumenta() throws Exception {
        int databaseSizeBeforeCreate = stavkaPrometnogDokumentaRepository.findAll().size();
        // Create the StavkaPrometnogDokumenta
        restStavkaPrometnogDokumentaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(stavkaPrometnogDokumenta))
            )
            .andExpect(status().isCreated());

        // Validate the StavkaPrometnogDokumenta in the database
        List<StavkaPrometnogDokumenta> stavkaPrometnogDokumentaList = stavkaPrometnogDokumentaRepository.findAll();
        assertThat(stavkaPrometnogDokumentaList).hasSize(databaseSizeBeforeCreate + 1);
        StavkaPrometnogDokumenta testStavkaPrometnogDokumenta = stavkaPrometnogDokumentaList.get(stavkaPrometnogDokumentaList.size() - 1);
        assertThat(testStavkaPrometnogDokumenta.getKolicina()).isEqualTo(DEFAULT_KOLICINA);
        assertThat(testStavkaPrometnogDokumenta.getCena()).isEqualTo(DEFAULT_CENA);
        assertThat(testStavkaPrometnogDokumenta.getVrednost()).isEqualTo(DEFAULT_VREDNOST);
    }

    @Test
    @Transactional
    void createStavkaPrometnogDokumentaWithExistingId() throws Exception {
        // Create the StavkaPrometnogDokumenta with an existing ID
        stavkaPrometnogDokumenta.setId(1L);

        int databaseSizeBeforeCreate = stavkaPrometnogDokumentaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStavkaPrometnogDokumentaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(stavkaPrometnogDokumenta))
            )
            .andExpect(status().isBadRequest());

        // Validate the StavkaPrometnogDokumenta in the database
        List<StavkaPrometnogDokumenta> stavkaPrometnogDokumentaList = stavkaPrometnogDokumentaRepository.findAll();
        assertThat(stavkaPrometnogDokumentaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllStavkaPrometnogDokumentas() throws Exception {
        // Initialize the database
        stavkaPrometnogDokumentaRepository.saveAndFlush(stavkaPrometnogDokumenta);

        // Get all the stavkaPrometnogDokumentaList
        restStavkaPrometnogDokumentaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stavkaPrometnogDokumenta.getId().intValue())))
            .andExpect(jsonPath("$.[*].kolicina").value(hasItem(DEFAULT_KOLICINA.intValue())))
            .andExpect(jsonPath("$.[*].cena").value(hasItem(DEFAULT_CENA.intValue())))
            .andExpect(jsonPath("$.[*].vrednost").value(hasItem(DEFAULT_VREDNOST.intValue())));
    }

    @Test
    @Transactional
    void getStavkaPrometnogDokumenta() throws Exception {
        // Initialize the database
        stavkaPrometnogDokumentaRepository.saveAndFlush(stavkaPrometnogDokumenta);

        // Get the stavkaPrometnogDokumenta
        restStavkaPrometnogDokumentaMockMvc
            .perform(get(ENTITY_API_URL_ID, stavkaPrometnogDokumenta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(stavkaPrometnogDokumenta.getId().intValue()))
            .andExpect(jsonPath("$.kolicina").value(DEFAULT_KOLICINA.intValue()))
            .andExpect(jsonPath("$.cena").value(DEFAULT_CENA.intValue()))
            .andExpect(jsonPath("$.vrednost").value(DEFAULT_VREDNOST.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingStavkaPrometnogDokumenta() throws Exception {
        // Get the stavkaPrometnogDokumenta
        restStavkaPrometnogDokumentaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingStavkaPrometnogDokumenta() throws Exception {
        // Initialize the database
        stavkaPrometnogDokumentaRepository.saveAndFlush(stavkaPrometnogDokumenta);

        int databaseSizeBeforeUpdate = stavkaPrometnogDokumentaRepository.findAll().size();

        // Update the stavkaPrometnogDokumenta
        StavkaPrometnogDokumenta updatedStavkaPrometnogDokumenta = stavkaPrometnogDokumentaRepository
            .findById(stavkaPrometnogDokumenta.getId())
            .get();
        // Disconnect from session so that the updates on updatedStavkaPrometnogDokumenta are not directly saved in db
        em.detach(updatedStavkaPrometnogDokumenta);
        updatedStavkaPrometnogDokumenta.kolicina(UPDATED_KOLICINA).cena(UPDATED_CENA).vrednost(UPDATED_VREDNOST);

        restStavkaPrometnogDokumentaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedStavkaPrometnogDokumenta.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedStavkaPrometnogDokumenta))
            )
            .andExpect(status().isOk());

        // Validate the StavkaPrometnogDokumenta in the database
        List<StavkaPrometnogDokumenta> stavkaPrometnogDokumentaList = stavkaPrometnogDokumentaRepository.findAll();
        assertThat(stavkaPrometnogDokumentaList).hasSize(databaseSizeBeforeUpdate);
        StavkaPrometnogDokumenta testStavkaPrometnogDokumenta = stavkaPrometnogDokumentaList.get(stavkaPrometnogDokumentaList.size() - 1);
        assertThat(testStavkaPrometnogDokumenta.getKolicina()).isEqualTo(UPDATED_KOLICINA);
        assertThat(testStavkaPrometnogDokumenta.getCena()).isEqualTo(UPDATED_CENA);
        assertThat(testStavkaPrometnogDokumenta.getVrednost()).isEqualTo(UPDATED_VREDNOST);
    }

    @Test
    @Transactional
    void putNonExistingStavkaPrometnogDokumenta() throws Exception {
        int databaseSizeBeforeUpdate = stavkaPrometnogDokumentaRepository.findAll().size();
        stavkaPrometnogDokumenta.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStavkaPrometnogDokumentaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, stavkaPrometnogDokumenta.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(stavkaPrometnogDokumenta))
            )
            .andExpect(status().isBadRequest());

        // Validate the StavkaPrometnogDokumenta in the database
        List<StavkaPrometnogDokumenta> stavkaPrometnogDokumentaList = stavkaPrometnogDokumentaRepository.findAll();
        assertThat(stavkaPrometnogDokumentaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStavkaPrometnogDokumenta() throws Exception {
        int databaseSizeBeforeUpdate = stavkaPrometnogDokumentaRepository.findAll().size();
        stavkaPrometnogDokumenta.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStavkaPrometnogDokumentaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(stavkaPrometnogDokumenta))
            )
            .andExpect(status().isBadRequest());

        // Validate the StavkaPrometnogDokumenta in the database
        List<StavkaPrometnogDokumenta> stavkaPrometnogDokumentaList = stavkaPrometnogDokumentaRepository.findAll();
        assertThat(stavkaPrometnogDokumentaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStavkaPrometnogDokumenta() throws Exception {
        int databaseSizeBeforeUpdate = stavkaPrometnogDokumentaRepository.findAll().size();
        stavkaPrometnogDokumenta.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStavkaPrometnogDokumentaMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(stavkaPrometnogDokumenta))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the StavkaPrometnogDokumenta in the database
        List<StavkaPrometnogDokumenta> stavkaPrometnogDokumentaList = stavkaPrometnogDokumentaRepository.findAll();
        assertThat(stavkaPrometnogDokumentaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStavkaPrometnogDokumentaWithPatch() throws Exception {
        // Initialize the database
        stavkaPrometnogDokumentaRepository.saveAndFlush(stavkaPrometnogDokumenta);

        int databaseSizeBeforeUpdate = stavkaPrometnogDokumentaRepository.findAll().size();

        // Update the stavkaPrometnogDokumenta using partial update
        StavkaPrometnogDokumenta partialUpdatedStavkaPrometnogDokumenta = new StavkaPrometnogDokumenta();
        partialUpdatedStavkaPrometnogDokumenta.setId(stavkaPrometnogDokumenta.getId());

        partialUpdatedStavkaPrometnogDokumenta.cena(UPDATED_CENA).vrednost(UPDATED_VREDNOST);

        restStavkaPrometnogDokumentaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStavkaPrometnogDokumenta.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStavkaPrometnogDokumenta))
            )
            .andExpect(status().isOk());

        // Validate the StavkaPrometnogDokumenta in the database
        List<StavkaPrometnogDokumenta> stavkaPrometnogDokumentaList = stavkaPrometnogDokumentaRepository.findAll();
        assertThat(stavkaPrometnogDokumentaList).hasSize(databaseSizeBeforeUpdate);
        StavkaPrometnogDokumenta testStavkaPrometnogDokumenta = stavkaPrometnogDokumentaList.get(stavkaPrometnogDokumentaList.size() - 1);
        assertThat(testStavkaPrometnogDokumenta.getKolicina()).isEqualTo(DEFAULT_KOLICINA);
        assertThat(testStavkaPrometnogDokumenta.getCena()).isEqualTo(UPDATED_CENA);
        assertThat(testStavkaPrometnogDokumenta.getVrednost()).isEqualTo(UPDATED_VREDNOST);
    }

    @Test
    @Transactional
    void fullUpdateStavkaPrometnogDokumentaWithPatch() throws Exception {
        // Initialize the database
        stavkaPrometnogDokumentaRepository.saveAndFlush(stavkaPrometnogDokumenta);

        int databaseSizeBeforeUpdate = stavkaPrometnogDokumentaRepository.findAll().size();

        // Update the stavkaPrometnogDokumenta using partial update
        StavkaPrometnogDokumenta partialUpdatedStavkaPrometnogDokumenta = new StavkaPrometnogDokumenta();
        partialUpdatedStavkaPrometnogDokumenta.setId(stavkaPrometnogDokumenta.getId());

        partialUpdatedStavkaPrometnogDokumenta.kolicina(UPDATED_KOLICINA).cena(UPDATED_CENA).vrednost(UPDATED_VREDNOST);

        restStavkaPrometnogDokumentaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStavkaPrometnogDokumenta.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStavkaPrometnogDokumenta))
            )
            .andExpect(status().isOk());

        // Validate the StavkaPrometnogDokumenta in the database
        List<StavkaPrometnogDokumenta> stavkaPrometnogDokumentaList = stavkaPrometnogDokumentaRepository.findAll();
        assertThat(stavkaPrometnogDokumentaList).hasSize(databaseSizeBeforeUpdate);
        StavkaPrometnogDokumenta testStavkaPrometnogDokumenta = stavkaPrometnogDokumentaList.get(stavkaPrometnogDokumentaList.size() - 1);
        assertThat(testStavkaPrometnogDokumenta.getKolicina()).isEqualTo(UPDATED_KOLICINA);
        assertThat(testStavkaPrometnogDokumenta.getCena()).isEqualTo(UPDATED_CENA);
        assertThat(testStavkaPrometnogDokumenta.getVrednost()).isEqualTo(UPDATED_VREDNOST);
    }

    @Test
    @Transactional
    void patchNonExistingStavkaPrometnogDokumenta() throws Exception {
        int databaseSizeBeforeUpdate = stavkaPrometnogDokumentaRepository.findAll().size();
        stavkaPrometnogDokumenta.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStavkaPrometnogDokumentaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, stavkaPrometnogDokumenta.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(stavkaPrometnogDokumenta))
            )
            .andExpect(status().isBadRequest());

        // Validate the StavkaPrometnogDokumenta in the database
        List<StavkaPrometnogDokumenta> stavkaPrometnogDokumentaList = stavkaPrometnogDokumentaRepository.findAll();
        assertThat(stavkaPrometnogDokumentaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStavkaPrometnogDokumenta() throws Exception {
        int databaseSizeBeforeUpdate = stavkaPrometnogDokumentaRepository.findAll().size();
        stavkaPrometnogDokumenta.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStavkaPrometnogDokumentaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(stavkaPrometnogDokumenta))
            )
            .andExpect(status().isBadRequest());

        // Validate the StavkaPrometnogDokumenta in the database
        List<StavkaPrometnogDokumenta> stavkaPrometnogDokumentaList = stavkaPrometnogDokumentaRepository.findAll();
        assertThat(stavkaPrometnogDokumentaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStavkaPrometnogDokumenta() throws Exception {
        int databaseSizeBeforeUpdate = stavkaPrometnogDokumentaRepository.findAll().size();
        stavkaPrometnogDokumenta.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStavkaPrometnogDokumentaMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(stavkaPrometnogDokumenta))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the StavkaPrometnogDokumenta in the database
        List<StavkaPrometnogDokumenta> stavkaPrometnogDokumentaList = stavkaPrometnogDokumentaRepository.findAll();
        assertThat(stavkaPrometnogDokumentaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStavkaPrometnogDokumenta() throws Exception {
        // Initialize the database
        stavkaPrometnogDokumentaRepository.saveAndFlush(stavkaPrometnogDokumenta);

        int databaseSizeBeforeDelete = stavkaPrometnogDokumentaRepository.findAll().size();

        // Delete the stavkaPrometnogDokumenta
        restStavkaPrometnogDokumentaMockMvc
            .perform(delete(ENTITY_API_URL_ID, stavkaPrometnogDokumenta.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StavkaPrometnogDokumenta> stavkaPrometnogDokumentaList = stavkaPrometnogDokumentaRepository.findAll();
        assertThat(stavkaPrometnogDokumentaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
