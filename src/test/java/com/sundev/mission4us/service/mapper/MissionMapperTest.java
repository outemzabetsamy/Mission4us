package com.sundev.mission4us.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MissionMapperTest {

    private MissionMapper missionMapper;

    @BeforeEach
    public void setUp() {
        missionMapper = new MissionMapperImpl();
    }
}
