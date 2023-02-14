package com.sundev.mission4us.web.rest;

import com.sundev.mission4us.repository.DriverLicenceRepository;
import com.sundev.mission4us.service.DriverLicenceService;
import com.sundev.mission4us.service.dto.DriverLicenceDTO;
import com.sundev.mission4us.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.sundev.mission4us.domain.DriverLicence}.
 */
@RestController
@RequestMapping("/api")
public class DriverLicenceResource {

    private final Logger log = LoggerFactory.getLogger(DriverLicenceResource.class);

    private static final String ENTITY_NAME = "driverLicence";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DriverLicenceService driverLicenceService;

    private final DriverLicenceRepository driverLicenceRepository;

    public DriverLicenceResource(DriverLicenceService driverLicenceService, DriverLicenceRepository driverLicenceRepository) {
        this.driverLicenceService = driverLicenceService;
        this.driverLicenceRepository = driverLicenceRepository;
    }

    /**
     * {@code POST  /driver-licences} : Create a new driverLicence.
     *
     * @param driverLicenceDTO the driverLicenceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new driverLicenceDTO, or with status {@code 400 (Bad Request)} if the driverLicence has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/driver-licences")
    public ResponseEntity<DriverLicenceDTO> createDriverLicence(@RequestBody DriverLicenceDTO driverLicenceDTO) throws URISyntaxException {
        log.debug("REST request to save DriverLicence : {}", driverLicenceDTO);
        if (driverLicenceDTO.getId() != null) {
            throw new BadRequestAlertException("A new driverLicence cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DriverLicenceDTO result = driverLicenceService.save(driverLicenceDTO);
        return ResponseEntity
            .created(new URI("/api/driver-licences/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /driver-licences/:id} : Updates an existing driverLicence.
     *
     * @param id the id of the driverLicenceDTO to save.
     * @param driverLicenceDTO the driverLicenceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated driverLicenceDTO,
     * or with status {@code 400 (Bad Request)} if the driverLicenceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the driverLicenceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/driver-licences/{id}")
    public ResponseEntity<DriverLicenceDTO> updateDriverLicence(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DriverLicenceDTO driverLicenceDTO
    ) throws URISyntaxException {
        log.debug("REST request to update DriverLicence : {}, {}", id, driverLicenceDTO);
        if (driverLicenceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, driverLicenceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!driverLicenceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DriverLicenceDTO result = driverLicenceService.save(driverLicenceDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, driverLicenceDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /driver-licences/:id} : Partial updates given fields of an existing driverLicence, field will ignore if it is null
     *
     * @param id the id of the driverLicenceDTO to save.
     * @param driverLicenceDTO the driverLicenceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated driverLicenceDTO,
     * or with status {@code 400 (Bad Request)} if the driverLicenceDTO is not valid,
     * or with status {@code 404 (Not Found)} if the driverLicenceDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the driverLicenceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/driver-licences/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DriverLicenceDTO> partialUpdateDriverLicence(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DriverLicenceDTO driverLicenceDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DriverLicence partially : {}, {}", id, driverLicenceDTO);
        if (driverLicenceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, driverLicenceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!driverLicenceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DriverLicenceDTO> result = driverLicenceService.partialUpdate(driverLicenceDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, driverLicenceDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /driver-licences} : get all the driverLicences.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of driverLicences in body.
     */
    @GetMapping("/driver-licences")
    public ResponseEntity<List<DriverLicenceDTO>> getAllDriverLicences(Pageable pageable) {
        log.debug("REST request to get a page of DriverLicences");
        Page<DriverLicenceDTO> page = driverLicenceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /driver-licences/:id} : get the "id" driverLicence.
     *
     * @param id the id of the driverLicenceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the driverLicenceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/driver-licences/{id}")
    public ResponseEntity<DriverLicenceDTO> getDriverLicence(@PathVariable Long id) {
        log.debug("REST request to get DriverLicence : {}", id);
        Optional<DriverLicenceDTO> driverLicenceDTO = driverLicenceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(driverLicenceDTO);
    }

    /**
     * {@code DELETE  /driver-licences/:id} : delete the "id" driverLicence.
     *
     * @param id the id of the driverLicenceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/driver-licences/{id}")
    public ResponseEntity<Void> deleteDriverLicence(@PathVariable Long id) {
        log.debug("REST request to delete DriverLicence : {}", id);
        driverLicenceService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
