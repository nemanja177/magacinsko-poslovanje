package com.magacin.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.magacin.IntegrationTest;
import com.magacin.domain.MagacinskaKartica;
import com.magacin.repository.MagacinskaKarticaRepository;
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
 * Integration tests for the {@link MagacinskaKarticaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MagacinskaKarticaResourceIT {

    private static final Double DEFAULT_POCETNO_STANJE_KOLICINA = 1D;
    private static final Double UPDATED_POCETNO_STANJE_KOLICINA = 2D;

    private static final Double DEFAULT_PROMET_ULAZA_KOLICINA = 1D;
    private static final Double UPDATED_PROMET_ULAZA_KOLICINA = 2D;

    private static final Double DEFAULT_PROMET_IZLAZA_KOLICINA = 1D;
    private static final Double UPDATED_PROMET_IZLAZA_KOLICINA = 2D;

    private static final Double DEFAULT_UKUPNA_KOLICINA = 1D;
    private static final Double UPDATED_UKUPNA_KOLICINA = 2D;

    private static final Double DEFAULT_POCETNO_STANJE_VREDNOSTI = 1D;
    private static final Double UPDATED_POCETNO_STANJE_VREDNOSTI = 2D;

    private static final Double DEFAULT_PROMET_ULAZA_VREDNOSTI = 1D;
    private static final Double UPDATED_PROMET_ULAZA_VREDNOSTI = 2D;

    private static final Double DEFAULT_PROMET_IZLAZA_VREDNOSTI = 1D;
    private static final Double UPDATED_PROMET_IZLAZA_VREDNOSTI = 2D;

    private static final Double DEFAULT_UKUPNA_VREDNOST = 1D;
    private static final Double UPDATED_UKUPNA_VREDNOST = 2D;

    private static final String ENTITY_API_URL = "/api/magacinska-karticas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MagacinskaKarticaRepository magacinskaKarticaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMagacinskaKarticaMockMvc;

    private MagacinskaKartica magacinskaKartica;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MagacinskaKartica createEntity(EntityManager em) {
        MagacinskaKartica magacinskaKartica = new MagacinskaKartica()
            .pocetnoStanjeKolicina(DEFAULT_POCETNO_STANJE_KOLICINA)
            .prometUlazaKolicina(DEFAULT_PROMET_ULAZA_KOLICINA)
            .prometIzlazaKolicina(DEFAULT_PROMET_IZLAZA_KOLICINA)
            .ukupnaKolicina(DEFAULT_UKUPNA_KOLICINA)
            .pocetnoStanjeVrednosti(DEFAULT_POCETNO_STANJE_VREDNOSTI)
            .prometUlazaVrednosti(DEFAULT_PROMET_ULAZA_VREDNOSTI)
            .prometIzlazaVrednosti(DEFAULT_PROMET_IZLAZA_VREDNOSTI)
            .ukupnaVrednost(DEFAULT_UKUPNA_VREDNOST);
        return magacinskaKartica;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MagacinskaKartica createUpdatedEntity(EntityManager em) {
        MagacinskaKartica magacinskaKartica = new MagacinskaKartica()
            .pocetnoStanjeKolicina(UPDATED_POCETNO_STANJE_KOLICINA)
            .prometUlazaKolicina(UPDATED_PROMET_ULAZA_KOLICINA)
            .prometIzlazaKolicina(UPDATED_PROMET_IZLAZA_KOLICINA)
            .ukupnaKolicina(UPDATED_UKUPNA_KOLICINA)
            .pocetnoStanjeVrednosti(UPDATED_POCETNO_STANJE_VREDNOSTI)
            .prometUlazaVrednosti(UPDATED_PROMET_ULAZA_VREDNOSTI)
            .prometIzlazaVrednosti(UPDATED_PROMET_IZLAZA_VREDNOSTI)
            .ukupnaVrednost(UPDATED_UKUPNA_VREDNOST);
        return magacinskaKartica;
    }

    @BeforeEach
    public void initTest() {
        magacinskaKartica = createEntity(em);
    }

    @Test
    @Transactional
    void createMagacinskaKartica() throws Exception {
        int databaseSizeBeforeCreate = magacinskaKarticaRepository.findAll().size();
        // Create the MagacinskaKartica
        restMagacinskaKarticaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(magacinskaKartica))
            )
            .andExpect(status().isCreated());

        // Validate the MagacinskaKartica in the database
        List<MagacinskaKartica> magacinskaKarticaList = magacinskaKarticaRepository.findAll();
        assertThat(magacinskaKarticaList).hasSize(databaseSizeBeforeCreate + 1);
        MagacinskaKartica testMagacinskaKartica = magacinskaKarticaList.get(magacinskaKarticaList.size() - 1);
        assertThat(testMagacinskaKartica.getPocetnoStanjeKolicina()).isEqualTo(DEFAULT_POCETNO_STANJE_KOLICINA);
        assertThat(testMagacinskaKartica.getPrometUlazaKolicina()).isEqualTo(DEFAULT_PROMET_ULAZA_KOLICINA);
        assertThat(testMagacinskaKartica.getPrometIzlazaKolicina()).isEqualTo(DEFAULT_PROMET_IZLAZA_KOLICINA);
        assertThat(testMagacinskaKartica.getUkupnaKolicina()).isEqualTo(DEFAULT_UKUPNA_KOLICINA);
        assertThat(testMagacinskaKartica.getPocetnoStanjeVrednosti()).isEqualTo(DEFAULT_POCETNO_STANJE_VREDNOSTI);
        assertThat(testMagacinskaKartica.getPrometUlazaVrednosti()).isEqualTo(DEFAULT_PROMET_ULAZA_VREDNOSTI);
        assertThat(testMagacinskaKartica.getPrometIzlazaVrednosti()).isEqualTo(DEFAULT_PROMET_IZLAZA_VREDNOSTI);
        assertThat(testMagacinskaKartica.getUkupnaVrednost()).isEqualTo(DEFAULT_UKUPNA_VREDNOST);
    }

    @Test
    @Transactional
    void createMagacinskaKarticaWithExistingId() throws Exception {
        // Create the MagacinskaKartica with an existing ID
        magacinskaKartica.setId(1L);

        int databaseSizeBeforeCreate = magacinskaKarticaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMagacinskaKarticaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(magacinskaKartica))
            )
            .andExpect(status().isBadRequest());

        // Validate the MagacinskaKartica in the database
        List<MagacinskaKartica> magacinskaKarticaList = magacinskaKarticaRepository.findAll();
        assertThat(magacinskaKarticaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMagacinskaKarticas() throws Exception {
        // Initialize the database
        magacinskaKarticaRepository.saveAndFlush(magacinskaKartica);

        // Get all the magacinskaKarticaList
        restMagacinskaKarticaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(magacinskaKartica.getId().intValue())))
            .andExpect(jsonPath("$.[*].pocetnoStanjeKolicina").value(hasItem(DEFAULT_POCETNO_STANJE_KOLICINA.doubleValue())))
            .andExpect(jsonPath("$.[*].prometUlazaKolicina").value(hasItem(DEFAULT_PROMET_ULAZA_KOLICINA.doubleValue())))
            .andExpect(jsonPath("$.[*].prometIzlazaKolicina").value(hasItem(DEFAULT_PROMET_IZLAZA_KOLICINA.doubleValue())))
            .andExpect(jsonPath("$.[*].ukupnaKolicina").value(hasItem(DEFAULT_UKUPNA_KOLICINA.doubleValue())))
            .andExpect(jsonPath("$.[*].pocetnoStanjeVrednosti").value(hasItem(DEFAULT_POCETNO_STANJE_VREDNOSTI.doubleValue())))
            .andExpect(jsonPath("$.[*].prometUlazaVrednosti").value(hasItem(DEFAULT_PROMET_ULAZA_VREDNOSTI.doubleValue())))
            .andExpect(jsonPath("$.[*].prometIzlazaVrednosti").value(hasItem(DEFAULT_PROMET_IZLAZA_VREDNOSTI.doubleValue())))
            .andExpect(jsonPath("$.[*].ukupnaVrednost").value(hasItem(DEFAULT_UKUPNA_VREDNOST.doubleValue())));
    }

    @Test
    @Transactional
    void getMagacinskaKartica() throws Exception {
        // Initialize the database
        magacinskaKarticaRepository.saveAndFlush(magacinskaKartica);

        // Get the magacinskaKartica
        restMagacinskaKarticaMockMvc
            .perform(get(ENTITY_API_URL_ID, magacinskaKartica.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(magacinskaKartica.getId().intValue()))
            .andExpect(jsonPath("$.pocetnoStanjeKolicina").value(DEFAULT_POCETNO_STANJE_KOLICINA.doubleValue()))
            .andExpect(jsonPath("$.prometUlazaKolicina").value(DEFAULT_PROMET_ULAZA_KOLICINA.doubleValue()))
            .andExpect(jsonPath("$.prometIzlazaKolicina").value(DEFAULT_PROMET_IZLAZA_KOLICINA.doubleValue()))
            .andExpect(jsonPath("$.ukupnaKolicina").value(DEFAULT_UKUPNA_KOLICINA.doubleValue()))
            .andExpect(jsonPath("$.pocetnoStanjeVrednosti").value(DEFAULT_POCETNO_STANJE_VREDNOSTI.doubleValue()))
            .andExpect(jsonPath("$.prometUlazaVrednosti").value(DEFAULT_PROMET_ULAZA_VREDNOSTI.doubleValue()))
            .andExpect(jsonPath("$.prometIzlazaVrednosti").value(DEFAULT_PROMET_IZLAZA_VREDNOSTI.doubleValue()))
            .andExpect(jsonPath("$.ukupnaVrednost").value(DEFAULT_UKUPNA_VREDNOST.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingMagacinskaKartica() throws Exception {
        // Get the magacinskaKartica
        restMagacinskaKarticaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMagacinskaKartica() throws Exception {
        // Initialize the database
        magacinskaKarticaRepository.saveAndFlush(magacinskaKartica);

        int databaseSizeBeforeUpdate = magacinskaKarticaRepository.findAll().size();

        // Update the magacinskaKartica
        MagacinskaKartica updatedMagacinskaKartica = magacinskaKarticaRepository.findById(magacinskaKartica.getId()).get();
        // Disconnect from session so that the updates on updatedMagacinskaKartica are not directly saved in db
        em.detach(updatedMagacinskaKartica);
        updatedMagacinskaKartica
            .pocetnoStanjeKolicina(UPDATED_POCETNO_STANJE_KOLICINA)
            .prometUlazaKolicina(UPDATED_PROMET_ULAZA_KOLICINA)
            .prometIzlazaKolicina(UPDATED_PROMET_IZLAZA_KOLICINA)
            .ukupnaKolicina(UPDATED_UKUPNA_KOLICINA)
            .pocetnoStanjeVrednosti(UPDATED_POCETNO_STANJE_VREDNOSTI)
            .prometUlazaVrednosti(UPDATED_PROMET_ULAZA_VREDNOSTI)
            .prometIzlazaVrednosti(UPDATED_PROMET_IZLAZA_VREDNOSTI)
            .ukupnaVrednost(UPDATED_UKUPNA_VREDNOST);

        restMagacinskaKarticaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMagacinskaKartica.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMagacinskaKartica))
            )
            .andExpect(status().isOk());

        // Validate the MagacinskaKartica in the database
        List<MagacinskaKartica> magacinskaKarticaList = magacinskaKarticaRepository.findAll();
        assertThat(magacinskaKarticaList).hasSize(databaseSizeBeforeUpdate);
        MagacinskaKartica testMagacinskaKartica = magacinskaKarticaList.get(magacinskaKarticaList.size() - 1);
        assertThat(testMagacinskaKartica.getPocetnoStanjeKolicina()).isEqualTo(UPDATED_POCETNO_STANJE_KOLICINA);
        assertThat(testMagacinskaKartica.getPrometUlazaKolicina()).isEqualTo(UPDATED_PROMET_ULAZA_KOLICINA);
        assertThat(testMagacinskaKartica.getPrometIzlazaKolicina()).isEqualTo(UPDATED_PROMET_IZLAZA_KOLICINA);
        assertThat(testMagacinskaKartica.getUkupnaKolicina()).isEqualTo(UPDATED_UKUPNA_KOLICINA);
        assertThat(testMagacinskaKartica.getPocetnoStanjeVrednosti()).isEqualTo(UPDATED_POCETNO_STANJE_VREDNOSTI);
        assertThat(testMagacinskaKartica.getPrometUlazaVrednosti()).isEqualTo(UPDATED_PROMET_ULAZA_VREDNOSTI);
        assertThat(testMagacinskaKartica.getPrometIzlazaVrednosti()).isEqualTo(UPDATED_PROMET_IZLAZA_VREDNOSTI);
        assertThat(testMagacinskaKartica.getUkupnaVrednost()).isEqualTo(UPDATED_UKUPNA_VREDNOST);
    }

    @Test
    @Transactional
    void putNonExistingMagacinskaKartica() throws Exception {
        int databaseSizeBeforeUpdate = magacinskaKarticaRepository.findAll().size();
        magacinskaKartica.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMagacinskaKarticaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, magacinskaKartica.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(magacinskaKartica))
            )
            .andExpect(status().isBadRequest());

        // Validate the MagacinskaKartica in the database
        List<MagacinskaKartica> magacinskaKarticaList = magacinskaKarticaRepository.findAll();
        assertThat(magacinskaKarticaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMagacinskaKartica() throws Exception {
        int databaseSizeBeforeUpdate = magacinskaKarticaRepository.findAll().size();
        magacinskaKartica.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMagacinskaKarticaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(magacinskaKartica))
            )
            .andExpect(status().isBadRequest());

        // Validate the MagacinskaKartica in the database
        List<MagacinskaKartica> magacinskaKarticaList = magacinskaKarticaRepository.findAll();
        assertThat(magacinskaKarticaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMagacinskaKartica() throws Exception {
        int databaseSizeBeforeUpdate = magacinskaKarticaRepository.findAll().size();
        magacinskaKartica.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMagacinskaKarticaMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(magacinskaKartica))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MagacinskaKartica in the database
        List<MagacinskaKartica> magacinskaKarticaList = magacinskaKarticaRepository.findAll();
        assertThat(magacinskaKarticaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMagacinskaKarticaWithPatch() throws Exception {
        // Initialize the database
        magacinskaKarticaRepository.saveAndFlush(magacinskaKartica);

        int databaseSizeBeforeUpdate = magacinskaKarticaRepository.findAll().size();

        // Update the magacinskaKartica using partial update
        MagacinskaKartica partialUpdatedMagacinskaKartica = new MagacinskaKartica();
        partialUpdatedMagacinskaKartica.setId(magacinskaKartica.getId());

        partialUpdatedMagacinskaKartica
            .pocetnoStanjeKolicina(UPDATED_POCETNO_STANJE_KOLICINA)
            .prometUlazaKolicina(UPDATED_PROMET_ULAZA_KOLICINA)
            .prometIzlazaKolicina(UPDATED_PROMET_IZLAZA_KOLICINA)
            .ukupnaKolicina(UPDATED_UKUPNA_KOLICINA);

        restMagacinskaKarticaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMagacinskaKartica.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMagacinskaKartica))
            )
            .andExpect(status().isOk());

        // Validate the MagacinskaKartica in the database
        List<MagacinskaKartica> magacinskaKarticaList = magacinskaKarticaRepository.findAll();
        assertThat(magacinskaKarticaList).hasSize(databaseSizeBeforeUpdate);
        MagacinskaKartica testMagacinskaKartica = magacinskaKarticaList.get(magacinskaKarticaList.size() - 1);
        assertThat(testMagacinskaKartica.getPocetnoStanjeKolicina()).isEqualTo(UPDATED_POCETNO_STANJE_KOLICINA);
        assertThat(testMagacinskaKartica.getPrometUlazaKolicina()).isEqualTo(UPDATED_PROMET_ULAZA_KOLICINA);
        assertThat(testMagacinskaKartica.getPrometIzlazaKolicina()).isEqualTo(UPDATED_PROMET_IZLAZA_KOLICINA);
        assertThat(testMagacinskaKartica.getUkupnaKolicina()).isEqualTo(UPDATED_UKUPNA_KOLICINA);
        assertThat(testMagacinskaKartica.getPocetnoStanjeVrednosti()).isEqualTo(DEFAULT_POCETNO_STANJE_VREDNOSTI);
        assertThat(testMagacinskaKartica.getPrometUlazaVrednosti()).isEqualTo(DEFAULT_PROMET_ULAZA_VREDNOSTI);
        assertThat(testMagacinskaKartica.getPrometIzlazaVrednosti()).isEqualTo(DEFAULT_PROMET_IZLAZA_VREDNOSTI);
        assertThat(testMagacinskaKartica.getUkupnaVrednost()).isEqualTo(DEFAULT_UKUPNA_VREDNOST);
    }

    @Test
    @Transactional
    void fullUpdateMagacinskaKarticaWithPatch() throws Exception {
        // Initialize the database
        magacinskaKarticaRepository.saveAndFlush(magacinskaKartica);

        int databaseSizeBeforeUpdate = magacinskaKarticaRepository.findAll().size();

        // Update the magacinskaKartica using partial update
        MagacinskaKartica partialUpdatedMagacinskaKartica = new MagacinskaKartica();
        partialUpdatedMagacinskaKartica.setId(magacinskaKartica.getId());

        partialUpdatedMagacinskaKartica
            .pocetnoStanjeKolicina(UPDATED_POCETNO_STANJE_KOLICINA)
            .prometUlazaKolicina(UPDATED_PROMET_ULAZA_KOLICINA)
            .prometIzlazaKolicina(UPDATED_PROMET_IZLAZA_KOLICINA)
            .ukupnaKolicina(UPDATED_UKUPNA_KOLICINA)
            .pocetnoStanjeVrednosti(UPDATED_POCETNO_STANJE_VREDNOSTI)
            .prometUlazaVrednosti(UPDATED_PROMET_ULAZA_VREDNOSTI)
            .prometIzlazaVrednosti(UPDATED_PROMET_IZLAZA_VREDNOSTI)
            .ukupnaVrednost(UPDATED_UKUPNA_VREDNOST);

        restMagacinskaKarticaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMagacinskaKartica.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMagacinskaKartica))
            )
            .andExpect(status().isOk());

        // Validate the MagacinskaKartica in the database
        List<MagacinskaKartica> magacinskaKarticaList = magacinskaKarticaRepository.findAll();
        assertThat(magacinskaKarticaList).hasSize(databaseSizeBeforeUpdate);
        MagacinskaKartica testMagacinskaKartica = magacinskaKarticaList.get(magacinskaKarticaList.size() - 1);
        assertThat(testMagacinskaKartica.getPocetnoStanjeKolicina()).isEqualTo(UPDATED_POCETNO_STANJE_KOLICINA);
        assertThat(testMagacinskaKartica.getPrometUlazaKolicina()).isEqualTo(UPDATED_PROMET_ULAZA_KOLICINA);
        assertThat(testMagacinskaKartica.getPrometIzlazaKolicina()).isEqualTo(UPDATED_PROMET_IZLAZA_KOLICINA);
        assertThat(testMagacinskaKartica.getUkupnaKolicina()).isEqualTo(UPDATED_UKUPNA_KOLICINA);
        assertThat(testMagacinskaKartica.getPocetnoStanjeVrednosti()).isEqualTo(UPDATED_POCETNO_STANJE_VREDNOSTI);
        assertThat(testMagacinskaKartica.getPrometUlazaVrednosti()).isEqualTo(UPDATED_PROMET_ULAZA_VREDNOSTI);
        assertThat(testMagacinskaKartica.getPrometIzlazaVrednosti()).isEqualTo(UPDATED_PROMET_IZLAZA_VREDNOSTI);
        assertThat(testMagacinskaKartica.getUkupnaVrednost()).isEqualTo(UPDATED_UKUPNA_VREDNOST);
    }

    @Test
    @Transactional
    void patchNonExistingMagacinskaKartica() throws Exception {
        int databaseSizeBeforeUpdate = magacinskaKarticaRepository.findAll().size();
        magacinskaKartica.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMagacinskaKarticaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, magacinskaKartica.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(magacinskaKartica))
            )
            .andExpect(status().isBadRequest());

        // Validate the MagacinskaKartica in the database
        List<MagacinskaKartica> magacinskaKarticaList = magacinskaKarticaRepository.findAll();
        assertThat(magacinskaKarticaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMagacinskaKartica() throws Exception {
        int databaseSizeBeforeUpdate = magacinskaKarticaRepository.findAll().size();
        magacinskaKartica.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMagacinskaKarticaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(magacinskaKartica))
            )
            .andExpect(status().isBadRequest());

        // Validate the MagacinskaKartica in the database
        List<MagacinskaKartica> magacinskaKarticaList = magacinskaKarticaRepository.findAll();
        assertThat(magacinskaKarticaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMagacinskaKartica() throws Exception {
        int databaseSizeBeforeUpdate = magacinskaKarticaRepository.findAll().size();
        magacinskaKartica.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMagacinskaKarticaMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(magacinskaKartica))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MagacinskaKartica in the database
        List<MagacinskaKartica> magacinskaKarticaList = magacinskaKarticaRepository.findAll();
        assertThat(magacinskaKarticaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMagacinskaKartica() throws Exception {
        // Initialize the database
        magacinskaKarticaRepository.saveAndFlush(magacinskaKartica);

        int databaseSizeBeforeDelete = magacinskaKarticaRepository.findAll().size();

        // Delete the magacinskaKartica
        restMagacinskaKarticaMockMvc
            .perform(delete(ENTITY_API_URL_ID, magacinskaKartica.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MagacinskaKartica> magacinskaKarticaList = magacinskaKarticaRepository.findAll();
        assertThat(magacinskaKarticaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
