package com.magacin.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.magacin.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PoslovanGodinaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PoslovanGodina.class);
        PoslovanGodina poslovanGodina1 = new PoslovanGodina();
        poslovanGodina1.setId(1L);
        PoslovanGodina poslovanGodina2 = new PoslovanGodina();
        poslovanGodina2.setId(poslovanGodina1.getId());
        assertThat(poslovanGodina1).isEqualTo(poslovanGodina2);
        poslovanGodina2.setId(2L);
        assertThat(poslovanGodina1).isNotEqualTo(poslovanGodina2);
        poslovanGodina1.setId(null);
        assertThat(poslovanGodina1).isNotEqualTo(poslovanGodina2);
    }
}
