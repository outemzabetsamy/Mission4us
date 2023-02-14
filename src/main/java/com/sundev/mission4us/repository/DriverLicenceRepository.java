package com.sundev.mission4us.repository;

import com.sundev.mission4us.domain.DriverLicence;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DriverLicence entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DriverLicenceRepository extends JpaRepository<DriverLicence, Long> {}
