package com.magacin.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.magacin.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StavkaPopisaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StavkaPopisa.class);
        StavkaPopisa stavkaPopisa1 = new StavkaPopisa();
        stavkaPopisa1.setId(1L);
        StavkaPopisa stavkaPopisa2 = new StavkaPopisa();
        stavkaPopisa2.setId(stavkaPopisa1.getId());
        assertThat(stavkaPopisa1).isEqualTo(stavkaPopisa2);
        stavkaPopisa2.setId(2L);
        assertThat(stavkaPopisa1).isNotEqualTo(stavkaPopisa2);
        stavkaPopisa1.setId(null);
        assertThat(stavkaPopisa1).isNotEqualTo(stavkaPopisa2);
    }
}
