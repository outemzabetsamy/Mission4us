package com.sundev.mission4us.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.sundev.mission4us.IntegrationTest;
import com.sundev.mission4us.domain.DriverLicence;
import com.sundev.mission4us.repository.DriverLicenceRepository;
import com.sundev.mission4us.service.dto.DriverLicenceDTO;
import com.sundev.mission4us.service.mapper.DriverLicenceMapper;
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
 * Integration tests for the {@link DriverLicenceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DriverLicenceResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/driver-licences";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DriverLicenceRepository driverLicenceRepository;

    @Autowired
    private DriverLicenceMapper driverLicenceMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDriverLicenceMockMvc;

    private DriverLicence driverLicence;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DriverLicence createEntity(EntityManager em) {
        DriverLicence driverLicence = new DriverLicence().name(DEFAULT_NAME);
        return driverLicence;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DriverLicence createUpdatedEntity(EntityManager em) {
        DriverLicence driverLicence = new DriverLicence().name(UPDATED_NAME);
        return driverLicence;
    }

    @BeforeEach
    public void initTest() {
        driverLicence = createEntity(em);
    }

    @Test
    @Transactional
    void createDriverLicence() throws Exception {
        int databaseSizeBeforeCreate = driverLicenceRepository.findAll().size();
        // Create the DriverLicence
        DriverLicenceDTO driverLicenceDTO = driverLicenceMapper.toDto(driverLicence);
        restDriverLicenceMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(driverLicenceDTO))
            )
            .andExpect(status().isCreated());

        // Validate the DriverLicence in the database
        List<DriverLicence> driverLicenceList = driverLicenceRepository.findAll();
        assertThat(driverLicenceList).hasSize(databaseSizeBeforeCreate + 1);
        DriverLicence testDriverLicence = driverLicenceList.get(driverLicenceList.size() - 1);
        assertThat(testDriverLicence.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createDriverLicenceWithExistingId() throws Exception {
        // Create the DriverLicence with an existing ID
        driverLicence.setId(1L);
        DriverLicenceDTO driverLicenceDTO = driverLicenceMapper.toDto(driverLicence);

        int databaseSizeBeforeCreate = driverLicenceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDriverLicenceMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(driverLicenceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DriverLicence in the database
        List<DriverLicence> driverLicenceList = driverLicenceRepository.findAll();
        assertThat(driverLicenceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDriverLicences() throws Exception {
        // Initialize the database
        driverLicenceRepository.saveAndFlush(driverLicence);

        // Get all the driverLicenceList
        restDriverLicenceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(driverLicence.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getDriverLicence() throws Exception {
        // Initialize the database
        driverLicenceRepository.saveAndFlush(driverLicence);

        // Get the driverLicence
        restDriverLicenceMockMvc
            .perform(get(ENTITY_API_URL_ID, driverLicence.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(driverLicence.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingDriverLicence() throws Exception {
        // Get the driverLicence
        restDriverLicenceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDriverLicence() throws Exception {
        // Initialize the database
        driverLicenceRepository.saveAndFlush(driverLicence);

        int databaseSizeBeforeUpdate = driverLicenceRepository.findAll().size();

        // Update the driverLicence
        DriverLicence updatedDriverLicence = driverLicenceRepository.findById(driverLicence.getId()).get();
        // Disconnect from session so that the updates on updatedDriverLicence are not directly saved in db
        em.detach(updatedDriverLicence);
        updatedDriverLicence.name(UPDATED_NAME);
        DriverLicenceDTO driverLicenceDTO = driverLicenceMapper.toDto(updatedDriverLicence);

        restDriverLicenceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, driverLicenceDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(driverLicenceDTO))
            )
            .andExpect(status().isOk());

        // Validate the DriverLicence in the database
        List<DriverLicence> driverLicenceList = driverLicenceRepository.findAll();
        assertThat(driverLicenceList).hasSize(databaseSizeBeforeUpdate);
        DriverLicence testDriverLicence = driverLicenceList.get(driverLicenceList.size() - 1);
        assertThat(testDriverLicence.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingDriverLicence() throws Exception {
        int databaseSizeBeforeUpdate = driverLicenceRepository.findAll().size();
        driverLicence.setId(count.incrementAndGet());

        // Create the DriverLicence
        DriverLicenceDTO driverLicenceDTO = driverLicenceMapper.toDto(driverLicence);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDriverLicenceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, driverLicenceDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(driverLicenceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DriverLicence in the database
        List<DriverLicence> driverLicenceList = driverLicenceRepository.findAll();
        assertThat(driverLicenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDriverLicence() throws Exception {
        int databaseSizeBeforeUpdate = driverLicenceRepository.findAll().size();
        driverLicence.setId(count.incrementAndGet());

        // Create the DriverLicence
        DriverLicenceDTO driverLicenceDTO = driverLicenceMapper.toDto(driverLicence);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDriverLicenceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(driverLicenceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DriverLicence in the database
        List<DriverLicence> driverLicenceList = driverLicenceRepository.findAll();
        assertThat(driverLicenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDriverLicence() throws Exception {
        int databaseSizeBeforeUpdate = driverLicenceRepository.findAll().size();
        driverLicence.setId(count.incrementAndGet());

        // Create the DriverLicence
        DriverLicenceDTO driverLicenceDTO = driverLicenceMapper.toDto(driverLicence);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDriverLicenceMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(driverLicenceDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DriverLicence in the database
        List<DriverLicence> driverLicenceList = driverLicenceRepository.findAll();
        assertThat(driverLicenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDriverLicenceWithPatch() throws Exception {
        // Initialize the database
        driverLicenceRepository.saveAndFlush(driverLicence);

        int databaseSizeBeforeUpdate = driverLicenceRepository.findAll().size();

        // Update the driverLicence using partial update
        DriverLicence partialUpdatedDriverLicence = new DriverLicence();
        partialUpdatedDriverLicence.setId(driverLicence.getId());

        restDriverLicenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDriverLicence.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDriverLicence))
            )
            .andExpect(status().isOk());

        // Validate the DriverLicence in the database
        List<DriverLicence> driverLicenceList = driverLicenceRepository.findAll();
        assertThat(driverLicenceList).hasSize(databaseSizeBeforeUpdate);
        DriverLicence testDriverLicence = driverLicenceList.get(driverLicenceList.size() - 1);
        assertThat(testDriverLicence.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void fullUpdateDriverLicenceWithPatch() throws Exception {
        // Initialize the database
        driverLicenceRepository.saveAndFlush(driverLicence);

        int databaseSizeBeforeUpdate = driverLicenceRepository.findAll().size();

        // Update the driverLicence using partial update
        DriverLicence partialUpdatedDriverLicence = new DriverLicence();
        partialUpdatedDriverLicence.setId(driverLicence.getId());

        partialUpdatedDriverLicence.name(UPDATED_NAME);

        restDriverLicenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDriverLicence.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDriverLicence))
            )
            .andExpect(status().isOk());

        // Validate the DriverLicence in the database
        List<DriverLicence> driverLicenceList = driverLicenceRepository.findAll();
        assertThat(driverLicenceList).hasSize(databaseSizeBeforeUpdate);
        DriverLicence testDriverLicence = driverLicenceList.get(driverLicenceList.size() - 1);
        assertThat(testDriverLicence.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingDriverLicence() throws Exception {
        int databaseSizeBeforeUpdate = driverLicenceRepository.findAll().size();
        driverLicence.setId(count.incrementAndGet());

        // Create the DriverLicence
        DriverLicenceDTO driverLicenceDTO = driverLicenceMapper.toDto(driverLicence);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDriverLicenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, driverLicenceDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(driverLicenceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DriverLicence in the database
        List<DriverLicence> driverLicenceList = driverLicenceRepository.findAll();
        assertThat(driverLicenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDriverLicence() throws Exception {
        int databaseSizeBeforeUpdate = driverLicenceRepository.findAll().size();
        driverLicence.setId(count.incrementAndGet());

        // Create the DriverLicence
        DriverLicenceDTO driverLicenceDTO = driverLicenceMapper.toDto(driverLicence);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDriverLicenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(driverLicenceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DriverLicence in the database
        List<DriverLicence> driverLicenceList = driverLicenceRepository.findAll();
        assertThat(driverLicenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDriverLicence() throws Exception {
        int databaseSizeBeforeUpdate = driverLicenceRepository.findAll().size();
        driverLicence.setId(count.incrementAndGet());

        // Create the DriverLicence
        DriverLicenceDTO driverLicenceDTO = driverLicenceMapper.toDto(driverLicence);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDriverLicenceMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(driverLicenceDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DriverLicence in the database
        List<DriverLicence> driverLicenceList = driverLicenceRepository.findAll();
        assertThat(driverLicenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDriverLicence() throws Exception {
        // Initialize the database
        driverLicenceRepository.saveAndFlush(driverLicence);

        int databaseSizeBeforeDelete = driverLicenceRepository.findAll().size();

        // Delete the driverLicence
        restDriverLicenceMockMvc
            .perform(delete(ENTITY_API_URL_ID, driverLicence.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DriverLicence> driverLicenceList = driverLicenceRepository.findAll();
        assertThat(driverLicenceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
