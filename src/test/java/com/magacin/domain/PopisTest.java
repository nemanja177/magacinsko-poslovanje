package com.magacin.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.magacin.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PopisTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Popis.class);
        Popis popis1 = new Popis();
        popis1.setId(1L);
        Popis popis2 = new Popis();
        popis2.setId(popis1.getId());
        assertThat(popis1).isEqualTo(popis2);
        popis2.setId(2L);
        assertThat(popis1).isNotEqualTo(popis2);
        popis1.setId(null);
        assertThat(popis1).isNotEqualTo(popis2);
    }
}
