package com.magacin.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.magacin.IntegrationTest;
import com.magacin.domain.Radnik;
import com.magacin.repository.RadnikRepository;
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
 * Integration tests for the {@link RadnikResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RadnikResourceIT {

    private static final String DEFAULT_IME = "AAAAAAAAAA";
    private static final String UPDATED_IME = "BBBBBBBBBB";

    private static final String DEFAULT_PREZIME = "AAAAAAAAAA";
    private static final String UPDATED_PREZIME = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESA = "AAAAAAAAAA";
    private static final String UPDATED_ADRESA = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFON = "AAAAAAAAAA";
    private static final String UPDATED_TELEFON = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/radniks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RadnikRepository radnikRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRadnikMockMvc;

    private Radnik radnik;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Radnik createEntity(EntityManager em) {
        Radnik radnik = new Radnik().ime(DEFAULT_IME).prezime(DEFAULT_PREZIME).adresa(DEFAULT_ADRESA).telefon(DEFAULT_TELEFON);
        return radnik;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Radnik createUpdatedEntity(EntityManager em) {
        Radnik radnik = new Radnik().ime(UPDATED_IME).prezime(UPDATED_PREZIME).adresa(UPDATED_ADRESA).telefon(UPDATED_TELEFON);
        return radnik;
    }

    @BeforeEach
    public void initTest() {
        radnik = createEntity(em);
    }

    @Test
    @Transactional
    void createRadnik() throws Exception {
        int databaseSizeBeforeCreate = radnikRepository.findAll().size();
        // Create the Radnik
        restRadnikMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(radnik)))
            .andExpect(status().isCreated());

        // Validate the Radnik in the database
        List<Radnik> radnikList = radnikRepository.findAll();
        assertThat(radnikList).hasSize(databaseSizeBeforeCreate + 1);
        Radnik testRadnik = radnikList.get(radnikList.size() - 1);
        assertThat(testRadnik.getIme()).isEqualTo(DEFAULT_IME);
        assertThat(testRadnik.getPrezime()).isEqualTo(DEFAULT_PREZIME);
        assertThat(testRadnik.getAdresa()).isEqualTo(DEFAULT_ADRESA);
        assertThat(testRadnik.getTelefon()).isEqualTo(DEFAULT_TELEFON);
    }

    @Test
    @Transactional
    void createRadnikWithExistingId() throws Exception {
        // Create the Radnik with an existing ID
        radnik.setId(1L);

        int databaseSizeBeforeCreate = radnikRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRadnikMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(radnik)))
            .andExpect(status().isBadRequest());

        // Validate the Radnik in the database
        List<Radnik> radnikList = radnikRepository.findAll();
        assertThat(radnikList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRadniks() throws Exception {
        // Initialize the database
        radnikRepository.saveAndFlush(radnik);

        // Get all the radnikList
        restRadnikMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(radnik.getId().intValue())))
            .andExpect(jsonPath("$.[*].ime").value(hasItem(DEFAULT_IME)))
            .andExpect(jsonPath("$.[*].prezime").value(hasItem(DEFAULT_PREZIME)))
            .andExpect(jsonPath("$.[*].adresa").value(hasItem(DEFAULT_ADRESA)))
            .andExpect(jsonPath("$.[*].telefon").value(hasItem(DEFAULT_TELEFON)));
    }

    @Test
    @Transactional
    void getRadnik() throws Exception {
        // Initialize the database
        radnikRepository.saveAndFlush(radnik);

        // Get the radnik
        restRadnikMockMvc
            .perform(get(ENTITY_API_URL_ID, radnik.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(radnik.getId().intValue()))
            .andExpect(jsonPath("$.ime").value(DEFAULT_IME))
            .andExpect(jsonPath("$.prezime").value(DEFAULT_PREZIME))
            .andExpect(jsonPath("$.adresa").value(DEFAULT_ADRESA))
            .andExpect(jsonPath("$.telefon").value(DEFAULT_TELEFON));
    }

    @Test
    @Transactional
    void getNonExistingRadnik() throws Exception {
        // Get the radnik
        restRadnikMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRadnik() throws Exception {
        // Initialize the database
        radnikRepository.saveAndFlush(radnik);

        int databaseSizeBeforeUpdate = radnikRepository.findAll().size();

        // Update the radnik
        Radnik updatedRadnik = radnikRepository.findById(radnik.getId()).get();
        // Disconnect from session so that the updates on updatedRadnik are not directly saved in db
        em.detach(updatedRadnik);
        updatedRadnik.ime(UPDATED_IME).prezime(UPDATED_PREZIME).adresa(UPDATED_ADRESA).telefon(UPDATED_TELEFON);

        restRadnikMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRadnik.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRadnik))
            )
            .andExpect(status().isOk());

        // Validate the Radnik in the database
        List<Radnik> radnikList = radnikRepository.findAll();
        assertThat(radnikList).hasSize(databaseSizeBeforeUpdate);
        Radnik testRadnik = radnikList.get(radnikList.size() - 1);
        assertThat(testRadnik.getIme()).isEqualTo(UPDATED_IME);
        assertThat(testRadnik.getPrezime()).isEqualTo(UPDATED_PREZIME);
        assertThat(testRadnik.getAdresa()).isEqualTo(UPDATED_ADRESA);
        assertThat(testRadnik.getTelefon()).isEqualTo(UPDATED_TELEFON);
    }

    @Test
    @Transactional
    void putNonExistingRadnik() throws Exception {
        int databaseSizeBeforeUpdate = radnikRepository.findAll().size();
        radnik.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRadnikMockMvc
            .perform(
                put(ENTITY_API_URL_ID, radnik.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(radnik))
            )
            .andExpect(status().isBadRequest());

        // Validate the Radnik in the database
        List<Radnik> radnikList = radnikRepository.findAll();
        assertThat(radnikList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRadnik() throws Exception {
        int databaseSizeBeforeUpdate = radnikRepository.findAll().size();
        radnik.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRadnikMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(radnik))
            )
            .andExpect(status().isBadRequest());

        // Validate the Radnik in the database
        List<Radnik> radnikList = radnikRepository.findAll();
        assertThat(radnikList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRadnik() throws Exception {
        int databaseSizeBeforeUpdate = radnikRepository.findAll().size();
        radnik.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRadnikMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(radnik)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Radnik in the database
        List<Radnik> radnikList = radnikRepository.findAll();
        assertThat(radnikList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRadnikWithPatch() throws Exception {
        // Initialize the database
        radnikRepository.saveAndFlush(radnik);

        int databaseSizeBeforeUpdate = radnikRepository.findAll().size();

        // Update the radnik using partial update
        Radnik partialUpdatedRadnik = new Radnik();
        partialUpdatedRadnik.setId(radnik.getId());

        partialUpdatedRadnik.telefon(UPDATED_TELEFON);

        restRadnikMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRadnik.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRadnik))
            )
            .andExpect(status().isOk());

        // Validate the Radnik in the database
        List<Radnik> radnikList = radnikRepository.findAll();
        assertThat(radnikList).hasSize(databaseSizeBeforeUpdate);
        Radnik testRadnik = radnikList.get(radnikList.size() - 1);
        assertThat(testRadnik.getIme()).isEqualTo(DEFAULT_IME);
        assertThat(testRadnik.getPrezime()).isEqualTo(DEFAULT_PREZIME);
        assertThat(testRadnik.getAdresa()).isEqualTo(DEFAULT_ADRESA);
        assertThat(testRadnik.getTelefon()).isEqualTo(UPDATED_TELEFON);
    }

    @Test
    @Transactional
    void fullUpdateRadnikWithPatch() throws Exception {
        // Initialize the database
        radnikRepository.saveAndFlush(radnik);

        int databaseSizeBeforeUpdate = radnikRepository.findAll().size();

        // Update the radnik using partial update
        Radnik partialUpdatedRadnik = new Radnik();
        partialUpdatedRadnik.setId(radnik.getId());

        partialUpdatedRadnik.ime(UPDATED_IME).prezime(UPDATED_PREZIME).adresa(UPDATED_ADRESA).telefon(UPDATED_TELEFON);

        restRadnikMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRadnik.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRadnik))
            )
            .andExpect(status().isOk());

        // Validate the Radnik in the database
        List<Radnik> radnikList = radnikRepository.findAll();
        assertThat(radnikList).hasSize(databaseSizeBeforeUpdate);
        Radnik testRadnik = radnikList.get(radnikList.size() - 1);
        assertThat(testRadnik.getIme()).isEqualTo(UPDATED_IME);
        assertThat(testRadnik.getPrezime()).isEqualTo(UPDATED_PREZIME);
        assertThat(testRadnik.getAdresa()).isEqualTo(UPDATED_ADRESA);
        assertThat(testRadnik.getTelefon()).isEqualTo(UPDATED_TELEFON);
    }

    @Test
    @Transactional
    void patchNonExistingRadnik() throws Exception {
        int databaseSizeBeforeUpdate = radnikRepository.findAll().size();
        radnik.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRadnikMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, radnik.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(radnik))
            )
            .andExpect(status().isBadRequest());

        // Validate the Radnik in the database
        List<Radnik> radnikList = radnikRepository.findAll();
        assertThat(radnikList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRadnik() throws Exception {
        int databaseSizeBeforeUpdate = radnikRepository.findAll().size();
        radnik.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRadnikMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(radnik))
            )
            .andExpect(status().isBadRequest());

        // Validate the Radnik in the database
        List<Radnik> radnikList = radnikRepository.findAll();
        assertThat(radnikList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRadnik() throws Exception {
        int databaseSizeBeforeUpdate = radnikRepository.findAll().size();
        radnik.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRadnikMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(radnik)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Radnik in the database
        List<Radnik> radnikList = radnikRepository.findAll();
        assertThat(radnikList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRadnik() throws Exception {
        // Initialize the database
        radnikRepository.saveAndFlush(radnik);

        int databaseSizeBeforeDelete = radnikRepository.findAll().size();

        // Delete the radnik
        restRadnikMockMvc
            .perform(delete(ENTITY_API_URL_ID, radnik.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Radnik> radnikList = radnikRepository.findAll();
        assertThat(radnikList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
