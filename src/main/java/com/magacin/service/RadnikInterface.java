package com.magacin.service;

import com.magacin.domain.Radnik;
import java.util.List;
import org.springframework.data.domain.Page;

public interface RadnikInterface {
    List<Radnik> findAll();

    //Page<Radnik> findAll(int pageNum);

    Radnik findOne(Long id);

    Radnik save(Radnik entity);

    void delete(Long id);
}
