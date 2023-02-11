package com.magacin.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.magacin.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PrometMagacinskeKarticeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PrometMagacinskeKartice.class);
        PrometMagacinskeKartice prometMagacinskeKartice1 = new PrometMagacinskeKartice();
        prometMagacinskeKartice1.setId(1L);
        PrometMagacinskeKartice prometMagacinskeKartice2 = new PrometMagacinskeKartice();
        prometMagacinskeKartice2.setId(prometMagacinskeKartice1.getId());
        assertThat(prometMagacinskeKartice1).isEqualTo(prometMagacinskeKartice2);
        prometMagacinskeKartice2.setId(2L);
        assertThat(prometMagacinskeKartice1).isNotEqualTo(prometMagacinskeKartice2);
        prometMagacinskeKartice1.setId(null);
        assertThat(prometMagacinskeKartice1).isNotEqualTo(prometMagacinskeKartice2);
    }
}
