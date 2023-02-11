package com.magacin.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.magacin.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PrometniDokumentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PrometniDokument.class);
        PrometniDokument prometniDokument1 = new PrometniDokument();
        prometniDokument1.setId(1L);
        PrometniDokument prometniDokument2 = new PrometniDokument();
        prometniDokument2.setId(prometniDokument1.getId());
        assertThat(prometniDokument1).isEqualTo(prometniDokument2);
        prometniDokument2.setId(2L);
        assertThat(prometniDokument1).isNotEqualTo(prometniDokument2);
        prometniDokument1.setId(null);
        assertThat(prometniDokument1).isNotEqualTo(prometniDokument2);
    }
}
