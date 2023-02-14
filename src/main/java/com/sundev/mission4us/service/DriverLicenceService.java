package com.sundev.mission4us.service;

import com.sundev.mission4us.domain.DriverLicence;
import com.sundev.mission4us.repository.DriverLicenceRepository;
import com.sundev.mission4us.service.dto.DriverLicenceDTO;
import com.sundev.mission4us.service.mapper.DriverLicenceMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DriverLicence}.
 */
@Service
@Transactional
public class DriverLicenceService {

    private final Logger log = LoggerFactory.getLogger(DriverLicenceService.class);

    private final DriverLicenceRepository driverLicenceRepository;

    private final DriverLicenceMapper driverLicenceMapper;

    public DriverLicenceService(DriverLicenceRepository driverLicenceRepository, DriverLicenceMapper driverLicenceMapper) {
        this.driverLicenceRepository = driverLicenceRepository;
        this.driverLicenceMapper = driverLicenceMapper;
    }

    /**
     * Save a driverLicence.
     *
     * @param driverLicenceDTO the entity to save.
     * @return the persisted entity.
     */
    public DriverLicenceDTO save(DriverLicenceDTO driverLicenceDTO) {
        log.debug("Request to save DriverLicence : {}", driverLicenceDTO);
        DriverLicence driverLicence = driverLicenceMapper.toEntity(driverLicenceDTO);
        driverLicence = driverLicenceRepository.save(driverLicence);
        return driverLicenceMapper.toDto(driverLicence);
    }

    /**
     * Partially update a driverLicence.
     *
     * @param driverLicenceDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DriverLicenceDTO> partialUpdate(DriverLicenceDTO driverLicenceDTO) {
        log.debug("Request to partially update DriverLicence : {}", driverLicenceDTO);

        return driverLicenceRepository
            .findById(driverLicenceDTO.getId())
            .map(existingDriverLicence -> {
                driverLicenceMapper.partialUpdate(existingDriverLicence, driverLicenceDTO);

                return existingDriverLicence;
            })
            .map(driverLicenceRepository::save)
            .map(driverLicenceMapper::toDto);
    }

    /**
     * Get all the driverLicences.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DriverLicenceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DriverLicences");
        return driverLicenceRepository.findAll(pageable).map(driverLicenceMapper::toDto);
    }

    /**
     * Get one driverLicence by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DriverLicenceDTO> findOne(Long id) {
        log.debug("Request to get DriverLicence : {}", id);
        return driverLicenceRepository.findById(id).map(driverLicenceMapper::toDto);
    }

    /**
     * Delete the driverLicence by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DriverLicence : {}", id);
        driverLicenceRepository.deleteById(id);
    }
}
