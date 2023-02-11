package com.magacin.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.magacin.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RadnikTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Radnik.class);
        Radnik radnik1 = new Radnik();
        radnik1.setId(1L);
        Radnik radnik2 = new Radnik();
        radnik2.setId(radnik1.getId());
        assertThat(radnik1).isEqualTo(radnik2);
        radnik2.setId(2L);
        assertThat(radnik1).isNotEqualTo(radnik2);
        radnik1.setId(null);
        assertThat(radnik1).isNotEqualTo(radnik2);
    }
}
