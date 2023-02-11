package com.magacin.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.magacin.IntegrationTest;
import com.magacin.domain.PrometMagacinskeKartice;
import com.magacin.repository.PrometMagacinskeKarticeRepository;
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
 * Integration tests for the {@link PrometMagacinskeKarticeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PrometMagacinskeKarticeResourceIT {

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

    private static final String DEFAULT_SMER = "AAAAAAAAAA";
    private static final String UPDATED_SMER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/promet-magacinske-kartices";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PrometMagacinskeKarticeRepository prometMagacinskeKarticeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPrometMagacinskeKarticeMockMvc;

    private PrometMagacinskeKartice prometMagacinskeKartice;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PrometMagacinskeKartice createEntity(EntityManager em) {
        PrometMagacinskeKartice prometMagacinskeKartice = new PrometMagacinskeKartice()
            .datumPrometa(DEFAULT_DATUM_PROMETA)
            .kolicina(DEFAULT_KOLICINA)
            .cena(DEFAULT_CENA)
            .vrednost(DEFAULT_VREDNOST)
            .dokument(DEFAULT_DOKUMENT)
            .smer(DEFAULT_SMER);
        return prometMagacinskeKartice;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PrometMagacinskeKartice createUpdatedEntity(EntityManager em) {
        PrometMagacinskeKartice prometMagacinskeKartice = new PrometMagacinskeKartice()
            .datumPrometa(UPDATED_DATUM_PROMETA)
            .kolicina(UPDATED_KOLICINA)
            .cena(UPDATED_CENA)
            .vrednost(UPDATED_VREDNOST)
            .dokument(UPDATED_DOKUMENT)
            .smer(UPDATED_SMER);
        return prometMagacinskeKartice;
    }

    @BeforeEach
    public void initTest() {
        prometMagacinskeKartice = createEntity(em);
    }

    @Test
    @Transactional
    void createPrometMagacinskeKartice() throws Exception {
        int databaseSizeBeforeCreate = prometMagacinskeKarticeRepository.findAll().size();
        // Create the PrometMagacinskeKartice
        restPrometMagacinskeKarticeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(prometMagacinskeKartice))
            )
            .andExpect(status().isCreated());

        // Validate the PrometMagacinskeKartice in the database
        List<PrometMagacinskeKartice> prometMagacinskeKarticeList = prometMagacinskeKarticeRepository.findAll();
        assertThat(prometMagacinskeKarticeList).hasSize(databaseSizeBeforeCreate + 1);
        PrometMagacinskeKartice testPrometMagacinskeKartice = prometMagacinskeKarticeList.get(prometMagacinskeKarticeList.size() - 1);
        assertThat(testPrometMagacinskeKartice.getDatumPrometa()).isEqualTo(DEFAULT_DATUM_PROMETA);
        assertThat(testPrometMagacinskeKartice.getKolicina()).isEqualTo(DEFAULT_KOLICINA);
        assertThat(testPrometMagacinskeKartice.getCena()).isEqualTo(DEFAULT_CENA);
        assertThat(testPrometMagacinskeKartice.getVrednost()).isEqualTo(DEFAULT_VREDNOST);
        assertThat(testPrometMagacinskeKartice.getDokument()).isEqualTo(DEFAULT_DOKUMENT);
        assertThat(testPrometMagacinskeKartice.getSmer()).isEqualTo(DEFAULT_SMER);
    }

    @Test
    @Transactional
    void createPrometMagacinskeKarticeWithExistingId() throws Exception {
        // Create the PrometMagacinskeKartice with an existing ID
        prometMagacinskeKartice.setId(1L);

        int databaseSizeBeforeCreate = prometMagacinskeKarticeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPrometMagacinskeKarticeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(prometMagacinskeKartice))
            )
            .andExpect(status().isBadRequest());

        // Validate the PrometMagacinskeKartice in the database
        List<PrometMagacinskeKartice> prometMagacinskeKarticeList = prometMagacinskeKarticeRepository.findAll();
        assertThat(prometMagacinskeKarticeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPrometMagacinskeKartices() throws Exception {
        // Initialize the database
        prometMagacinskeKarticeRepository.saveAndFlush(prometMagacinskeKartice);

        // Get all the prometMagacinskeKarticeList
        restPrometMagacinskeKarticeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prometMagacinskeKartice.getId().intValue())))
            .andExpect(jsonPath("$.[*].datumPrometa").value(hasItem(DEFAULT_DATUM_PROMETA.toString())))
            .andExpect(jsonPath("$.[*].kolicina").value(hasItem(DEFAULT_KOLICINA.intValue())))
            .andExpect(jsonPath("$.[*].cena").value(hasItem(DEFAULT_CENA.intValue())))
            .andExpect(jsonPath("$.[*].vrednost").value(hasItem(DEFAULT_VREDNOST.intValue())))
            .andExpect(jsonPath("$.[*].dokument").value(hasItem(DEFAULT_DOKUMENT)))
            .andExpect(jsonPath("$.[*].smer").value(hasItem(DEFAULT_SMER)));
    }

    @Test
    @Transactional
    void getPrometMagacinskeKartice() throws Exception {
        // Initialize the database
        prometMagacinskeKarticeRepository.saveAndFlush(prometMagacinskeKartice);

        // Get the prometMagacinskeKartice
        restPrometMagacinskeKarticeMockMvc
            .perform(get(ENTITY_API_URL_ID, prometMagacinskeKartice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(prometMagacinskeKartice.getId().intValue()))
            .andExpect(jsonPath("$.datumPrometa").value(DEFAULT_DATUM_PROMETA.toString()))
            .andExpect(jsonPath("$.kolicina").value(DEFAULT_KOLICINA.intValue()))
            .andExpect(jsonPath("$.cena").value(DEFAULT_CENA.intValue()))
            .andExpect(jsonPath("$.vrednost").value(DEFAULT_VREDNOST.intValue()))
            .andExpect(jsonPath("$.dokument").value(DEFAULT_DOKUMENT))
            .andExpect(jsonPath("$.smer").value(DEFAULT_SMER));
    }

    @Test
    @Transactional
    void getNonExistingPrometMagacinskeKartice() throws Exception {
        // Get the prometMagacinskeKartice
        restPrometMagacinskeKarticeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPrometMagacinskeKartice() throws Exception {
        // Initialize the database
        prometMagacinskeKarticeRepository.saveAndFlush(prometMagacinskeKartice);

        int databaseSizeBeforeUpdate = prometMagacinskeKarticeRepository.findAll().size();

        // Update the prometMagacinskeKartice
        PrometMagacinskeKartice updatedPrometMagacinskeKartice = prometMagacinskeKarticeRepository
            .findById(prometMagacinskeKartice.getId())
            .get();
        // Disconnect from session so that the updates on updatedPrometMagacinskeKartice are not directly saved in db
        em.detach(updatedPrometMagacinskeKartice);
        updatedPrometMagacinskeKartice
            .datumPrometa(UPDATED_DATUM_PROMETA)
            .kolicina(UPDATED_KOLICINA)
            .cena(UPDATED_CENA)
            .vrednost(UPDATED_VREDNOST)
            .dokument(UPDATED_DOKUMENT)
            .smer(UPDATED_SMER);

        restPrometMagacinskeKarticeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPrometMagacinskeKartice.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPrometMagacinskeKartice))
            )
            .andExpect(status().isOk());

        // Validate the PrometMagacinskeKartice in the database
        List<PrometMagacinskeKartice> prometMagacinskeKarticeList = prometMagacinskeKarticeRepository.findAll();
        assertThat(prometMagacinskeKarticeList).hasSize(databaseSizeBeforeUpdate);
        PrometMagacinskeKartice testPrometMagacinskeKartice = prometMagacinskeKarticeList.get(prometMagacinskeKarticeList.size() - 1);
        assertThat(testPrometMagacinskeKartice.getDatumPrometa()).isEqualTo(UPDATED_DATUM_PROMETA);
        assertThat(testPrometMagacinskeKartice.getKolicina()).isEqualTo(UPDATED_KOLICINA);
        assertThat(testPrometMagacinskeKartice.getCena()).isEqualTo(UPDATED_CENA);
        assertThat(testPrometMagacinskeKartice.getVrednost()).isEqualTo(UPDATED_VREDNOST);
        assertThat(testPrometMagacinskeKartice.getDokument()).isEqualTo(UPDATED_DOKUMENT);
        assertThat(testPrometMagacinskeKartice.getSmer()).isEqualTo(UPDATED_SMER);
    }

    @Test
    @Transactional
    void putNonExistingPrometMagacinskeKartice() throws Exception {
        int databaseSizeBeforeUpdate = prometMagacinskeKarticeRepository.findAll().size();
        prometMagacinskeKartice.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrometMagacinskeKarticeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, prometMagacinskeKartice.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(prometMagacinskeKartice))
            )
            .andExpect(status().isBadRequest());

        // Validate the PrometMagacinskeKartice in the database
        List<PrometMagacinskeKartice> prometMagacinskeKarticeList = prometMagacinskeKarticeRepository.findAll();
        assertThat(prometMagacinskeKarticeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPrometMagacinskeKartice() throws Exception {
        int databaseSizeBeforeUpdate = prometMagacinskeKarticeRepository.findAll().size();
        prometMagacinskeKartice.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrometMagacinskeKarticeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(prometMagacinskeKartice))
            )
            .andExpect(status().isBadRequest());

        // Validate the PrometMagacinskeKartice in the database
        List<PrometMagacinskeKartice> prometMagacinskeKarticeList = prometMagacinskeKarticeRepository.findAll();
        assertThat(prometMagacinskeKarticeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPrometMagacinskeKartice() throws Exception {
        int databaseSizeBeforeUpdate = prometMagacinskeKarticeRepository.findAll().size();
        prometMagacinskeKartice.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrometMagacinskeKarticeMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(prometMagacinskeKartice))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PrometMagacinskeKartice in the database
        List<PrometMagacinskeKartice> prometMagacinskeKarticeList = prometMagacinskeKarticeRepository.findAll();
        assertThat(prometMagacinskeKarticeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePrometMagacinskeKarticeWithPatch() throws Exception {
        // Initialize the database
        prometMagacinskeKarticeRepository.saveAndFlush(prometMagacinskeKartice);

        int databaseSizeBeforeUpdate = prometMagacinskeKarticeRepository.findAll().size();

        // Update the prometMagacinskeKartice using partial update
        PrometMagacinskeKartice partialUpdatedPrometMagacinskeKartice = new PrometMagacinskeKartice();
        partialUpdatedPrometMagacinskeKartice.setId(prometMagacinskeKartice.getId());

        partialUpdatedPrometMagacinskeKartice.kolicina(UPDATED_KOLICINA).cena(UPDATED_CENA);

        restPrometMagacinskeKarticeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPrometMagacinskeKartice.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPrometMagacinskeKartice))
            )
            .andExpect(status().isOk());

        // Validate the PrometMagacinskeKartice in the database
        List<PrometMagacinskeKartice> prometMagacinskeKarticeList = prometMagacinskeKarticeRepository.findAll();
        assertThat(prometMagacinskeKarticeList).hasSize(databaseSizeBeforeUpdate);
        PrometMagacinskeKartice testPrometMagacinskeKartice = prometMagacinskeKarticeList.get(prometMagacinskeKarticeList.size() - 1);
        assertThat(testPrometMagacinskeKartice.getDatumPrometa()).isEqualTo(DEFAULT_DATUM_PROMETA);
        assertThat(testPrometMagacinskeKartice.getKolicina()).isEqualTo(UPDATED_KOLICINA);
        assertThat(testPrometMagacinskeKartice.getCena()).isEqualTo(UPDATED_CENA);
        assertThat(testPrometMagacinskeKartice.getVrednost()).isEqualTo(DEFAULT_VREDNOST);
        assertThat(testPrometMagacinskeKartice.getDokument()).isEqualTo(DEFAULT_DOKUMENT);
        assertThat(testPrometMagacinskeKartice.getSmer()).isEqualTo(DEFAULT_SMER);
    }

    @Test
    @Transactional
    void fullUpdatePrometMagacinskeKarticeWithPatch() throws Exception {
        // Initialize the database
        prometMagacinskeKarticeRepository.saveAndFlush(prometMagacinskeKartice);

        int databaseSizeBeforeUpdate = prometMagacinskeKarticeRepository.findAll().size();

        // Update the prometMagacinskeKartice using partial update
        PrometMagacinskeKartice partialUpdatedPrometMagacinskeKartice = new PrometMagacinskeKartice();
        partialUpdatedPrometMagacinskeKartice.setId(prometMagacinskeKartice.getId());

        partialUpdatedPrometMagacinskeKartice
            .datumPrometa(UPDATED_DATUM_PROMETA)
            .kolicina(UPDATED_KOLICINA)
            .cena(UPDATED_CENA)
            .vrednost(UPDATED_VREDNOST)
            .dokument(UPDATED_DOKUMENT)
            .smer(UPDATED_SMER);

        restPrometMagacinskeKarticeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPrometMagacinskeKartice.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPrometMagacinskeKartice))
            )
            .andExpect(status().isOk());

        // Validate the PrometMagacinskeKartice in the database
        List<PrometMagacinskeKartice> prometMagacinskeKarticeList = prometMagacinskeKarticeRepository.findAll();
        assertThat(prometMagacinskeKarticeList).hasSize(databaseSizeBeforeUpdate);
        PrometMagacinskeKartice testPrometMagacinskeKartice = prometMagacinskeKarticeList.get(prometMagacinskeKarticeList.size() - 1);
        assertThat(testPrometMagacinskeKartice.getDatumPrometa()).isEqualTo(UPDATED_DATUM_PROMETA);
        assertThat(testPrometMagacinskeKartice.getKolicina()).isEqualTo(UPDATED_KOLICINA);
        assertThat(testPrometMagacinskeKartice.getCena()).isEqualTo(UPDATED_CENA);
        assertThat(testPrometMagacinskeKartice.getVrednost()).isEqualTo(UPDATED_VREDNOST);
        assertThat(testPrometMagacinskeKartice.getDokument()).isEqualTo(UPDATED_DOKUMENT);
        assertThat(testPrometMagacinskeKartice.getSmer()).isEqualTo(UPDATED_SMER);
    }

    @Test
    @Transactional
    void patchNonExistingPrometMagacinskeKartice() throws Exception {
        int databaseSizeBeforeUpdate = prometMagacinskeKarticeRepository.findAll().size();
        prometMagacinskeKartice.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrometMagacinskeKarticeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, prometMagacinskeKartice.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(prometMagacinskeKartice))
            )
            .andExpect(status().isBadRequest());

        // Validate the PrometMagacinskeKartice in the database
        List<PrometMagacinskeKartice> prometMagacinskeKarticeList = prometMagacinskeKarticeRepository.findAll();
        assertThat(prometMagacinskeKarticeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPrometMagacinskeKartice() throws Exception {
        int databaseSizeBeforeUpdate = prometMagacinskeKarticeRepository.findAll().size();
        prometMagacinskeKartice.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrometMagacinskeKarticeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(prometMagacinskeKartice))
            )
            .andExpect(status().isBadRequest());

        // Validate the PrometMagacinskeKartice in the database
        List<PrometMagacinskeKartice> prometMagacinskeKarticeList = prometMagacinskeKarticeRepository.findAll();
        assertThat(prometMagacinskeKarticeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPrometMagacinskeKartice() throws Exception {
        int databaseSizeBeforeUpdate = prometMagacinskeKarticeRepository.findAll().size();
        prometMagacinskeKartice.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrometMagacinskeKarticeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(prometMagacinskeKartice))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PrometMagacinskeKartice in the database
        List<PrometMagacinskeKartice> prometMagacinskeKarticeList = prometMagacinskeKarticeRepository.findAll();
        assertThat(prometMagacinskeKarticeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePrometMagacinskeKartice() throws Exception {
        // Initialize the database
        prometMagacinskeKarticeRepository.saveAndFlush(prometMagacinskeKartice);

        int databaseSizeBeforeDelete = prometMagacinskeKarticeRepository.findAll().size();

        // Delete the prometMagacinskeKartice
        restPrometMagacinskeKarticeMockMvc
            .perform(delete(ENTITY_API_URL_ID, prometMagacinskeKartice.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PrometMagacinskeKartice> prometMagacinskeKarticeList = prometMagacinskeKarticeRepository.findAll();
        assertThat(prometMagacinskeKarticeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
