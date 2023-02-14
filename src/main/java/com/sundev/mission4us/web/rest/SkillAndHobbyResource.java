package com.sundev.mission4us.web.rest;

import com.sundev.mission4us.repository.SkillAndHobbyRepository;
import com.sundev.mission4us.service.SkillAndHobbyService;
import com.sundev.mission4us.service.dto.SkillAndHobbyDTO;
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
 * REST controller for managing {@link com.sundev.mission4us.domain.SkillAndHobby}.
 */
@RestController
@RequestMapping("/api")
public class SkillAndHobbyResource {

    private final Logger log = LoggerFactory.getLogger(SkillAndHobbyResource.class);

    private static final String ENTITY_NAME = "skillAndHobby";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SkillAndHobbyService skillAndHobbyService;

    private final SkillAndHobbyRepository skillAndHobbyRepository;

    public SkillAndHobbyResource(SkillAndHobbyService skillAndHobbyService, SkillAndHobbyRepository skillAndHobbyRepository) {
        this.skillAndHobbyService = skillAndHobbyService;
        this.skillAndHobbyRepository = skillAndHobbyRepository;
    }

    /**
     * {@code POST  /skill-and-hobbies} : Create a new skillAndHobby.
     *
     * @param skillAndHobbyDTO the skillAndHobbyDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new skillAndHobbyDTO, or with status {@code 400 (Bad Request)} if the skillAndHobby has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/skill-and-hobbies")
    public ResponseEntity<SkillAndHobbyDTO> createSkillAndHobby(@RequestBody SkillAndHobbyDTO skillAndHobbyDTO) throws URISyntaxException {
        log.debug("REST request to save SkillAndHobby : {}", skillAndHobbyDTO);
        if (skillAndHobbyDTO.getId() != null) {
            throw new BadRequestAlertException("A new skillAndHobby cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SkillAndHobbyDTO result = skillAndHobbyService.save(skillAndHobbyDTO);
        return ResponseEntity
            .created(new URI("/api/skill-and-hobbies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /skill-and-hobbies/:id} : Updates an existing skillAndHobby.
     *
     * @param id the id of the skillAndHobbyDTO to save.
     * @param skillAndHobbyDTO the skillAndHobbyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated skillAndHobbyDTO,
     * or with status {@code 400 (Bad Request)} if the skillAndHobbyDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the skillAndHobbyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/skill-and-hobbies/{id}")
    public ResponseEntity<SkillAndHobbyDTO> updateSkillAndHobby(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SkillAndHobbyDTO skillAndHobbyDTO
    ) throws URISyntaxException {
        log.debug("REST request to update SkillAndHobby : {}, {}", id, skillAndHobbyDTO);
        if (skillAndHobbyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, skillAndHobbyDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!skillAndHobbyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SkillAndHobbyDTO result = skillAndHobbyService.save(skillAndHobbyDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, skillAndHobbyDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /skill-and-hobbies/:id} : Partial updates given fields of an existing skillAndHobby, field will ignore if it is null
     *
     * @param id the id of the skillAndHobbyDTO to save.
     * @param skillAndHobbyDTO the skillAndHobbyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated skillAndHobbyDTO,
     * or with status {@code 400 (Bad Request)} if the skillAndHobbyDTO is not valid,
     * or with status {@code 404 (Not Found)} if the skillAndHobbyDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the skillAndHobbyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/skill-and-hobbies/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SkillAndHobbyDTO> partialUpdateSkillAndHobby(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SkillAndHobbyDTO skillAndHobbyDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update SkillAndHobby partially : {}, {}", id, skillAndHobbyDTO);
        if (skillAndHobbyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, skillAndHobbyDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!skillAndHobbyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SkillAndHobbyDTO> result = skillAndHobbyService.partialUpdate(skillAndHobbyDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, skillAndHobbyDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /skill-and-hobbies} : get all the skillAndHobbies.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of skillAndHobbies in body.
     */
    @GetMapping("/skill-and-hobbies")
    public ResponseEntity<List<SkillAndHobbyDTO>> getAllSkillAndHobbies(Pageable pageable) {
        log.debug("REST request to get a page of SkillAndHobbies");
        Page<SkillAndHobbyDTO> page = skillAndHobbyService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /skill-and-hobbies/:id} : get the "id" skillAndHobby.
     *
     * @param id the id of the skillAndHobbyDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the skillAndHobbyDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/skill-and-hobbies/{id}")
    public ResponseEntity<SkillAndHobbyDTO> getSkillAndHobby(@PathVariable Long id) {
        log.debug("REST request to get SkillAndHobby : {}", id);
        Optional<SkillAndHobbyDTO> skillAndHobbyDTO = skillAndHobbyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(skillAndHobbyDTO);
    }

    /**
     * {@code DELETE  /skill-and-hobbies/:id} : delete the "id" skillAndHobby.
     *
     * @param id the id of the skillAndHobbyDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/skill-and-hobbies/{id}")
    public ResponseEntity<Void> deleteSkillAndHobby(@PathVariable Long id) {
        log.debug("REST request to delete SkillAndHobby : {}", id);
        skillAndHobbyService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
