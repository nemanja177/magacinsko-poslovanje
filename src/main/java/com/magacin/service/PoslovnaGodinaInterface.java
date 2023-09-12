package com.magacin.service;

import com.magacin.domain.PoslovanGodina;
import java.util.List;
import org.springframework.data.domain.Page;

public interface PoslovnaGodinaInterface {
    List<PoslovanGodina> findAll();

    Page<PoslovanGodina> findAll(int pageNum);

    PoslovanGodina findPoslovanGodinaById(Long id);

    PoslovanGodina save(PoslovanGodina poslovnaGodina);

    void delete(Long Id);
}
