package com.magacin.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.magacin.IntegrationTest;
import com.magacin.domain.Popis;
import com.magacin.repository.PopisRepository;
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
 * Integration tests for the {@link PopisResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PopisResourceIT {

    private static final LocalDate DEFAULT_DATUM_POPISA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUM_POPISA = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_BROJ_POPISA = 1L;
    private static final Long UPDATED_BROJ_POPISA = 2L;

    private static final Boolean DEFAULT_STATUS_POPISA = false;
    private static final Boolean UPDATED_STATUS_POPISA = true;

    private static final String ENTITY_API_URL = "/api/popis";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PopisRepository popisRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPopisMockMvc;

    private Popis popis;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Popis createEntity(EntityManager em) {
        Popis popis = new Popis().datumPopisa(DEFAULT_DATUM_POPISA).brojPopisa(DEFAULT_BROJ_POPISA).statusPopisa(DEFAULT_STATUS_POPISA);
        return popis;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Popis createUpdatedEntity(EntityManager em) {
        Popis popis = new Popis().datumPopisa(UPDATED_DATUM_POPISA).brojPopisa(UPDATED_BROJ_POPISA).statusPopisa(UPDATED_STATUS_POPISA);
        return popis;
    }

    @BeforeEach
    public void initTest() {
        popis = createEntity(em);
    }

    @Test
    @Transactional
    void createPopis() throws Exception {
        int databaseSizeBeforeCreate = popisRepository.findAll().size();
        // Create the Popis
        restPopisMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(popis)))
            .andExpect(status().isCreated());

        // Validate the Popis in the database
        List<Popis> popisList = popisRepository.findAll();
        assertThat(popisList).hasSize(databaseSizeBeforeCreate + 1);
        Popis testPopis = popisList.get(popisList.size() - 1);
        assertThat(testPopis.getDatumPopisa()).isEqualTo(DEFAULT_DATUM_POPISA);
        assertThat(testPopis.getBrojPopisa()).isEqualTo(DEFAULT_BROJ_POPISA);
        assertThat(testPopis.getStatusPopisa()).isEqualTo(DEFAULT_STATUS_POPISA);
    }

    @Test
    @Transactional
    void createPopisWithExistingId() throws Exception {
        // Create the Popis with an existing ID
        popis.setId(1L);

        int databaseSizeBeforeCreate = popisRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPopisMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(popis)))
            .andExpect(status().isBadRequest());

        // Validate the Popis in the database
        List<Popis> popisList = popisRepository.findAll();
        assertThat(popisList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPopis() throws Exception {
        // Initialize the database
        popisRepository.saveAndFlush(popis);

        // Get all the popisList
        restPopisMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(popis.getId().intValue())))
            .andExpect(jsonPath("$.[*].datumPopisa").value(hasItem(DEFAULT_DATUM_POPISA.toString())))
            .andExpect(jsonPath("$.[*].brojPopisa").value(hasItem(DEFAULT_BROJ_POPISA.intValue())))
            .andExpect(jsonPath("$.[*].statusPopisa").value(hasItem(DEFAULT_STATUS_POPISA.booleanValue())));
    }

    @Test
    @Transactional
    void getPopis() throws Exception {
        // Initialize the database
        popisRepository.saveAndFlush(popis);

        // Get the popis
        restPopisMockMvc
            .perform(get(ENTITY_API_URL_ID, popis.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(popis.getId().intValue()))
            .andExpect(jsonPath("$.datumPopisa").value(DEFAULT_DATUM_POPISA.toString()))
            .andExpect(jsonPath("$.brojPopisa").value(DEFAULT_BROJ_POPISA.intValue()))
            .andExpect(jsonPath("$.statusPopisa").value(DEFAULT_STATUS_POPISA.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingPopis() throws Exception {
        // Get the popis
        restPopisMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPopis() throws Exception {
        // Initialize the database
        popisRepository.saveAndFlush(popis);

        int databaseSizeBeforeUpdate = popisRepository.findAll().size();

        // Update the popis
        Popis updatedPopis = popisRepository.findById(popis.getId()).get();
        // Disconnect from session so that the updates on updatedPopis are not directly saved in db
        em.detach(updatedPopis);
        updatedPopis.datumPopisa(UPDATED_DATUM_POPISA).brojPopisa(UPDATED_BROJ_POPISA).statusPopisa(UPDATED_STATUS_POPISA);

        restPopisMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPopis.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPopis))
            )
            .andExpect(status().isOk());

        // Validate the Popis in the database
        List<Popis> popisList = popisRepository.findAll();
        assertThat(popisList).hasSize(databaseSizeBeforeUpdate);
        Popis testPopis = popisList.get(popisList.size() - 1);
        assertThat(testPopis.getDatumPopisa()).isEqualTo(UPDATED_DATUM_POPISA);
        assertThat(testPopis.getBrojPopisa()).isEqualTo(UPDATED_BROJ_POPISA);
        assertThat(testPopis.getStatusPopisa()).isEqualTo(UPDATED_STATUS_POPISA);
    }

    @Test
    @Transactional
    void putNonExistingPopis() throws Exception {
        int databaseSizeBeforeUpdate = popisRepository.findAll().size();
        popis.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPopisMockMvc
            .perform(
                put(ENTITY_API_URL_ID, popis.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(popis))
            )
            .andExpect(status().isBadRequest());

        // Validate the Popis in the database
        List<Popis> popisList = popisRepository.findAll();
        assertThat(popisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPopis() throws Exception {
        int databaseSizeBeforeUpdate = popisRepository.findAll().size();
        popis.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPopisMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(popis))
            )
            .andExpect(status().isBadRequest());

        // Validate the Popis in the database
        List<Popis> popisList = popisRepository.findAll();
        assertThat(popisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPopis() throws Exception {
        int databaseSizeBeforeUpdate = popisRepository.findAll().size();
        popis.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPopisMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(popis)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Popis in the database
        List<Popis> popisList = popisRepository.findAll();
        assertThat(popisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePopisWithPatch() throws Exception {
        // Initialize the database
        popisRepository.saveAndFlush(popis);

        int databaseSizeBeforeUpdate = popisRepository.findAll().size();

        // Update the popis using partial update
        Popis partialUpdatedPopis = new Popis();
        partialUpdatedPopis.setId(popis.getId());

        partialUpdatedPopis.brojPopisa(UPDATED_BROJ_POPISA);

        restPopisMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPopis.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPopis))
            )
            .andExpect(status().isOk());

        // Validate the Popis in the database
        List<Popis> popisList = popisRepository.findAll();
        assertThat(popisList).hasSize(databaseSizeBeforeUpdate);
        Popis testPopis = popisList.get(popisList.size() - 1);
        assertThat(testPopis.getDatumPopisa()).isEqualTo(DEFAULT_DATUM_POPISA);
        assertThat(testPopis.getBrojPopisa()).isEqualTo(UPDATED_BROJ_POPISA);
        assertThat(testPopis.getStatusPopisa()).isEqualTo(DEFAULT_STATUS_POPISA);
    }

    @Test
    @Transactional
    void fullUpdatePopisWithPatch() throws Exception {
        // Initialize the database
        popisRepository.saveAndFlush(popis);

        int databaseSizeBeforeUpdate = popisRepository.findAll().size();

        // Update the popis using partial update
        Popis partialUpdatedPopis = new Popis();
        partialUpdatedPopis.setId(popis.getId());

        partialUpdatedPopis.datumPopisa(UPDATED_DATUM_POPISA).brojPopisa(UPDATED_BROJ_POPISA).statusPopisa(UPDATED_STATUS_POPISA);

        restPopisMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPopis.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPopis))
            )
            .andExpect(status().isOk());

        // Validate the Popis in the database
        List<Popis> popisList = popisRepository.findAll();
        assertThat(popisList).hasSize(databaseSizeBeforeUpdate);
        Popis testPopis = popisList.get(popisList.size() - 1);
        assertThat(testPopis.getDatumPopisa()).isEqualTo(UPDATED_DATUM_POPISA);
        assertThat(testPopis.getBrojPopisa()).isEqualTo(UPDATED_BROJ_POPISA);
        assertThat(testPopis.getStatusPopisa()).isEqualTo(UPDATED_STATUS_POPISA);
    }

    @Test
    @Transactional
    void patchNonExistingPopis() throws Exception {
        int databaseSizeBeforeUpdate = popisRepository.findAll().size();
        popis.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPopisMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, popis.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(popis))
            )
            .andExpect(status().isBadRequest());

        // Validate the Popis in the database
        List<Popis> popisList = popisRepository.findAll();
        assertThat(popisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPopis() throws Exception {
        int databaseSizeBeforeUpdate = popisRepository.findAll().size();
        popis.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPopisMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(popis))
            )
            .andExpect(status().isBadRequest());

        // Validate the Popis in the database
        List<Popis> popisList = popisRepository.findAll();
        assertThat(popisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPopis() throws Exception {
        int databaseSizeBeforeUpdate = popisRepository.findAll().size();
        popis.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPopisMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(popis)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Popis in the database
        List<Popis> popisList = popisRepository.findAll();
        assertThat(popisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePopis() throws Exception {
        // Initialize the database
        popisRepository.saveAndFlush(popis);

        int databaseSizeBeforeDelete = popisRepository.findAll().size();

        // Delete the popis
        restPopisMockMvc
            .perform(delete(ENTITY_API_URL_ID, popis.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Popis> popisList = popisRepository.findAll();
        assertThat(popisList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
