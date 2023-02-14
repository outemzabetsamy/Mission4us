package com.sundev.mission4us.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class QuoteMapperTest {

    private QuoteMapper quoteMapper;

    @BeforeEach
    public void setUp() {
        quoteMapper = new QuoteMapperImpl();
    }
}
