package com.sundev.mission4us.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.sundev.mission4us.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SkillAndHobbyTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SkillAndHobby.class);
        SkillAndHobby skillAndHobby1 = new SkillAndHobby();
        skillAndHobby1.setId(1L);
        SkillAndHobby skillAndHobby2 = new SkillAndHobby();
        skillAndHobby2.setId(skillAndHobby1.getId());
        assertThat(skillAndHobby1).isEqualTo(skillAndHobby2);
        skillAndHobby2.setId(2L);
        assertThat(skillAndHobby1).isNotEqualTo(skillAndHobby2);
        skillAndHobby1.setId(null);
        assertThat(skillAndHobby1).isNotEqualTo(skillAndHobby2);
    }
}
