package com.magacin.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.magacin.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PoslovniPartnerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PoslovniPartner.class);
        PoslovniPartner poslovniPartner1 = new PoslovniPartner();
        poslovniPartner1.setId(1L);
        PoslovniPartner poslovniPartner2 = new PoslovniPartner();
        poslovniPartner2.setId(poslovniPartner1.getId());
        assertThat(poslovniPartner1).isEqualTo(poslovniPartner2);
        poslovniPartner2.setId(2L);
        assertThat(poslovniPartner1).isNotEqualTo(poslovniPartner2);
        poslovniPartner1.setId(null);
        assertThat(poslovniPartner1).isNotEqualTo(poslovniPartner2);
    }
}
