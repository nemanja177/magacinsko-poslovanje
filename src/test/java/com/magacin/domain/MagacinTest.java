package com.magacin.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.magacin.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MagacinTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Magacin.class);
        Magacin magacin1 = new Magacin();
        magacin1.setId(1L);
        Magacin magacin2 = new Magacin();
        magacin2.setId(magacin1.getId());
        assertThat(magacin1).isEqualTo(magacin2);
        magacin2.setId(2L);
        assertThat(magacin1).isNotEqualTo(magacin2);
        magacin1.setId(null);
        assertThat(magacin1).isNotEqualTo(magacin2);
    }
}
