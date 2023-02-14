package com.sundev.mission4us.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.sundev.mission4us.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DriverLicenceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DriverLicence.class);
        DriverLicence driverLicence1 = new DriverLicence();
        driverLicence1.setId(1L);
        DriverLicence driverLicence2 = new DriverLicence();
        driverLicence2.setId(driverLicence1.getId());
        assertThat(driverLicence1).isEqualTo(driverLicence2);
        driverLicence2.setId(2L);
        assertThat(driverLicence1).isNotEqualTo(driverLicence2);
        driverLicence1.setId(null);
        assertThat(driverLicence1).isNotEqualTo(driverLicence2);
    }
}
