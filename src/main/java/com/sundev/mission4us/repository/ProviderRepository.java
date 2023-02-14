package com.sundev.mission4us.repository;

import com.sundev.mission4us.domain.Provider;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Provider entity.
 */
@Repository
public interface ProviderRepository extends JpaRepository<Provider, Long> {
    @Query(
        value = "select distinct provider from Provider provider left join fetch provider.languages left join fetch provider.jobs left join fetch provider.driverLicences",
        countQuery = "select count(distinct provider) from Provider provider"
    )
    Page<Provider> findAllWithEagerRelationships(Pageable pageable);

    @Query(
        "select distinct provider from Provider provider left join fetch provider.languages left join fetch provider.jobs left join fetch provider.driverLicences"
    )
    List<Provider> findAllWithEagerRelationships();

    @Query(
        "select provider from Provider provider left join fetch provider.languages left join fetch provider.jobs left join fetch provider.driverLicences where provider.id =:id"
    )
    Optional<Provider> findOneWithEagerRelationships(@Param("id") Long id);
}
