package com.sundev.mission4us.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.sundev.mission4us.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DriverLicenceDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DriverLicenceDTO.class);
        DriverLicenceDTO driverLicenceDTO1 = new DriverLicenceDTO();
        driverLicenceDTO1.setId(1L);
        DriverLicenceDTO driverLicenceDTO2 = new DriverLicenceDTO();
        assertThat(driverLicenceDTO1).isNotEqualTo(driverLicenceDTO2);
        driverLicenceDTO2.setId(driverLicenceDTO1.getId());
        assertThat(driverLicenceDTO1).isEqualTo(driverLicenceDTO2);
        driverLicenceDTO2.setId(2L);
        assertThat(driverLicenceDTO1).isNotEqualTo(driverLicenceDTO2);
        driverLicenceDTO1.setId(null);
        assertThat(driverLicenceDTO1).isNotEqualTo(driverLicenceDTO2);
    }
}
