package com.magacin.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.magacin.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AnalitickaMagacinskaKarticaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnalitickaMagacinskaKartica.class);
        AnalitickaMagacinskaKartica analitickaMagacinskaKartica1 = new AnalitickaMagacinskaKartica();
        analitickaMagacinskaKartica1.setId(1L);
        AnalitickaMagacinskaKartica analitickaMagacinskaKartica2 = new AnalitickaMagacinskaKartica();
        analitickaMagacinskaKartica2.setId(analitickaMagacinskaKartica1.getId());
        assertThat(analitickaMagacinskaKartica1).isEqualTo(analitickaMagacinskaKartica2);
        analitickaMagacinskaKartica2.setId(2L);
        assertThat(analitickaMagacinskaKartica1).isNotEqualTo(analitickaMagacinskaKartica2);
        analitickaMagacinskaKartica1.setId(null);
        assertThat(analitickaMagacinskaKartica1).isNotEqualTo(analitickaMagacinskaKartica2);
    }
}
