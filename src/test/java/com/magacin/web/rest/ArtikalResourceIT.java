package com.magacin.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.magacin.IntegrationTest;
import com.magacin.domain.Artikal;
import com.magacin.repository.ArtikalRepository;
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
 * Integration tests for the {@link ArtikalResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ArtikalResourceIT {

    private static final String DEFAULT_NAZIV = "AAAAAAAAAA";
    private static final String UPDATED_NAZIV = "BBBBBBBBBB";

    private static final String DEFAULT_OPIS = "AAAAAAAAAA";
    private static final String UPDATED_OPIS = "BBBBBBBBBB";

    private static final String DEFAULT_PAKOVANJE = "AAAAAAAAAA";
    private static final String UPDATED_PAKOVANJE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/artikals";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ArtikalRepository artikalRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restArtikalMockMvc;

    private Artikal artikal;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Artikal createEntity(EntityManager em) {
        Artikal artikal = new Artikal().naziv(DEFAULT_NAZIV).opis(DEFAULT_OPIS).pakovanje(DEFAULT_PAKOVANJE);
        return artikal;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Artikal createUpdatedEntity(EntityManager em) {
        Artikal artikal = new Artikal().naziv(UPDATED_NAZIV).opis(UPDATED_OPIS).pakovanje(UPDATED_PAKOVANJE);
        return artikal;
    }

    @BeforeEach
    public void initTest() {
        artikal = createEntity(em);
    }

    @Test
    @Transactional
    void createArtikal() throws Exception {
        int databaseSizeBeforeCreate = artikalRepository.findAll().size();
        // Create the Artikal
        restArtikalMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(artikal)))
            .andExpect(status().isCreated());

        // Validate the Artikal in the database
        List<Artikal> artikalList = artikalRepository.findAll();
        assertThat(artikalList).hasSize(databaseSizeBeforeCreate + 1);
        Artikal testArtikal = artikalList.get(artikalList.size() - 1);
        assertThat(testArtikal.getNaziv()).isEqualTo(DEFAULT_NAZIV);
        assertThat(testArtikal.getOpis()).isEqualTo(DEFAULT_OPIS);
        assertThat(testArtikal.getPakovanje()).isEqualTo(DEFAULT_PAKOVANJE);
    }

    @Test
    @Transactional
    void createArtikalWithExistingId() throws Exception {
        // Create the Artikal with an existing ID
        artikal.setId(1L);

        int databaseSizeBeforeCreate = artikalRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restArtikalMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(artikal)))
            .andExpect(status().isBadRequest());

        // Validate the Artikal in the database
        List<Artikal> artikalList = artikalRepository.findAll();
        assertThat(artikalList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllArtikals() throws Exception {
        // Initialize the database
        artikalRepository.saveAndFlush(artikal);

        // Get all the artikalList
        restArtikalMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(artikal.getId().intValue())))
            .andExpect(jsonPath("$.[*].naziv").value(hasItem(DEFAULT_NAZIV)))
            .andExpect(jsonPath("$.[*].opis").value(hasItem(DEFAULT_OPIS)))
            .andExpect(jsonPath("$.[*].pakovanje").value(hasItem(DEFAULT_PAKOVANJE)));
    }

    @Test
    @Transactional
    void getArtikal() throws Exception {
        // Initialize the database
        artikalRepository.saveAndFlush(artikal);

        // Get the artikal
        restArtikalMockMvc
            .perform(get(ENTITY_API_URL_ID, artikal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(artikal.getId().intValue()))
            .andExpect(jsonPath("$.naziv").value(DEFAULT_NAZIV))
            .andExpect(jsonPath("$.opis").value(DEFAULT_OPIS))
            .andExpect(jsonPath("$.pakovanje").value(DEFAULT_PAKOVANJE));
    }

    @Test
    @Transactional
    void getNonExistingArtikal() throws Exception {
        // Get the artikal
        restArtikalMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingArtikal() throws Exception {
        // Initialize the database
        artikalRepository.saveAndFlush(artikal);

        int databaseSizeBeforeUpdate = artikalRepository.findAll().size();

        // Update the artikal
        Artikal updatedArtikal = artikalRepository.findById(artikal.getId()).get();
        // Disconnect from session so that the updates on updatedArtikal are not directly saved in db
        em.detach(updatedArtikal);
        updatedArtikal.naziv(UPDATED_NAZIV).opis(UPDATED_OPIS).pakovanje(UPDATED_PAKOVANJE);

        restArtikalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedArtikal.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedArtikal))
            )
            .andExpect(status().isOk());

        // Validate the Artikal in the database
        List<Artikal> artikalList = artikalRepository.findAll();
        assertThat(artikalList).hasSize(databaseSizeBeforeUpdate);
        Artikal testArtikal = artikalList.get(artikalList.size() - 1);
        assertThat(testArtikal.getNaziv()).isEqualTo(UPDATED_NAZIV);
        assertThat(testArtikal.getOpis()).isEqualTo(UPDATED_OPIS);
        assertThat(testArtikal.getPakovanje()).isEqualTo(UPDATED_PAKOVANJE);
    }

    @Test
    @Transactional
    void putNonExistingArtikal() throws Exception {
        int databaseSizeBeforeUpdate = artikalRepository.findAll().size();
        artikal.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArtikalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, artikal.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(artikal))
            )
            .andExpect(status().isBadRequest());

        // Validate the Artikal in the database
        List<Artikal> artikalList = artikalRepository.findAll();
        assertThat(artikalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchArtikal() throws Exception {
        int databaseSizeBeforeUpdate = artikalRepository.findAll().size();
        artikal.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArtikalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(artikal))
            )
            .andExpect(status().isBadRequest());

        // Validate the Artikal in the database
        List<Artikal> artikalList = artikalRepository.findAll();
        assertThat(artikalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamArtikal() throws Exception {
        int databaseSizeBeforeUpdate = artikalRepository.findAll().size();
        artikal.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArtikalMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(artikal)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Artikal in the database
        List<Artikal> artikalList = artikalRepository.findAll();
        assertThat(artikalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateArtikalWithPatch() throws Exception {
        // Initialize the database
        artikalRepository.saveAndFlush(artikal);

        int databaseSizeBeforeUpdate = artikalRepository.findAll().size();

        // Update the artikal using partial update
        Artikal partialUpdatedArtikal = new Artikal();
        partialUpdatedArtikal.setId(artikal.getId());

        partialUpdatedArtikal.naziv(UPDATED_NAZIV);

        restArtikalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArtikal.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedArtikal))
            )
            .andExpect(status().isOk());

        // Validate the Artikal in the database
        List<Artikal> artikalList = artikalRepository.findAll();
        assertThat(artikalList).hasSize(databaseSizeBeforeUpdate);
        Artikal testArtikal = artikalList.get(artikalList.size() - 1);
        assertThat(testArtikal.getNaziv()).isEqualTo(UPDATED_NAZIV);
        assertThat(testArtikal.getOpis()).isEqualTo(DEFAULT_OPIS);
        assertThat(testArtikal.getPakovanje()).isEqualTo(DEFAULT_PAKOVANJE);
    }

    @Test
    @Transactional
    void fullUpdateArtikalWithPatch() throws Exception {
        // Initialize the database
        artikalRepository.saveAndFlush(artikal);

        int databaseSizeBeforeUpdate = artikalRepository.findAll().size();

        // Update the artikal using partial update
        Artikal partialUpdatedArtikal = new Artikal();
        partialUpdatedArtikal.setId(artikal.getId());

        partialUpdatedArtikal.naziv(UPDATED_NAZIV).opis(UPDATED_OPIS).pakovanje(UPDATED_PAKOVANJE);

        restArtikalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArtikal.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedArtikal))
            )
            .andExpect(status().isOk());

        // Validate the Artikal in the database
        List<Artikal> artikalList = artikalRepository.findAll();
        assertThat(artikalList).hasSize(databaseSizeBeforeUpdate);
        Artikal testArtikal = artikalList.get(artikalList.size() - 1);
        assertThat(testArtikal.getNaziv()).isEqualTo(UPDATED_NAZIV);
        assertThat(testArtikal.getOpis()).isEqualTo(UPDATED_OPIS);
        assertThat(testArtikal.getPakovanje()).isEqualTo(UPDATED_PAKOVANJE);
    }

    @Test
    @Transactional
    void patchNonExistingArtikal() throws Exception {
        int databaseSizeBeforeUpdate = artikalRepository.findAll().size();
        artikal.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArtikalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, artikal.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(artikal))
            )
            .andExpect(status().isBadRequest());

        // Validate the Artikal in the database
        List<Artikal> artikalList = artikalRepository.findAll();
        assertThat(artikalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchArtikal() throws Exception {
        int databaseSizeBeforeUpdate = artikalRepository.findAll().size();
        artikal.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArtikalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(artikal))
            )
            .andExpect(status().isBadRequest());

        // Validate the Artikal in the database
        List<Artikal> artikalList = artikalRepository.findAll();
        assertThat(artikalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamArtikal() throws Exception {
        int databaseSizeBeforeUpdate = artikalRepository.findAll().size();
        artikal.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArtikalMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(artikal)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Artikal in the database
        List<Artikal> artikalList = artikalRepository.findAll();
        assertThat(artikalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteArtikal() throws Exception {
        // Initialize the database
        artikalRepository.saveAndFlush(artikal);

        int databaseSizeBeforeDelete = artikalRepository.findAll().size();

        // Delete the artikal
        restArtikalMockMvc
            .perform(delete(ENTITY_API_URL_ID, artikal.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Artikal> artikalList = artikalRepository.findAll();
        assertThat(artikalList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
