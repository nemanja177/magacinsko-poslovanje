package com.magacin.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.magacin.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ArtikalTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Artikal.class);
        Artikal artikal1 = new Artikal();
        artikal1.setId(1L);
        Artikal artikal2 = new Artikal();
        artikal2.setId(artikal1.getId());
        assertThat(artikal1).isEqualTo(artikal2);
        artikal2.setId(2L);
        assertThat(artikal1).isNotEqualTo(artikal2);
        artikal1.setId(null);
        assertThat(artikal1).isNotEqualTo(artikal2);
    }
}
