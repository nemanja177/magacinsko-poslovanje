package com.magacin.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.magacin.IntegrationTest;
import com.magacin.domain.PoslovanGodina;
import com.magacin.repository.PoslovanGodinaRepository;
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
 * Integration tests for the {@link PoslovanGodinaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PoslovanGodinaResourceIT {

    private static final Long DEFAULT_GODINA = 1L;
    private static final Long UPDATED_GODINA = 2L;

    private static final Boolean DEFAULT_ZAKLJUCENA = false;
    private static final Boolean UPDATED_ZAKLJUCENA = true;

    private static final String ENTITY_API_URL = "/api/poslovan-godinas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PoslovanGodinaRepository poslovanGodinaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPoslovanGodinaMockMvc;

    private PoslovanGodina poslovanGodina;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PoslovanGodina createEntity(EntityManager em) {
        PoslovanGodina poslovanGodina = new PoslovanGodina().godina(DEFAULT_GODINA).zakljucena(DEFAULT_ZAKLJUCENA);
        return poslovanGodina;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PoslovanGodina createUpdatedEntity(EntityManager em) {
        PoslovanGodina poslovanGodina = new PoslovanGodina().godina(UPDATED_GODINA).zakljucena(UPDATED_ZAKLJUCENA);
        return poslovanGodina;
    }

    @BeforeEach
    public void initTest() {
        poslovanGodina = createEntity(em);
    }

    @Test
    @Transactional
    void createPoslovanGodina() throws Exception {
        int databaseSizeBeforeCreate = poslovanGodinaRepository.findAll().size();
        // Create the PoslovanGodina
        restPoslovanGodinaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(poslovanGodina))
            )
            .andExpect(status().isCreated());

        // Validate the PoslovanGodina in the database
        List<PoslovanGodina> poslovanGodinaList = poslovanGodinaRepository.findAll();
        assertThat(poslovanGodinaList).hasSize(databaseSizeBeforeCreate + 1);
        PoslovanGodina testPoslovanGodina = poslovanGodinaList.get(poslovanGodinaList.size() - 1);
        assertThat(testPoslovanGodina.getGodina()).isEqualTo(DEFAULT_GODINA);
        assertThat(testPoslovanGodina.getZakljucena()).isEqualTo(DEFAULT_ZAKLJUCENA);
    }

    @Test
    @Transactional
    void createPoslovanGodinaWithExistingId() throws Exception {
        // Create the PoslovanGodina with an existing ID
        poslovanGodina.setId(1L);

        int databaseSizeBeforeCreate = poslovanGodinaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPoslovanGodinaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(poslovanGodina))
            )
            .andExpect(status().isBadRequest());

        // Validate the PoslovanGodina in the database
        List<PoslovanGodina> poslovanGodinaList = poslovanGodinaRepository.findAll();
        assertThat(poslovanGodinaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPoslovanGodinas() throws Exception {
        // Initialize the database
        poslovanGodinaRepository.saveAndFlush(poslovanGodina);

        // Get all the poslovanGodinaList
        restPoslovanGodinaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(poslovanGodina.getId().intValue())))
            .andExpect(jsonPath("$.[*].godina").value(hasItem(DEFAULT_GODINA.intValue())))
            .andExpect(jsonPath("$.[*].zakljucena").value(hasItem(DEFAULT_ZAKLJUCENA.booleanValue())));
    }

    @Test
    @Transactional
    void getPoslovanGodina() throws Exception {
        // Initialize the database
        poslovanGodinaRepository.saveAndFlush(poslovanGodina);

        // Get the poslovanGodina
        restPoslovanGodinaMockMvc
            .perform(get(ENTITY_API_URL_ID, poslovanGodina.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(poslovanGodina.getId().intValue()))
            .andExpect(jsonPath("$.godina").value(DEFAULT_GODINA.intValue()))
            .andExpect(jsonPath("$.zakljucena").value(DEFAULT_ZAKLJUCENA.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingPoslovanGodina() throws Exception {
        // Get the poslovanGodina
        restPoslovanGodinaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPoslovanGodina() throws Exception {
        // Initialize the database
        poslovanGodinaRepository.saveAndFlush(poslovanGodina);

        int databaseSizeBeforeUpdate = poslovanGodinaRepository.findAll().size();

        // Update the poslovanGodina
        PoslovanGodina updatedPoslovanGodina = poslovanGodinaRepository.findById(poslovanGodina.getId()).get();
        // Disconnect from session so that the updates on updatedPoslovanGodina are not directly saved in db
        em.detach(updatedPoslovanGodina);
        updatedPoslovanGodina.godina(UPDATED_GODINA).zakljucena(UPDATED_ZAKLJUCENA);

        restPoslovanGodinaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPoslovanGodina.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPoslovanGodina))
            )
            .andExpect(status().isOk());

        // Validate the PoslovanGodina in the database
        List<PoslovanGodina> poslovanGodinaList = poslovanGodinaRepository.findAll();
        assertThat(poslovanGodinaList).hasSize(databaseSizeBeforeUpdate);
        PoslovanGodina testPoslovanGodina = poslovanGodinaList.get(poslovanGodinaList.size() - 1);
        assertThat(testPoslovanGodina.getGodina()).isEqualTo(UPDATED_GODINA);
        assertThat(testPoslovanGodina.getZakljucena()).isEqualTo(UPDATED_ZAKLJUCENA);
    }

    @Test
    @Transactional
    void putNonExistingPoslovanGodina() throws Exception {
        int databaseSizeBeforeUpdate = poslovanGodinaRepository.findAll().size();
        poslovanGodina.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPoslovanGodinaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, poslovanGodina.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(poslovanGodina))
            )
            .andExpect(status().isBadRequest());

        // Validate the PoslovanGodina in the database
        List<PoslovanGodina> poslovanGodinaList = poslovanGodinaRepository.findAll();
        assertThat(poslovanGodinaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPoslovanGodina() throws Exception {
        int databaseSizeBeforeUpdate = poslovanGodinaRepository.findAll().size();
        poslovanGodina.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPoslovanGodinaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(poslovanGodina))
            )
            .andExpect(status().isBadRequest());

        // Validate the PoslovanGodina in the database
        List<PoslovanGodina> poslovanGodinaList = poslovanGodinaRepository.findAll();
        assertThat(poslovanGodinaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPoslovanGodina() throws Exception {
        int databaseSizeBeforeUpdate = poslovanGodinaRepository.findAll().size();
        poslovanGodina.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPoslovanGodinaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(poslovanGodina)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PoslovanGodina in the database
        List<PoslovanGodina> poslovanGodinaList = poslovanGodinaRepository.findAll();
        assertThat(poslovanGodinaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePoslovanGodinaWithPatch() throws Exception {
        // Initialize the database
        poslovanGodinaRepository.saveAndFlush(poslovanGodina);

        int databaseSizeBeforeUpdate = poslovanGodinaRepository.findAll().size();

        // Update the poslovanGodina using partial update
        PoslovanGodina partialUpdatedPoslovanGodina = new PoslovanGodina();
        partialUpdatedPoslovanGodina.setId(poslovanGodina.getId());

        partialUpdatedPoslovanGodina.zakljucena(UPDATED_ZAKLJUCENA);

        restPoslovanGodinaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPoslovanGodina.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPoslovanGodina))
            )
            .andExpect(status().isOk());

        // Validate the PoslovanGodina in the database
        List<PoslovanGodina> poslovanGodinaList = poslovanGodinaRepository.findAll();
        assertThat(poslovanGodinaList).hasSize(databaseSizeBeforeUpdate);
        PoslovanGodina testPoslovanGodina = poslovanGodinaList.get(poslovanGodinaList.size() - 1);
        assertThat(testPoslovanGodina.getGodina()).isEqualTo(DEFAULT_GODINA);
        assertThat(testPoslovanGodina.getZakljucena()).isEqualTo(UPDATED_ZAKLJUCENA);
    }

    @Test
    @Transactional
    void fullUpdatePoslovanGodinaWithPatch() throws Exception {
        // Initialize the database
        poslovanGodinaRepository.saveAndFlush(poslovanGodina);

        int databaseSizeBeforeUpdate = poslovanGodinaRepository.findAll().size();

        // Update the poslovanGodina using partial update
        PoslovanGodina partialUpdatedPoslovanGodina = new PoslovanGodina();
        partialUpdatedPoslovanGodina.setId(poslovanGodina.getId());

        partialUpdatedPoslovanGodina.godina(UPDATED_GODINA).zakljucena(UPDATED_ZAKLJUCENA);

        restPoslovanGodinaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPoslovanGodina.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPoslovanGodina))
            )
            .andExpect(status().isOk());

        // Validate the PoslovanGodina in the database
        List<PoslovanGodina> poslovanGodinaList = poslovanGodinaRepository.findAll();
        assertThat(poslovanGodinaList).hasSize(databaseSizeBeforeUpdate);
        PoslovanGodina testPoslovanGodina = poslovanGodinaList.get(poslovanGodinaList.size() - 1);
        assertThat(testPoslovanGodina.getGodina()).isEqualTo(UPDATED_GODINA);
        assertThat(testPoslovanGodina.getZakljucena()).isEqualTo(UPDATED_ZAKLJUCENA);
    }

    @Test
    @Transactional
    void patchNonExistingPoslovanGodina() throws Exception {
        int databaseSizeBeforeUpdate = poslovanGodinaRepository.findAll().size();
        poslovanGodina.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPoslovanGodinaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, poslovanGodina.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(poslovanGodina))
            )
            .andExpect(status().isBadRequest());

        // Validate the PoslovanGodina in the database
        List<PoslovanGodina> poslovanGodinaList = poslovanGodinaRepository.findAll();
        assertThat(poslovanGodinaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPoslovanGodina() throws Exception {
        int databaseSizeBeforeUpdate = poslovanGodinaRepository.findAll().size();
        poslovanGodina.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPoslovanGodinaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(poslovanGodina))
            )
            .andExpect(status().isBadRequest());

        // Validate the PoslovanGodina in the database
        List<PoslovanGodina> poslovanGodinaList = poslovanGodinaRepository.findAll();
        assertThat(poslovanGodinaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPoslovanGodina() throws Exception {
        int databaseSizeBeforeUpdate = poslovanGodinaRepository.findAll().size();
        poslovanGodina.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPoslovanGodinaMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(poslovanGodina))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PoslovanGodina in the database
        List<PoslovanGodina> poslovanGodinaList = poslovanGodinaRepository.findAll();
        assertThat(poslovanGodinaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePoslovanGodina() throws Exception {
        // Initialize the database
        poslovanGodinaRepository.saveAndFlush(poslovanGodina);

        int databaseSizeBeforeDelete = poslovanGodinaRepository.findAll().size();

        // Delete the poslovanGodina
        restPoslovanGodinaMockMvc
            .perform(delete(ENTITY_API_URL_ID, poslovanGodina.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PoslovanGodina> poslovanGodinaList = poslovanGodinaRepository.findAll();
        assertThat(poslovanGodinaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
