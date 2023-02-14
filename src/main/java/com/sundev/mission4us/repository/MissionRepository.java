package com.sundev.mission4us.repository;

import com.sundev.mission4us.domain.Mission;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Mission entity.
 */
@Repository
public interface MissionRepository extends JpaRepository<Mission, Long> {
    @Query(
        value = "select distinct mission from Mission mission left join fetch mission.languages",
        countQuery = "select count(distinct mission) from Mission mission"
    )
    Page<Mission> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct mission from Mission mission left join fetch mission.languages")
    List<Mission> findAllWithEagerRelationships();

    @Query("select mission from Mission mission left join fetch mission.languages where mission.id =:id")
    Optional<Mission> findOneWithEagerRelationships(@Param("id") Long id);
}
