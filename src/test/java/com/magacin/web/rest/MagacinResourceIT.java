package com.magacin.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.magacin.IntegrationTest;
import com.magacin.domain.Magacin;
import com.magacin.repository.MagacinRepository;
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
 * Integration tests for the {@link MagacinResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MagacinResourceIT {

    private static final String DEFAULT_NAZIV = "AAAAAAAAAA";
    private static final String UPDATED_NAZIV = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/magacins";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MagacinRepository magacinRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMagacinMockMvc;

    private Magacin magacin;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Magacin createEntity(EntityManager em) {
        Magacin magacin = new Magacin().naziv(DEFAULT_NAZIV);
        return magacin;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Magacin createUpdatedEntity(EntityManager em) {
        Magacin magacin = new Magacin().naziv(UPDATED_NAZIV);
        return magacin;
    }

    @BeforeEach
    public void initTest() {
        magacin = createEntity(em);
    }

    @Test
    @Transactional
    void createMagacin() throws Exception {
        int databaseSizeBeforeCreate = magacinRepository.findAll().size();
        // Create the Magacin
        restMagacinMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(magacin)))
            .andExpect(status().isCreated());

        // Validate the Magacin in the database
        List<Magacin> magacinList = magacinRepository.findAll();
        assertThat(magacinList).hasSize(databaseSizeBeforeCreate + 1);
        Magacin testMagacin = magacinList.get(magacinList.size() - 1);
        assertThat(testMagacin.getNaziv()).isEqualTo(DEFAULT_NAZIV);
    }

    @Test
    @Transactional
    void createMagacinWithExistingId() throws Exception {
        // Create the Magacin with an existing ID
        magacin.setId(1L);

        int databaseSizeBeforeCreate = magacinRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMagacinMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(magacin)))
            .andExpect(status().isBadRequest());

        // Validate the Magacin in the database
        List<Magacin> magacinList = magacinRepository.findAll();
        assertThat(magacinList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMagacins() throws Exception {
        // Initialize the database
        magacinRepository.saveAndFlush(magacin);

        // Get all the magacinList
        restMagacinMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(magacin.getId().intValue())))
            .andExpect(jsonPath("$.[*].naziv").value(hasItem(DEFAULT_NAZIV)));
    }

    @Test
    @Transactional
    void getMagacin() throws Exception {
        // Initialize the database
        magacinRepository.saveAndFlush(magacin);

        // Get the magacin
        restMagacinMockMvc
            .perform(get(ENTITY_API_URL_ID, magacin.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(magacin.getId().intValue()))
            .andExpect(jsonPath("$.naziv").value(DEFAULT_NAZIV));
    }

    @Test
    @Transactional
    void getNonExistingMagacin() throws Exception {
        // Get the magacin
        restMagacinMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMagacin() throws Exception {
        // Initialize the database
        magacinRepository.saveAndFlush(magacin);

        int databaseSizeBeforeUpdate = magacinRepository.findAll().size();

        // Update the magacin
        Magacin updatedMagacin = magacinRepository.findById(magacin.getId()).get();
        // Disconnect from session so that the updates on updatedMagacin are not directly saved in db
        em.detach(updatedMagacin);
        updatedMagacin.naziv(UPDATED_NAZIV);

        restMagacinMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMagacin.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMagacin))
            )
            .andExpect(status().isOk());

        // Validate the Magacin in the database
        List<Magacin> magacinList = magacinRepository.findAll();
        assertThat(magacinList).hasSize(databaseSizeBeforeUpdate);
        Magacin testMagacin = magacinList.get(magacinList.size() - 1);
        assertThat(testMagacin.getNaziv()).isEqualTo(UPDATED_NAZIV);
    }

    @Test
    @Transactional
    void putNonExistingMagacin() throws Exception {
        int databaseSizeBeforeUpdate = magacinRepository.findAll().size();
        magacin.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMagacinMockMvc
            .perform(
                put(ENTITY_API_URL_ID, magacin.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(magacin))
            )
            .andExpect(status().isBadRequest());

        // Validate the Magacin in the database
        List<Magacin> magacinList = magacinRepository.findAll();
        assertThat(magacinList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMagacin() throws Exception {
        int databaseSizeBeforeUpdate = magacinRepository.findAll().size();
        magacin.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMagacinMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(magacin))
            )
            .andExpect(status().isBadRequest());

        // Validate the Magacin in the database
        List<Magacin> magacinList = magacinRepository.findAll();
        assertThat(magacinList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMagacin() throws Exception {
        int databaseSizeBeforeUpdate = magacinRepository.findAll().size();
        magacin.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMagacinMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(magacin)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Magacin in the database
        List<Magacin> magacinList = magacinRepository.findAll();
        assertThat(magacinList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMagacinWithPatch() throws Exception {
        // Initialize the database
        magacinRepository.saveAndFlush(magacin);

        int databaseSizeBeforeUpdate = magacinRepository.findAll().size();

        // Update the magacin using partial update
        Magacin partialUpdatedMagacin = new Magacin();
        partialUpdatedMagacin.setId(magacin.getId());

        restMagacinMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMagacin.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMagacin))
            )
            .andExpect(status().isOk());

        // Validate the Magacin in the database
        List<Magacin> magacinList = magacinRepository.findAll();
        assertThat(magacinList).hasSize(databaseSizeBeforeUpdate);
        Magacin testMagacin = magacinList.get(magacinList.size() - 1);
        assertThat(testMagacin.getNaziv()).isEqualTo(DEFAULT_NAZIV);
    }

    @Test
    @Transactional
    void fullUpdateMagacinWithPatch() throws Exception {
        // Initialize the database
        magacinRepository.saveAndFlush(magacin);

        int databaseSizeBeforeUpdate = magacinRepository.findAll().size();

        // Update the magacin using partial update
        Magacin partialUpdatedMagacin = new Magacin();
        partialUpdatedMagacin.setId(magacin.getId());

        partialUpdatedMagacin.naziv(UPDATED_NAZIV);

        restMagacinMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMagacin.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMagacin))
            )
            .andExpect(status().isOk());

        // Validate the Magacin in the database
        List<Magacin> magacinList = magacinRepository.findAll();
        assertThat(magacinList).hasSize(databaseSizeBeforeUpdate);
        Magacin testMagacin = magacinList.get(magacinList.size() - 1);
        assertThat(testMagacin.getNaziv()).isEqualTo(UPDATED_NAZIV);
    }

    @Test
    @Transactional
    void patchNonExistingMagacin() throws Exception {
        int databaseSizeBeforeUpdate = magacinRepository.findAll().size();
        magacin.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMagacinMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, magacin.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(magacin))
            )
            .andExpect(status().isBadRequest());

        // Validate the Magacin in the database
        List<Magacin> magacinList = magacinRepository.findAll();
        assertThat(magacinList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMagacin() throws Exception {
        int databaseSizeBeforeUpdate = magacinRepository.findAll().size();
        magacin.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMagacinMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(magacin))
            )
            .andExpect(status().isBadRequest());

        // Validate the Magacin in the database
        List<Magacin> magacinList = magacinRepository.findAll();
        assertThat(magacinList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMagacin() throws Exception {
        int databaseSizeBeforeUpdate = magacinRepository.findAll().size();
        magacin.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMagacinMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(magacin)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Magacin in the database
        List<Magacin> magacinList = magacinRepository.findAll();
        assertThat(magacinList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMagacin() throws Exception {
        // Initialize the database
        magacinRepository.saveAndFlush(magacin);

        int databaseSizeBeforeDelete = magacinRepository.findAll().size();

        // Delete the magacin
        restMagacinMockMvc
            .perform(delete(ENTITY_API_URL_ID, magacin.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Magacin> magacinList = magacinRepository.findAll();
        assertThat(magacinList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
