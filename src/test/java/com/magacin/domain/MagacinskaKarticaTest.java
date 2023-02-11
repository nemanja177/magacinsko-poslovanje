package com.magacin.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.magacin.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MagacinskaKarticaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MagacinskaKartica.class);
        MagacinskaKartica magacinskaKartica1 = new MagacinskaKartica();
        magacinskaKartica1.setId(1L);
        MagacinskaKartica magacinskaKartica2 = new MagacinskaKartica();
        magacinskaKartica2.setId(magacinskaKartica1.getId());
        assertThat(magacinskaKartica1).isEqualTo(magacinskaKartica2);
        magacinskaKartica2.setId(2L);
        assertThat(magacinskaKartica1).isNotEqualTo(magacinskaKartica2);
        magacinskaKartica1.setId(null);
        assertThat(magacinskaKartica1).isNotEqualTo(magacinskaKartica2);
    }
}
