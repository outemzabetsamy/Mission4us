package com.sundev.mission4us.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SkillAndHobbyMapperTest {

    private SkillAndHobbyMapper skillAndHobbyMapper;

    @BeforeEach
    public void setUp() {
        skillAndHobbyMapper = new SkillAndHobbyMapperImpl();
    }
}
