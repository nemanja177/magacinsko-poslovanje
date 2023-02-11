package com.magacin.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.magacin.IntegrationTest;
import com.magacin.domain.StavkaPopisa;
import com.magacin.repository.StavkaPopisaRepository;
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
 * Integration tests for the {@link StavkaPopisaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StavkaPopisaResourceIT {

    private static final Long DEFAULT_KOLICINA_POPISA = 1L;
    private static final Long UPDATED_KOLICINA_POPISA = 2L;

    private static final Long DEFAULT_KOLICINA_PO_KNJIGAMA = 1L;
    private static final Long UPDATED_KOLICINA_PO_KNJIGAMA = 2L;

    private static final String ENTITY_API_URL = "/api/stavka-popisas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private StavkaPopisaRepository stavkaPopisaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStavkaPopisaMockMvc;

    private StavkaPopisa stavkaPopisa;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StavkaPopisa createEntity(EntityManager em) {
        StavkaPopisa stavkaPopisa = new StavkaPopisa()
            .kolicinaPopisa(DEFAULT_KOLICINA_POPISA)
            .kolicinaPoKnjigama(DEFAULT_KOLICINA_PO_KNJIGAMA);
        return stavkaPopisa;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StavkaPopisa createUpdatedEntity(EntityManager em) {
        StavkaPopisa stavkaPopisa = new StavkaPopisa()
            .kolicinaPopisa(UPDATED_KOLICINA_POPISA)
            .kolicinaPoKnjigama(UPDATED_KOLICINA_PO_KNJIGAMA);
        return stavkaPopisa;
    }

    @BeforeEach
    public void initTest() {
        stavkaPopisa = createEntity(em);
    }

    @Test
    @Transactional
    void createStavkaPopisa() throws Exception {
        int databaseSizeBeforeCreate = stavkaPopisaRepository.findAll().size();
        // Create the StavkaPopisa
        restStavkaPopisaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stavkaPopisa)))
            .andExpect(status().isCreated());

        // Validate the StavkaPopisa in the database
        List<StavkaPopisa> stavkaPopisaList = stavkaPopisaRepository.findAll();
        assertThat(stavkaPopisaList).hasSize(databaseSizeBeforeCreate + 1);
        StavkaPopisa testStavkaPopisa = stavkaPopisaList.get(stavkaPopisaList.size() - 1);
        assertThat(testStavkaPopisa.getKolicinaPopisa()).isEqualTo(DEFAULT_KOLICINA_POPISA);
        assertThat(testStavkaPopisa.getKolicinaPoKnjigama()).isEqualTo(DEFAULT_KOLICINA_PO_KNJIGAMA);
    }

    @Test
    @Transactional
    void createStavkaPopisaWithExistingId() throws Exception {
        // Create the StavkaPopisa with an existing ID
        stavkaPopisa.setId(1L);

        int databaseSizeBeforeCreate = stavkaPopisaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStavkaPopisaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stavkaPopisa)))
            .andExpect(status().isBadRequest());

        // Validate the StavkaPopisa in the database
        List<StavkaPopisa> stavkaPopisaList = stavkaPopisaRepository.findAll();
        assertThat(stavkaPopisaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllStavkaPopisas() throws Exception {
        // Initialize the database
        stavkaPopisaRepository.saveAndFlush(stavkaPopisa);

        // Get all the stavkaPopisaList
        restStavkaPopisaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stavkaPopisa.getId().intValue())))
            .andExpect(jsonPath("$.[*].kolicinaPopisa").value(hasItem(DEFAULT_KOLICINA_POPISA.intValue())))
            .andExpect(jsonPath("$.[*].kolicinaPoKnjigama").value(hasItem(DEFAULT_KOLICINA_PO_KNJIGAMA.intValue())));
    }

    @Test
    @Transactional
    void getStavkaPopisa() throws Exception {
        // Initialize the database
        stavkaPopisaRepository.saveAndFlush(stavkaPopisa);

        // Get the stavkaPopisa
        restStavkaPopisaMockMvc
            .perform(get(ENTITY_API_URL_ID, stavkaPopisa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(stavkaPopisa.getId().intValue()))
            .andExpect(jsonPath("$.kolicinaPopisa").value(DEFAULT_KOLICINA_POPISA.intValue()))
            .andExpect(jsonPath("$.kolicinaPoKnjigama").value(DEFAULT_KOLICINA_PO_KNJIGAMA.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingStavkaPopisa() throws Exception {
        // Get the stavkaPopisa
        restStavkaPopisaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingStavkaPopisa() throws Exception {
        // Initialize the database
        stavkaPopisaRepository.saveAndFlush(stavkaPopisa);

        int databaseSizeBeforeUpdate = stavkaPopisaRepository.findAll().size();

        // Update the stavkaPopisa
        StavkaPopisa updatedStavkaPopisa = stavkaPopisaRepository.findById(stavkaPopisa.getId()).get();
        // Disconnect from session so that the updates on updatedStavkaPopisa are not directly saved in db
        em.detach(updatedStavkaPopisa);
        updatedStavkaPopisa.kolicinaPopisa(UPDATED_KOLICINA_POPISA).kolicinaPoKnjigama(UPDATED_KOLICINA_PO_KNJIGAMA);

        restStavkaPopisaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedStavkaPopisa.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedStavkaPopisa))
            )
            .andExpect(status().isOk());

        // Validate the StavkaPopisa in the database
        List<StavkaPopisa> stavkaPopisaList = stavkaPopisaRepository.findAll();
        assertThat(stavkaPopisaList).hasSize(databaseSizeBeforeUpdate);
        StavkaPopisa testStavkaPopisa = stavkaPopisaList.get(stavkaPopisaList.size() - 1);
        assertThat(testStavkaPopisa.getKolicinaPopisa()).isEqualTo(UPDATED_KOLICINA_POPISA);
        assertThat(testStavkaPopisa.getKolicinaPoKnjigama()).isEqualTo(UPDATED_KOLICINA_PO_KNJIGAMA);
    }

    @Test
    @Transactional
    void putNonExistingStavkaPopisa() throws Exception {
        int databaseSizeBeforeUpdate = stavkaPopisaRepository.findAll().size();
        stavkaPopisa.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStavkaPopisaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, stavkaPopisa.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(stavkaPopisa))
            )
            .andExpect(status().isBadRequest());

        // Validate the StavkaPopisa in the database
        List<StavkaPopisa> stavkaPopisaList = stavkaPopisaRepository.findAll();
        assertThat(stavkaPopisaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStavkaPopisa() throws Exception {
        int databaseSizeBeforeUpdate = stavkaPopisaRepository.findAll().size();
        stavkaPopisa.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStavkaPopisaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(stavkaPopisa))
            )
            .andExpect(status().isBadRequest());

        // Validate the StavkaPopisa in the database
        List<StavkaPopisa> stavkaPopisaList = stavkaPopisaRepository.findAll();
        assertThat(stavkaPopisaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStavkaPopisa() throws Exception {
        int databaseSizeBeforeUpdate = stavkaPopisaRepository.findAll().size();
        stavkaPopisa.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStavkaPopisaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stavkaPopisa)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the StavkaPopisa in the database
        List<StavkaPopisa> stavkaPopisaList = stavkaPopisaRepository.findAll();
        assertThat(stavkaPopisaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStavkaPopisaWithPatch() throws Exception {
        // Initialize the database
        stavkaPopisaRepository.saveAndFlush(stavkaPopisa);

        int databaseSizeBeforeUpdate = stavkaPopisaRepository.findAll().size();

        // Update the stavkaPopisa using partial update
        StavkaPopisa partialUpdatedStavkaPopisa = new StavkaPopisa();
        partialUpdatedStavkaPopisa.setId(stavkaPopisa.getId());

        restStavkaPopisaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStavkaPopisa.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStavkaPopisa))
            )
            .andExpect(status().isOk());

        // Validate the StavkaPopisa in the database
        List<StavkaPopisa> stavkaPopisaList = stavkaPopisaRepository.findAll();
        assertThat(stavkaPopisaList).hasSize(databaseSizeBeforeUpdate);
        StavkaPopisa testStavkaPopisa = stavkaPopisaList.get(stavkaPopisaList.size() - 1);
        assertThat(testStavkaPopisa.getKolicinaPopisa()).isEqualTo(DEFAULT_KOLICINA_POPISA);
        assertThat(testStavkaPopisa.getKolicinaPoKnjigama()).isEqualTo(DEFAULT_KOLICINA_PO_KNJIGAMA);
    }

    @Test
    @Transactional
    void fullUpdateStavkaPopisaWithPatch() throws Exception {
        // Initialize the database
        stavkaPopisaRepository.saveAndFlush(stavkaPopisa);

        int databaseSizeBeforeUpdate = stavkaPopisaRepository.findAll().size();

        // Update the stavkaPopisa using partial update
        StavkaPopisa partialUpdatedStavkaPopisa = new StavkaPopisa();
        partialUpdatedStavkaPopisa.setId(stavkaPopisa.getId());

        partialUpdatedStavkaPopisa.kolicinaPopisa(UPDATED_KOLICINA_POPISA).kolicinaPoKnjigama(UPDATED_KOLICINA_PO_KNJIGAMA);

        restStavkaPopisaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStavkaPopisa.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStavkaPopisa))
            )
            .andExpect(status().isOk());

        // Validate the StavkaPopisa in the database
        List<StavkaPopisa> stavkaPopisaList = stavkaPopisaRepository.findAll();
        assertThat(stavkaPopisaList).hasSize(databaseSizeBeforeUpdate);
        StavkaPopisa testStavkaPopisa = stavkaPopisaList.get(stavkaPopisaList.size() - 1);
        assertThat(testStavkaPopisa.getKolicinaPopisa()).isEqualTo(UPDATED_KOLICINA_POPISA);
        assertThat(testStavkaPopisa.getKolicinaPoKnjigama()).isEqualTo(UPDATED_KOLICINA_PO_KNJIGAMA);
    }

    @Test
    @Transactional
    void patchNonExistingStavkaPopisa() throws Exception {
        int databaseSizeBeforeUpdate = stavkaPopisaRepository.findAll().size();
        stavkaPopisa.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStavkaPopisaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, stavkaPopisa.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(stavkaPopisa))
            )
            .andExpect(status().isBadRequest());

        // Validate the StavkaPopisa in the database
        List<StavkaPopisa> stavkaPopisaList = stavkaPopisaRepository.findAll();
        assertThat(stavkaPopisaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStavkaPopisa() throws Exception {
        int databaseSizeBeforeUpdate = stavkaPopisaRepository.findAll().size();
        stavkaPopisa.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStavkaPopisaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(stavkaPopisa))
            )
            .andExpect(status().isBadRequest());

        // Validate the StavkaPopisa in the database
        List<StavkaPopisa> stavkaPopisaList = stavkaPopisaRepository.findAll();
        assertThat(stavkaPopisaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStavkaPopisa() throws Exception {
        int databaseSizeBeforeUpdate = stavkaPopisaRepository.findAll().size();
        stavkaPopisa.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStavkaPopisaMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(stavkaPopisa))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the StavkaPopisa in the database
        List<StavkaPopisa> stavkaPopisaList = stavkaPopisaRepository.findAll();
        assertThat(stavkaPopisaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStavkaPopisa() throws Exception {
        // Initialize the database
        stavkaPopisaRepository.saveAndFlush(stavkaPopisa);

        int databaseSizeBeforeDelete = stavkaPopisaRepository.findAll().size();

        // Delete the stavkaPopisa
        restStavkaPopisaMockMvc
            .perform(delete(ENTITY_API_URL_ID, stavkaPopisa.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StavkaPopisa> stavkaPopisaList = stavkaPopisaRepository.findAll();
        assertThat(stavkaPopisaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
