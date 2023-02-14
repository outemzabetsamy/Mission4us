package com.sundev.mission4us.service;

import com.sundev.mission4us.domain.SkillAndHobby;
import com.sundev.mission4us.repository.SkillAndHobbyRepository;
import com.sundev.mission4us.service.dto.SkillAndHobbyDTO;
import com.sundev.mission4us.service.mapper.SkillAndHobbyMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SkillAndHobby}.
 */
@Service
@Transactional
public class SkillAndHobbyService {

    private final Logger log = LoggerFactory.getLogger(SkillAndHobbyService.class);

    private final SkillAndHobbyRepository skillAndHobbyRepository;

    private final SkillAndHobbyMapper skillAndHobbyMapper;

    public SkillAndHobbyService(SkillAndHobbyRepository skillAndHobbyRepository, SkillAndHobbyMapper skillAndHobbyMapper) {
        this.skillAndHobbyRepository = skillAndHobbyRepository;
        this.skillAndHobbyMapper = skillAndHobbyMapper;
    }

    /**
     * Save a skillAndHobby.
     *
     * @param skillAndHobbyDTO the entity to save.
     * @return the persisted entity.
     */
    public SkillAndHobbyDTO save(SkillAndHobbyDTO skillAndHobbyDTO) {
        log.debug("Request to save SkillAndHobby : {}", skillAndHobbyDTO);
        SkillAndHobby skillAndHobby = skillAndHobbyMapper.toEntity(skillAndHobbyDTO);
        skillAndHobby = skillAndHobbyRepository.save(skillAndHobby);
        return skillAndHobbyMapper.toDto(skillAndHobby);
    }

    /**
     * Partially update a skillAndHobby.
     *
     * @param skillAndHobbyDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SkillAndHobbyDTO> partialUpdate(SkillAndHobbyDTO skillAndHobbyDTO) {
        log.debug("Request to partially update SkillAndHobby : {}", skillAndHobbyDTO);

        return skillAndHobbyRepository
            .findById(skillAndHobbyDTO.getId())
            .map(existingSkillAndHobby -> {
                skillAndHobbyMapper.partialUpdate(existingSkillAndHobby, skillAndHobbyDTO);

                return existingSkillAndHobby;
            })
            .map(skillAndHobbyRepository::save)
            .map(skillAndHobbyMapper::toDto);
    }

    /**
     * Get all the skillAndHobbies.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SkillAndHobbyDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SkillAndHobbies");
        return skillAndHobbyRepository.findAll(pageable).map(skillAndHobbyMapper::toDto);
    }

    /**
     * Get one skillAndHobby by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SkillAndHobbyDTO> findOne(Long id) {
        log.debug("Request to get SkillAndHobby : {}", id);
        return skillAndHobbyRepository.findById(id).map(skillAndHobbyMapper::toDto);
    }

    /**
     * Delete the skillAndHobby by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SkillAndHobby : {}", id);
        skillAndHobbyRepository.deleteById(id);
    }
}
