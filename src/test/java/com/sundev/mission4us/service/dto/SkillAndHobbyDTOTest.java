package com.sundev.mission4us.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.sundev.mission4us.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SkillAndHobbyDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SkillAndHobbyDTO.class);
        SkillAndHobbyDTO skillAndHobbyDTO1 = new SkillAndHobbyDTO();
        skillAndHobbyDTO1.setId(1L);
        SkillAndHobbyDTO skillAndHobbyDTO2 = new SkillAndHobbyDTO();
        assertThat(skillAndHobbyDTO1).isNotEqualTo(skillAndHobbyDTO2);
        skillAndHobbyDTO2.setId(skillAndHobbyDTO1.getId());
        assertThat(skillAndHobbyDTO1).isEqualTo(skillAndHobbyDTO2);
        skillAndHobbyDTO2.setId(2L);
        assertThat(skillAndHobbyDTO1).isNotEqualTo(skillAndHobbyDTO2);
        skillAndHobbyDTO1.setId(null);
        assertThat(skillAndHobbyDTO1).isNotEqualTo(skillAndHobbyDTO2);
    }
}
