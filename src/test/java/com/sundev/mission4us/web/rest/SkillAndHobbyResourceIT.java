package com.sundev.mission4us.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.sundev.mission4us.IntegrationTest;
import com.sundev.mission4us.domain.SkillAndHobby;
import com.sundev.mission4us.domain.enumeration.OccupationAndSkillType;
import com.sundev.mission4us.repository.SkillAndHobbyRepository;
import com.sundev.mission4us.service.dto.SkillAndHobbyDTO;
import com.sundev.mission4us.service.mapper.SkillAndHobbyMapper;
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
 * Integration tests for the {@link SkillAndHobbyResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SkillAndHobbyResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final OccupationAndSkillType DEFAULT_TYPE = OccupationAndSkillType.SKILL;
    private static final OccupationAndSkillType UPDATED_TYPE = OccupationAndSkillType.HOBBY;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Long DEFAULT_RATING = 1L;
    private static final Long UPDATED_RATING = 2L;

    private static final String ENTITY_API_URL = "/api/skill-and-hobbies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SkillAndHobbyRepository skillAndHobbyRepository;

    @Autowired
    private SkillAndHobbyMapper skillAndHobbyMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSkillAndHobbyMockMvc;

    private SkillAndHobby skillAndHobby;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SkillAndHobby createEntity(EntityManager em) {
        SkillAndHobby skillAndHobby = new SkillAndHobby()
            .name(DEFAULT_NAME)
            .type(DEFAULT_TYPE)
            .description(DEFAULT_DESCRIPTION)
            .rating(DEFAULT_RATING);
        return skillAndHobby;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SkillAndHobby createUpdatedEntity(EntityManager em) {
        SkillAndHobby skillAndHobby = new SkillAndHobby()
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .description(UPDATED_DESCRIPTION)
            .rating(UPDATED_RATING);
        return skillAndHobby;
    }

    @BeforeEach
    public void initTest() {
        skillAndHobby = createEntity(em);
    }

    @Test
    @Transactional
    void createSkillAndHobby() throws Exception {
        int databaseSizeBeforeCreate = skillAndHobbyRepository.findAll().size();
        // Create the SkillAndHobby
        SkillAndHobbyDTO skillAndHobbyDTO = skillAndHobbyMapper.toDto(skillAndHobby);
        restSkillAndHobbyMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(skillAndHobbyDTO))
            )
            .andExpect(status().isCreated());

        // Validate the SkillAndHobby in the database
        List<SkillAndHobby> skillAndHobbyList = skillAndHobbyRepository.findAll();
        assertThat(skillAndHobbyList).hasSize(databaseSizeBeforeCreate + 1);
        SkillAndHobby testSkillAndHobby = skillAndHobbyList.get(skillAndHobbyList.size() - 1);
        assertThat(testSkillAndHobby.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSkillAndHobby.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testSkillAndHobby.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSkillAndHobby.getRating()).isEqualTo(DEFAULT_RATING);
    }

    @Test
    @Transactional
    void createSkillAndHobbyWithExistingId() throws Exception {
        // Create the SkillAndHobby with an existing ID
        skillAndHobby.setId(1L);
        SkillAndHobbyDTO skillAndHobbyDTO = skillAndHobbyMapper.toDto(skillAndHobby);

        int databaseSizeBeforeCreate = skillAndHobbyRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSkillAndHobbyMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(skillAndHobbyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SkillAndHobby in the database
        List<SkillAndHobby> skillAndHobbyList = skillAndHobbyRepository.findAll();
        assertThat(skillAndHobbyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSkillAndHobbies() throws Exception {
        // Initialize the database
        skillAndHobbyRepository.saveAndFlush(skillAndHobby);

        // Get all the skillAndHobbyList
        restSkillAndHobbyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(skillAndHobby.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].rating").value(hasItem(DEFAULT_RATING.intValue())));
    }

    @Test
    @Transactional
    void getSkillAndHobby() throws Exception {
        // Initialize the database
        skillAndHobbyRepository.saveAndFlush(skillAndHobby);

        // Get the skillAndHobby
        restSkillAndHobbyMockMvc
            .perform(get(ENTITY_API_URL_ID, skillAndHobby.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(skillAndHobby.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.rating").value(DEFAULT_RATING.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingSkillAndHobby() throws Exception {
        // Get the skillAndHobby
        restSkillAndHobbyMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSkillAndHobby() throws Exception {
        // Initialize the database
        skillAndHobbyRepository.saveAndFlush(skillAndHobby);

        int databaseSizeBeforeUpdate = skillAndHobbyRepository.findAll().size();

        // Update the skillAndHobby
        SkillAndHobby updatedSkillAndHobby = skillAndHobbyRepository.findById(skillAndHobby.getId()).get();
        // Disconnect from session so that the updates on updatedSkillAndHobby are not directly saved in db
        em.detach(updatedSkillAndHobby);
        updatedSkillAndHobby.name(UPDATED_NAME).type(UPDATED_TYPE).description(UPDATED_DESCRIPTION).rating(UPDATED_RATING);
        SkillAndHobbyDTO skillAndHobbyDTO = skillAndHobbyMapper.toDto(updatedSkillAndHobby);

        restSkillAndHobbyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, skillAndHobbyDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(skillAndHobbyDTO))
            )
            .andExpect(status().isOk());

        // Validate the SkillAndHobby in the database
        List<SkillAndHobby> skillAndHobbyList = skillAndHobbyRepository.findAll();
        assertThat(skillAndHobbyList).hasSize(databaseSizeBeforeUpdate);
        SkillAndHobby testSkillAndHobby = skillAndHobbyList.get(skillAndHobbyList.size() - 1);
        assertThat(testSkillAndHobby.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSkillAndHobby.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testSkillAndHobby.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSkillAndHobby.getRating()).isEqualTo(UPDATED_RATING);
    }

    @Test
    @Transactional
    void putNonExistingSkillAndHobby() throws Exception {
        int databaseSizeBeforeUpdate = skillAndHobbyRepository.findAll().size();
        skillAndHobby.setId(count.incrementAndGet());

        // Create the SkillAndHobby
        SkillAndHobbyDTO skillAndHobbyDTO = skillAndHobbyMapper.toDto(skillAndHobby);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSkillAndHobbyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, skillAndHobbyDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(skillAndHobbyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SkillAndHobby in the database
        List<SkillAndHobby> skillAndHobbyList = skillAndHobbyRepository.findAll();
        assertThat(skillAndHobbyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSkillAndHobby() throws Exception {
        int databaseSizeBeforeUpdate = skillAndHobbyRepository.findAll().size();
        skillAndHobby.setId(count.incrementAndGet());

        // Create the SkillAndHobby
        SkillAndHobbyDTO skillAndHobbyDTO = skillAndHobbyMapper.toDto(skillAndHobby);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSkillAndHobbyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(skillAndHobbyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SkillAndHobby in the database
        List<SkillAndHobby> skillAndHobbyList = skillAndHobbyRepository.findAll();
        assertThat(skillAndHobbyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSkillAndHobby() throws Exception {
        int databaseSizeBeforeUpdate = skillAndHobbyRepository.findAll().size();
        skillAndHobby.setId(count.incrementAndGet());

        // Create the SkillAndHobby
        SkillAndHobbyDTO skillAndHobbyDTO = skillAndHobbyMapper.toDto(skillAndHobby);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSkillAndHobbyMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(skillAndHobbyDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SkillAndHobby in the database
        List<SkillAndHobby> skillAndHobbyList = skillAndHobbyRepository.findAll();
        assertThat(skillAndHobbyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSkillAndHobbyWithPatch() throws Exception {
        // Initialize the database
        skillAndHobbyRepository.saveAndFlush(skillAndHobby);

        int databaseSizeBeforeUpdate = skillAndHobbyRepository.findAll().size();

        // Update the skillAndHobby using partial update
        SkillAndHobby partialUpdatedSkillAndHobby = new SkillAndHobby();
        partialUpdatedSkillAndHobby.setId(skillAndHobby.getId());

        restSkillAndHobbyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSkillAndHobby.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSkillAndHobby))
            )
            .andExpect(status().isOk());

        // Validate the SkillAndHobby in the database
        List<SkillAndHobby> skillAndHobbyList = skillAndHobbyRepository.findAll();
        assertThat(skillAndHobbyList).hasSize(databaseSizeBeforeUpdate);
        SkillAndHobby testSkillAndHobby = skillAndHobbyList.get(skillAndHobbyList.size() - 1);
        assertThat(testSkillAndHobby.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSkillAndHobby.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testSkillAndHobby.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSkillAndHobby.getRating()).isEqualTo(DEFAULT_RATING);
    }

    @Test
    @Transactional
    void fullUpdateSkillAndHobbyWithPatch() throws Exception {
        // Initialize the database
        skillAndHobbyRepository.saveAndFlush(skillAndHobby);

        int databaseSizeBeforeUpdate = skillAndHobbyRepository.findAll().size();

        // Update the skillAndHobby using partial update
        SkillAndHobby partialUpdatedSkillAndHobby = new SkillAndHobby();
        partialUpdatedSkillAndHobby.setId(skillAndHobby.getId());

        partialUpdatedSkillAndHobby.name(UPDATED_NAME).type(UPDATED_TYPE).description(UPDATED_DESCRIPTION).rating(UPDATED_RATING);

        restSkillAndHobbyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSkillAndHobby.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSkillAndHobby))
            )
            .andExpect(status().isOk());

        // Validate the SkillAndHobby in the database
        List<SkillAndHobby> skillAndHobbyList = skillAndHobbyRepository.findAll();
        assertThat(skillAndHobbyList).hasSize(databaseSizeBeforeUpdate);
        SkillAndHobby testSkillAndHobby = skillAndHobbyList.get(skillAndHobbyList.size() - 1);
        assertThat(testSkillAndHobby.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSkillAndHobby.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testSkillAndHobby.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSkillAndHobby.getRating()).isEqualTo(UPDATED_RATING);
    }

    @Test
    @Transactional
    void patchNonExistingSkillAndHobby() throws Exception {
        int databaseSizeBeforeUpdate = skillAndHobbyRepository.findAll().size();
        skillAndHobby.setId(count.incrementAndGet());

        // Create the SkillAndHobby
        SkillAndHobbyDTO skillAndHobbyDTO = skillAndHobbyMapper.toDto(skillAndHobby);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSkillAndHobbyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, skillAndHobbyDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(skillAndHobbyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SkillAndHobby in the database
        List<SkillAndHobby> skillAndHobbyList = skillAndHobbyRepository.findAll();
        assertThat(skillAndHobbyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSkillAndHobby() throws Exception {
        int databaseSizeBeforeUpdate = skillAndHobbyRepository.findAll().size();
        skillAndHobby.setId(count.incrementAndGet());

        // Create the SkillAndHobby
        SkillAndHobbyDTO skillAndHobbyDTO = skillAndHobbyMapper.toDto(skillAndHobby);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSkillAndHobbyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(skillAndHobbyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SkillAndHobby in the database
        List<SkillAndHobby> skillAndHobbyList = skillAndHobbyRepository.findAll();
        assertThat(skillAndHobbyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSkillAndHobby() throws Exception {
        int databaseSizeBeforeUpdate = skillAndHobbyRepository.findAll().size();
        skillAndHobby.setId(count.incrementAndGet());

        // Create the SkillAndHobby
        SkillAndHobbyDTO skillAndHobbyDTO = skillAndHobbyMapper.toDto(skillAndHobby);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSkillAndHobbyMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(skillAndHobbyDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SkillAndHobby in the database
        List<SkillAndHobby> skillAndHobbyList = skillAndHobbyRepository.findAll();
        assertThat(skillAndHobbyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSkillAndHobby() throws Exception {
        // Initialize the database
        skillAndHobbyRepository.saveAndFlush(skillAndHobby);

        int databaseSizeBeforeDelete = skillAndHobbyRepository.findAll().size();

        // Delete the skillAndHobby
        restSkillAndHobbyMockMvc
            .perform(delete(ENTITY_API_URL_ID, skillAndHobby.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SkillAndHobby> skillAndHobbyList = skillAndHobbyRepository.findAll();
        assertThat(skillAndHobbyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
