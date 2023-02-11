package com.magacin.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.magacin.IntegrationTest;
import com.magacin.domain.PrometniDokument;
import com.magacin.repository.PrometniDokumentRepository;
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
 * Integration tests for the {@link PrometniDokumentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PrometniDokumentResourceIT {

    private static final Long DEFAULT_BROJ_DOKUMENATA = 1L;
    private static final Long UPDATED_BROJ_DOKUMENATA = 2L;

    private static final LocalDate DEFAULT_DATUM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUM = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_VRSTA = "AAAAAAAAAA";
    private static final String UPDATED_VRSTA = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/prometni-dokuments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PrometniDokumentRepository prometniDokumentRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPrometniDokumentMockMvc;

    private PrometniDokument prometniDokument;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PrometniDokument createEntity(EntityManager em) {
        PrometniDokument prometniDokument = new PrometniDokument()
            .brojDokumenata(DEFAULT_BROJ_DOKUMENATA)
            .datum(DEFAULT_DATUM)
            .vrsta(DEFAULT_VRSTA)
            .status(DEFAULT_STATUS);
        return prometniDokument;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PrometniDokument createUpdatedEntity(EntityManager em) {
        PrometniDokument prometniDokument = new PrometniDokument()
            .brojDokumenata(UPDATED_BROJ_DOKUMENATA)
            .datum(UPDATED_DATUM)
            .vrsta(UPDATED_VRSTA)
            .status(UPDATED_STATUS);
        return prometniDokument;
    }

    @BeforeEach
    public void initTest() {
        prometniDokument = createEntity(em);
    }

    @Test
    @Transactional
    void createPrometniDokument() throws Exception {
        int databaseSizeBeforeCreate = prometniDokumentRepository.findAll().size();
        // Create the PrometniDokument
        restPrometniDokumentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(prometniDokument))
            )
            .andExpect(status().isCreated());

        // Validate the PrometniDokument in the database
        List<PrometniDokument> prometniDokumentList = prometniDokumentRepository.findAll();
        assertThat(prometniDokumentList).hasSize(databaseSizeBeforeCreate + 1);
        PrometniDokument testPrometniDokument = prometniDokumentList.get(prometniDokumentList.size() - 1);
        assertThat(testPrometniDokument.getBrojDokumenata()).isEqualTo(DEFAULT_BROJ_DOKUMENATA);
        assertThat(testPrometniDokument.getDatum()).isEqualTo(DEFAULT_DATUM);
        assertThat(testPrometniDokument.getVrsta()).isEqualTo(DEFAULT_VRSTA);
        assertThat(testPrometniDokument.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void createPrometniDokumentWithExistingId() throws Exception {
        // Create the PrometniDokument with an existing ID
        prometniDokument.setId(1L);

        int databaseSizeBeforeCreate = prometniDokumentRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPrometniDokumentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(prometniDokument))
            )
            .andExpect(status().isBadRequest());

        // Validate the PrometniDokument in the database
        List<PrometniDokument> prometniDokumentList = prometniDokumentRepository.findAll();
        assertThat(prometniDokumentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPrometniDokuments() throws Exception {
        // Initialize the database
        prometniDokumentRepository.saveAndFlush(prometniDokument);

        // Get all the prometniDokumentList
        restPrometniDokumentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prometniDokument.getId().intValue())))
            .andExpect(jsonPath("$.[*].brojDokumenata").value(hasItem(DEFAULT_BROJ_DOKUMENATA.intValue())))
            .andExpect(jsonPath("$.[*].datum").value(hasItem(DEFAULT_DATUM.toString())))
            .andExpect(jsonPath("$.[*].vrsta").value(hasItem(DEFAULT_VRSTA)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    void getPrometniDokument() throws Exception {
        // Initialize the database
        prometniDokumentRepository.saveAndFlush(prometniDokument);

        // Get the prometniDokument
        restPrometniDokumentMockMvc
            .perform(get(ENTITY_API_URL_ID, prometniDokument.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(prometniDokument.getId().intValue()))
            .andExpect(jsonPath("$.brojDokumenata").value(DEFAULT_BROJ_DOKUMENATA.intValue()))
            .andExpect(jsonPath("$.datum").value(DEFAULT_DATUM.toString()))
            .andExpect(jsonPath("$.vrsta").value(DEFAULT_VRSTA))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    void getNonExistingPrometniDokument() throws Exception {
        // Get the prometniDokument
        restPrometniDokumentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPrometniDokument() throws Exception {
        // Initialize the database
        prometniDokumentRepository.saveAndFlush(prometniDokument);

        int databaseSizeBeforeUpdate = prometniDokumentRepository.findAll().size();

        // Update the prometniDokument
        PrometniDokument updatedPrometniDokument = prometniDokumentRepository.findById(prometniDokument.getId()).get();
        // Disconnect from session so that the updates on updatedPrometniDokument are not directly saved in db
        em.detach(updatedPrometniDokument);
        updatedPrometniDokument.brojDokumenata(UPDATED_BROJ_DOKUMENATA).datum(UPDATED_DATUM).vrsta(UPDATED_VRSTA).status(UPDATED_STATUS);

        restPrometniDokumentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPrometniDokument.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPrometniDokument))
            )
            .andExpect(status().isOk());

        // Validate the PrometniDokument in the database
        List<PrometniDokument> prometniDokumentList = prometniDokumentRepository.findAll();
        assertThat(prometniDokumentList).hasSize(databaseSizeBeforeUpdate);
        PrometniDokument testPrometniDokument = prometniDokumentList.get(prometniDokumentList.size() - 1);
        assertThat(testPrometniDokument.getBrojDokumenata()).isEqualTo(UPDATED_BROJ_DOKUMENATA);
        assertThat(testPrometniDokument.getDatum()).isEqualTo(UPDATED_DATUM);
        assertThat(testPrometniDokument.getVrsta()).isEqualTo(UPDATED_VRSTA);
        assertThat(testPrometniDokument.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingPrometniDokument() throws Exception {
        int databaseSizeBeforeUpdate = prometniDokumentRepository.findAll().size();
        prometniDokument.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrometniDokumentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, prometniDokument.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(prometniDokument))
            )
            .andExpect(status().isBadRequest());

        // Validate the PrometniDokument in the database
        List<PrometniDokument> prometniDokumentList = prometniDokumentRepository.findAll();
        assertThat(prometniDokumentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPrometniDokument() throws Exception {
        int databaseSizeBeforeUpdate = prometniDokumentRepository.findAll().size();
        prometniDokument.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrometniDokumentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(prometniDokument))
            )
            .andExpect(status().isBadRequest());

        // Validate the PrometniDokument in the database
        List<PrometniDokument> prometniDokumentList = prometniDokumentRepository.findAll();
        assertThat(prometniDokumentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPrometniDokument() throws Exception {
        int databaseSizeBeforeUpdate = prometniDokumentRepository.findAll().size();
        prometniDokument.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrometniDokumentMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(prometniDokument))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PrometniDokument in the database
        List<PrometniDokument> prometniDokumentList = prometniDokumentRepository.findAll();
        assertThat(prometniDokumentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePrometniDokumentWithPatch() throws Exception {
        // Initialize the database
        prometniDokumentRepository.saveAndFlush(prometniDokument);

        int databaseSizeBeforeUpdate = prometniDokumentRepository.findAll().size();

        // Update the prometniDokument using partial update
        PrometniDokument partialUpdatedPrometniDokument = new PrometniDokument();
        partialUpdatedPrometniDokument.setId(prometniDokument.getId());

        restPrometniDokumentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPrometniDokument.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPrometniDokument))
            )
            .andExpect(status().isOk());

        // Validate the PrometniDokument in the database
        List<PrometniDokument> prometniDokumentList = prometniDokumentRepository.findAll();
        assertThat(prometniDokumentList).hasSize(databaseSizeBeforeUpdate);
        PrometniDokument testPrometniDokument = prometniDokumentList.get(prometniDokumentList.size() - 1);
        assertThat(testPrometniDokument.getBrojDokumenata()).isEqualTo(DEFAULT_BROJ_DOKUMENATA);
        assertThat(testPrometniDokument.getDatum()).isEqualTo(DEFAULT_DATUM);
        assertThat(testPrometniDokument.getVrsta()).isEqualTo(DEFAULT_VRSTA);
        assertThat(testPrometniDokument.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void fullUpdatePrometniDokumentWithPatch() throws Exception {
        // Initialize the database
        prometniDokumentRepository.saveAndFlush(prometniDokument);

        int databaseSizeBeforeUpdate = prometniDokumentRepository.findAll().size();

        // Update the prometniDokument using partial update
        PrometniDokument partialUpdatedPrometniDokument = new PrometniDokument();
        partialUpdatedPrometniDokument.setId(prometniDokument.getId());

        partialUpdatedPrometniDokument
            .brojDokumenata(UPDATED_BROJ_DOKUMENATA)
            .datum(UPDATED_DATUM)
            .vrsta(UPDATED_VRSTA)
            .status(UPDATED_STATUS);

        restPrometniDokumentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPrometniDokument.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPrometniDokument))
            )
            .andExpect(status().isOk());

        // Validate the PrometniDokument in the database
        List<PrometniDokument> prometniDokumentList = prometniDokumentRepository.findAll();
        assertThat(prometniDokumentList).hasSize(databaseSizeBeforeUpdate);
        PrometniDokument testPrometniDokument = prometniDokumentList.get(prometniDokumentList.size() - 1);
        assertThat(testPrometniDokument.getBrojDokumenata()).isEqualTo(UPDATED_BROJ_DOKUMENATA);
        assertThat(testPrometniDokument.getDatum()).isEqualTo(UPDATED_DATUM);
        assertThat(testPrometniDokument.getVrsta()).isEqualTo(UPDATED_VRSTA);
        assertThat(testPrometniDokument.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingPrometniDokument() throws Exception {
        int databaseSizeBeforeUpdate = prometniDokumentRepository.findAll().size();
        prometniDokument.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrometniDokumentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, prometniDokument.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(prometniDokument))
            )
            .andExpect(status().isBadRequest());

        // Validate the PrometniDokument in the database
        List<PrometniDokument> prometniDokumentList = prometniDokumentRepository.findAll();
        assertThat(prometniDokumentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPrometniDokument() throws Exception {
        int databaseSizeBeforeUpdate = prometniDokumentRepository.findAll().size();
        prometniDokument.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrometniDokumentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(prometniDokument))
            )
            .andExpect(status().isBadRequest());

        // Validate the PrometniDokument in the database
        List<PrometniDokument> prometniDokumentList = prometniDokumentRepository.findAll();
        assertThat(prometniDokumentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPrometniDokument() throws Exception {
        int databaseSizeBeforeUpdate = prometniDokumentRepository.findAll().size();
        prometniDokument.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrometniDokumentMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(prometniDokument))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PrometniDokument in the database
        List<PrometniDokument> prometniDokumentList = prometniDokumentRepository.findAll();
        assertThat(prometniDokumentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePrometniDokument() throws Exception {
        // Initialize the database
        prometniDokumentRepository.saveAndFlush(prometniDokument);

        int databaseSizeBeforeDelete = prometniDokumentRepository.findAll().size();

        // Delete the prometniDokument
        restPrometniDokumentMockMvc
            .perform(delete(ENTITY_API_URL_ID, prometniDokument.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PrometniDokument> prometniDokumentList = prometniDokumentRepository.findAll();
        assertThat(prometniDokumentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
