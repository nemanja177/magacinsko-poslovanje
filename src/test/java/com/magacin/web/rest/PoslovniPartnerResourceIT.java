package com.magacin.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.magacin.IntegrationTest;
import com.magacin.domain.PoslovniPartner;
import com.magacin.repository.PoslovniPartnerRepository;
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
 * Integration tests for the {@link PoslovniPartnerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PoslovniPartnerResourceIT {

    private static final String DEFAULT_IME = "AAAAAAAAAA";
    private static final String UPDATED_IME = "BBBBBBBBBB";

    private static final String DEFAULT_PREZIME = "AAAAAAAAAA";
    private static final String UPDATED_PREZIME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_JMBG = "AAAAAAAAAA";
    private static final String UPDATED_JMBG = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESA = "AAAAAAAAAA";
    private static final String UPDATED_ADRESA = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/poslovni-partners";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PoslovniPartnerRepository poslovniPartnerRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPoslovniPartnerMockMvc;

    private PoslovniPartner poslovniPartner;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PoslovniPartner createEntity(EntityManager em) {
        PoslovniPartner poslovniPartner = new PoslovniPartner()
            .ime(DEFAULT_IME)
            .prezime(DEFAULT_PREZIME)
            .email(DEFAULT_EMAIL)
            .jmbg(DEFAULT_JMBG)
            .adresa(DEFAULT_ADRESA);
        return poslovniPartner;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PoslovniPartner createUpdatedEntity(EntityManager em) {
        PoslovniPartner poslovniPartner = new PoslovniPartner()
            .ime(UPDATED_IME)
            .prezime(UPDATED_PREZIME)
            .email(UPDATED_EMAIL)
            .jmbg(UPDATED_JMBG)
            .adresa(UPDATED_ADRESA);
        return poslovniPartner;
    }

    @BeforeEach
    public void initTest() {
        poslovniPartner = createEntity(em);
    }

    @Test
    @Transactional
    void createPoslovniPartner() throws Exception {
        int databaseSizeBeforeCreate = poslovniPartnerRepository.findAll().size();
        // Create the PoslovniPartner
        restPoslovniPartnerMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(poslovniPartner))
            )
            .andExpect(status().isCreated());

        // Validate the PoslovniPartner in the database
        List<PoslovniPartner> poslovniPartnerList = poslovniPartnerRepository.findAll();
        assertThat(poslovniPartnerList).hasSize(databaseSizeBeforeCreate + 1);
        PoslovniPartner testPoslovniPartner = poslovniPartnerList.get(poslovniPartnerList.size() - 1);
        assertThat(testPoslovniPartner.getIme()).isEqualTo(DEFAULT_IME);
        assertThat(testPoslovniPartner.getPrezime()).isEqualTo(DEFAULT_PREZIME);
        assertThat(testPoslovniPartner.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testPoslovniPartner.getJmbg()).isEqualTo(DEFAULT_JMBG);
        assertThat(testPoslovniPartner.getAdresa()).isEqualTo(DEFAULT_ADRESA);
    }

    @Test
    @Transactional
    void createPoslovniPartnerWithExistingId() throws Exception {
        // Create the PoslovniPartner with an existing ID
        poslovniPartner.setId(1L);

        int databaseSizeBeforeCreate = poslovniPartnerRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPoslovniPartnerMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(poslovniPartner))
            )
            .andExpect(status().isBadRequest());

        // Validate the PoslovniPartner in the database
        List<PoslovniPartner> poslovniPartnerList = poslovniPartnerRepository.findAll();
        assertThat(poslovniPartnerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPoslovniPartners() throws Exception {
        // Initialize the database
        poslovniPartnerRepository.saveAndFlush(poslovniPartner);

        // Get all the poslovniPartnerList
        restPoslovniPartnerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(poslovniPartner.getId().intValue())))
            .andExpect(jsonPath("$.[*].ime").value(hasItem(DEFAULT_IME)))
            .andExpect(jsonPath("$.[*].prezime").value(hasItem(DEFAULT_PREZIME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].jmbg").value(hasItem(DEFAULT_JMBG)))
            .andExpect(jsonPath("$.[*].adresa").value(hasItem(DEFAULT_ADRESA)));
    }

    @Test
    @Transactional
    void getPoslovniPartner() throws Exception {
        // Initialize the database
        poslovniPartnerRepository.saveAndFlush(poslovniPartner);

        // Get the poslovniPartner
        restPoslovniPartnerMockMvc
            .perform(get(ENTITY_API_URL_ID, poslovniPartner.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(poslovniPartner.getId().intValue()))
            .andExpect(jsonPath("$.ime").value(DEFAULT_IME))
            .andExpect(jsonPath("$.prezime").value(DEFAULT_PREZIME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.jmbg").value(DEFAULT_JMBG))
            .andExpect(jsonPath("$.adresa").value(DEFAULT_ADRESA));
    }

    @Test
    @Transactional
    void getNonExistingPoslovniPartner() throws Exception {
        // Get the poslovniPartner
        restPoslovniPartnerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPoslovniPartner() throws Exception {
        // Initialize the database
        poslovniPartnerRepository.saveAndFlush(poslovniPartner);

        int databaseSizeBeforeUpdate = poslovniPartnerRepository.findAll().size();

        // Update the poslovniPartner
        PoslovniPartner updatedPoslovniPartner = poslovniPartnerRepository.findById(poslovniPartner.getId()).get();
        // Disconnect from session so that the updates on updatedPoslovniPartner are not directly saved in db
        em.detach(updatedPoslovniPartner);
        updatedPoslovniPartner.ime(UPDATED_IME).prezime(UPDATED_PREZIME).email(UPDATED_EMAIL).jmbg(UPDATED_JMBG).adresa(UPDATED_ADRESA);

        restPoslovniPartnerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPoslovniPartner.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPoslovniPartner))
            )
            .andExpect(status().isOk());

        // Validate the PoslovniPartner in the database
        List<PoslovniPartner> poslovniPartnerList = poslovniPartnerRepository.findAll();
        assertThat(poslovniPartnerList).hasSize(databaseSizeBeforeUpdate);
        PoslovniPartner testPoslovniPartner = poslovniPartnerList.get(poslovniPartnerList.size() - 1);
        assertThat(testPoslovniPartner.getIme()).isEqualTo(UPDATED_IME);
        assertThat(testPoslovniPartner.getPrezime()).isEqualTo(UPDATED_PREZIME);
        assertThat(testPoslovniPartner.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testPoslovniPartner.getJmbg()).isEqualTo(UPDATED_JMBG);
        assertThat(testPoslovniPartner.getAdresa()).isEqualTo(UPDATED_ADRESA);
    }

    @Test
    @Transactional
    void putNonExistingPoslovniPartner() throws Exception {
        int databaseSizeBeforeUpdate = poslovniPartnerRepository.findAll().size();
        poslovniPartner.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPoslovniPartnerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, poslovniPartner.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(poslovniPartner))
            )
            .andExpect(status().isBadRequest());

        // Validate the PoslovniPartner in the database
        List<PoslovniPartner> poslovniPartnerList = poslovniPartnerRepository.findAll();
        assertThat(poslovniPartnerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPoslovniPartner() throws Exception {
        int databaseSizeBeforeUpdate = poslovniPartnerRepository.findAll().size();
        poslovniPartner.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPoslovniPartnerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(poslovniPartner))
            )
            .andExpect(status().isBadRequest());

        // Validate the PoslovniPartner in the database
        List<PoslovniPartner> poslovniPartnerList = poslovniPartnerRepository.findAll();
        assertThat(poslovniPartnerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPoslovniPartner() throws Exception {
        int databaseSizeBeforeUpdate = poslovniPartnerRepository.findAll().size();
        poslovniPartner.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPoslovniPartnerMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(poslovniPartner))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PoslovniPartner in the database
        List<PoslovniPartner> poslovniPartnerList = poslovniPartnerRepository.findAll();
        assertThat(poslovniPartnerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePoslovniPartnerWithPatch() throws Exception {
        // Initialize the database
        poslovniPartnerRepository.saveAndFlush(poslovniPartner);

        int databaseSizeBeforeUpdate = poslovniPartnerRepository.findAll().size();

        // Update the poslovniPartner using partial update
        PoslovniPartner partialUpdatedPoslovniPartner = new PoslovniPartner();
        partialUpdatedPoslovniPartner.setId(poslovniPartner.getId());

        partialUpdatedPoslovniPartner.ime(UPDATED_IME).prezime(UPDATED_PREZIME).jmbg(UPDATED_JMBG).adresa(UPDATED_ADRESA);

        restPoslovniPartnerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPoslovniPartner.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPoslovniPartner))
            )
            .andExpect(status().isOk());

        // Validate the PoslovniPartner in the database
        List<PoslovniPartner> poslovniPartnerList = poslovniPartnerRepository.findAll();
        assertThat(poslovniPartnerList).hasSize(databaseSizeBeforeUpdate);
        PoslovniPartner testPoslovniPartner = poslovniPartnerList.get(poslovniPartnerList.size() - 1);
        assertThat(testPoslovniPartner.getIme()).isEqualTo(UPDATED_IME);
        assertThat(testPoslovniPartner.getPrezime()).isEqualTo(UPDATED_PREZIME);
        assertThat(testPoslovniPartner.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testPoslovniPartner.getJmbg()).isEqualTo(UPDATED_JMBG);
        assertThat(testPoslovniPartner.getAdresa()).isEqualTo(UPDATED_ADRESA);
    }

    @Test
    @Transactional
    void fullUpdatePoslovniPartnerWithPatch() throws Exception {
        // Initialize the database
        poslovniPartnerRepository.saveAndFlush(poslovniPartner);

        int databaseSizeBeforeUpdate = poslovniPartnerRepository.findAll().size();

        // Update the poslovniPartner using partial update
        PoslovniPartner partialUpdatedPoslovniPartner = new PoslovniPartner();
        partialUpdatedPoslovniPartner.setId(poslovniPartner.getId());

        partialUpdatedPoslovniPartner
            .ime(UPDATED_IME)
            .prezime(UPDATED_PREZIME)
            .email(UPDATED_EMAIL)
            .jmbg(UPDATED_JMBG)
            .adresa(UPDATED_ADRESA);

        restPoslovniPartnerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPoslovniPartner.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPoslovniPartner))
            )
            .andExpect(status().isOk());

        // Validate the PoslovniPartner in the database
        List<PoslovniPartner> poslovniPartnerList = poslovniPartnerRepository.findAll();
        assertThat(poslovniPartnerList).hasSize(databaseSizeBeforeUpdate);
        PoslovniPartner testPoslovniPartner = poslovniPartnerList.get(poslovniPartnerList.size() - 1);
        assertThat(testPoslovniPartner.getIme()).isEqualTo(UPDATED_IME);
        assertThat(testPoslovniPartner.getPrezime()).isEqualTo(UPDATED_PREZIME);
        assertThat(testPoslovniPartner.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testPoslovniPartner.getJmbg()).isEqualTo(UPDATED_JMBG);
        assertThat(testPoslovniPartner.getAdresa()).isEqualTo(UPDATED_ADRESA);
    }

    @Test
    @Transactional
    void patchNonExistingPoslovniPartner() throws Exception {
        int databaseSizeBeforeUpdate = poslovniPartnerRepository.findAll().size();
        poslovniPartner.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPoslovniPartnerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, poslovniPartner.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(poslovniPartner))
            )
            .andExpect(status().isBadRequest());

        // Validate the PoslovniPartner in the database
        List<PoslovniPartner> poslovniPartnerList = poslovniPartnerRepository.findAll();
        assertThat(poslovniPartnerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPoslovniPartner() throws Exception {
        int databaseSizeBeforeUpdate = poslovniPartnerRepository.findAll().size();
        poslovniPartner.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPoslovniPartnerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(poslovniPartner))
            )
            .andExpect(status().isBadRequest());

        // Validate the PoslovniPartner in the database
        List<PoslovniPartner> poslovniPartnerList = poslovniPartnerRepository.findAll();
        assertThat(poslovniPartnerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPoslovniPartner() throws Exception {
        int databaseSizeBeforeUpdate = poslovniPartnerRepository.findAll().size();
        poslovniPartner.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPoslovniPartnerMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(poslovniPartner))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PoslovniPartner in the database
        List<PoslovniPartner> poslovniPartnerList = poslovniPartnerRepository.findAll();
        assertThat(poslovniPartnerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePoslovniPartner() throws Exception {
        // Initialize the database
        poslovniPartnerRepository.saveAndFlush(poslovniPartner);

        int databaseSizeBeforeDelete = poslovniPartnerRepository.findAll().size();

        // Delete the poslovniPartner
        restPoslovniPartnerMockMvc
            .perform(delete(ENTITY_API_URL_ID, poslovniPartner.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PoslovniPartner> poslovniPartnerList = poslovniPartnerRepository.findAll();
        assertThat(poslovniPartnerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
