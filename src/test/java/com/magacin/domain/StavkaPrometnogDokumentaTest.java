package com.magacin.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.magacin.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StavkaPrometnogDokumentaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StavkaPrometnogDokumenta.class);
        StavkaPrometnogDokumenta stavkaPrometnogDokumenta1 = new StavkaPrometnogDokumenta();
        stavkaPrometnogDokumenta1.setId(1L);
        StavkaPrometnogDokumenta stavkaPrometnogDokumenta2 = new StavkaPrometnogDokumenta();
        stavkaPrometnogDokumenta2.setId(stavkaPrometnogDokumenta1.getId());
        assertThat(stavkaPrometnogDokumenta1).isEqualTo(stavkaPrometnogDokumenta2);
        stavkaPrometnogDokumenta2.setId(2L);
        assertThat(stavkaPrometnogDokumenta1).isNotEqualTo(stavkaPrometnogDokumenta2);
        stavkaPrometnogDokumenta1.setId(null);
        assertThat(stavkaPrometnogDokumenta1).isNotEqualTo(stavkaPrometnogDokumenta2);
    }
}
