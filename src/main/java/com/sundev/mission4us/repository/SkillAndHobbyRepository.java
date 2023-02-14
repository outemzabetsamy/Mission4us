package com.sundev.mission4us.repository;

import com.sundev.mission4us.domain.SkillAndHobby;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the SkillAndHobby entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SkillAndHobbyRepository extends JpaRepository<SkillAndHobby, Long> {}
